package com.onboarding.task

import com.fasterxml.jackson.databind.ObjectMapper
import com.onboarding.task.entity.Member
import com.onboarding.task.repository.MemberRepository
import com.onboarding.task.service.JwtService
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.transaction.annotation.Transactional
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

private val KEY_USERNAME: String = "username"
private val KEY_PASSWORD: String = "password"
private val USERNAME: String = "username@email.com"
private val PASSWORD: String = "123456789"

private val LOGIN_URL: String = "/login"

private const val ACCESS_TOKEN_SUBJECT: String = "AccessToken"
private const val BEARER : String = "Bearer"

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class JwtFilterAuthenticationTest @Autowired constructor(
    val mockMvc: MockMvc,
    val memberRepository: MemberRepository,
    val em: EntityManager,
    val jwtService: JwtService,

    val delegatingPasswordEncoder: PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder(),

    @Value("\${jwt.secret}")
    val secret : String,
    @Value("\${jwt.access.expiration}")
    val accessTokenValidityInSeconds: Long,
    @Value("\${jwt.refresh.expiration}")
    val refreshTokenValidityInSeconds: Long,
    @Value("\${jwt.access.header}")
    val accessHeader: String,
    @Value("\${jwt.refresh.header}")
    val refreshHeader: String,

    private val objectMapper: ObjectMapper

    ){

    private fun clear() {
        em.flush()
        em.clear()
    }

    @BeforeEach
    fun init() {
        memberRepository.save(Member(USERNAME, delegatingPasswordEncoder.encode(PASSWORD), "member1"))
    }

    private fun getUsernamePasswordMap(username: String, password: String) : Map<String, String> {
        val map = HashMap<String, String>()
        map[KEY_USERNAME] = username
        map[KEY_PASSWORD] = password
        return map
    }

    private fun getAccessAndRefreshToken() : Map<String, String> {
        val map = getUsernamePasswordMap(USERNAME, PASSWORD)

        val result: MvcResult = mockMvc.perform(
            post(LOGIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(map)))
            .andReturn()

        val accessToken: String = result.response.getHeader(accessHeader)!!
        val refreshToken: String = result.response.getHeader(refreshHeader)!!

        val tokenMap = HashMap<String, String>()
        tokenMap[accessHeader]= accessToken
        tokenMap[refreshHeader] = refreshToken

        return tokenMap

    }

    @Test
    fun Access_Refresh_모두_존재_X() {
        mockMvc.perform(
            get(LOGIN_URL+"123")
        ).andExpect(status().isForbidden)
    }
}
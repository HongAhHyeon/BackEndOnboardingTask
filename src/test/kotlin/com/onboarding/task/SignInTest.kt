package com.onboarding.task

import com.fasterxml.jackson.databind.ObjectMapper
import com.onboarding.task.entity.Member
import com.onboarding.task.repository.MemberRepository
import com.onboarding.task.service.JwtService
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.Throws


private val KEY_USERNAME: String = "username"
private val KEY_PASSWORD: String = "password"
private val USERNAME: String = "username@email.com"
private val PASSWORD: String = "123456789"

private val LOGIN_URL: String = "/login"


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SignInTest @Autowired constructor(
    val mockMvc: MockMvc,
    val memberRepository: MemberRepository,
    val em: EntityManager,
    val jwtService: JwtService,

    val delegatingPasswordEncoder: PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder(),

    private val objectMapper: ObjectMapper

    ){

    private fun clear() {
        em.flush()
        em.clear()
    }

    @BeforeEach
    fun init() {
        memberRepository.save(Member(USERNAME, delegatingPasswordEncoder.encode(PASSWORD), "member1"))
        clear()
    }

    private fun getUsernamePasswordMap(username: String, password: String) : Map<String, String> {
        val map = HashMap<String, String>()
        map[KEY_USERNAME] = username
        map[KEY_PASSWORD] = password
        return map
    }

    private fun perform(url: String, mediaType: MediaType, usernamePasswordMap: Map<String, String>) : ResultActions{
        return mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .contentType(mediaType)
            .content(objectMapper.writeValueAsString(usernamePasswordMap)))
    }

    @Test
    fun 로그인_성공() {
        val map = getUsernamePasswordMap(USERNAME, PASSWORD)

        val result = perform(LOGIN_URL, MediaType.APPLICATION_JSON, map)
            .andDo(print())
            .andExpect(status().isOk)
            .andReturn()
    }

    @Test
    @Throws(Exception::class)
    fun 로그인_실패_아이디틀림() {
        val map = getUsernamePasswordMap(USERNAME+"123", PASSWORD)

        val result = perform(LOGIN_URL, MediaType.APPLICATION_JSON, map)
            .andDo(print())
            .andExpect(status().isOk)
            .andReturn()
    }
    @Test
    @Throws(Exception::class)
    fun 로그인_실패_비밀번호틀림() {
        val map = getUsernamePasswordMap(USERNAME, PASSWORD+"123")

        val result = perform(LOGIN_URL, MediaType.APPLICATION_JSON, map)
            .andDo(print())
            .andExpect(status().isOk)
            .andReturn()
    }

    @Test
    @Throws(Exception::class)
    fun 로그인_주소_X_FORBIDDEN() {
        val map = getUsernamePasswordMap(USERNAME, PASSWORD)

        val result = perform(LOGIN_URL+"123", MediaType.APPLICATION_JSON, map)
            .andDo(print())
            .andExpect(status().isForbidden)
    }
}
package com.onboarding.task.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.onboarding.task.entity.Member
import com.onboarding.task.enum.MemberRole
import com.onboarding.task.repository.MemberRepository
import jakarta.persistence.EntityManager
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class SignInServiceTest @Autowired constructor(
    val mockMvc: MockMvc,
    val memberRepository: MemberRepository,
    val em: EntityManager,
    val delegatingPasswordEncoder: PasswordEncoder? = PasswordEncoderFactories.createDelegatingPasswordEncoder(),
    val objectMapper: ObjectMapper = ObjectMapper(),

    ){

    val KEY_USEREMAIL: String = "useremail"
    val KEY_PASSWORD: String = "password"
    val USEREMAIL: String = "useremail"
    val PASSWORD: String = "123456789"
    val SignIn_URL: String = "/signIn"

    fun clear() {
        em.flush()
        em.clear()
    }

    @BeforeEach
    fun init() {
        memberRepository.save(Member(USEREMAIL, delegatingPasswordEncoder!!.encode(PASSWORD), "User1", MemberRole.USER))
        clear()
    }

    fun getUserEmailPasswordMap(userEmail: String, password: String) : Map<String, String> {
        val map = HashMap<String, String>()
        map[KEY_USEREMAIL] = userEmail
        map[KEY_PASSWORD] = password
        return map
    }

    fun perform(url: String, mediaType: MediaType, userEmailPasswordMap: Map<String, String>) : ResultActions {
        return mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .contentType(mediaType)
            .content(objectMapper.writeValueAsBytes(userEmailPasswordMap)))
    }

    @Test
    fun 로그인_성공() {
        val map = getUserEmailPasswordMap(USEREMAIL, PASSWORD)

        val result : MvcResult = perform(SignIn_URL, MediaType.APPLICATION_JSON, map)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andReturn()
    }

}
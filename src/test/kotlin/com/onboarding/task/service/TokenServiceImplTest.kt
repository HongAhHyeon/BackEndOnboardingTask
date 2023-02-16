package com.onboarding.task.service

import com.onboarding.task.handler.JwtHandler
import com.onboarding.task.service.impl.TokenServiceImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.*
import org.mockito.BDDMockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.util.ReflectionTestUtils
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest


@ExtendWith(MockitoExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class TokenServiceImplTest @Autowired constructor (

    val tokenServiceImpl: TokenServiceImpl,
    val jwtHandler: JwtHandler
){

    @BeforeEach
    fun beforeEach() {
        ReflectionTestUtils.setField(tokenServiceImpl, "accessTokenMaxAgeSeconds", 10L)
        ReflectionTestUtils.setField(tokenServiceImpl, "refreshTokenMaxAgeSeconds", 10L)
        ReflectionTestUtils.setField(tokenServiceImpl, "accessKey", "accessKey")
        ReflectionTestUtils.setField(tokenServiceImpl, "refreshKey", "refreshKey")
    }

    @Test
    fun createAccessTokenTest() {
        ReflectionTestUtils.setField(tokenServiceImpl, "accessTokenMaxAgeSeconds", 10L)
        ReflectionTestUtils.setField(tokenServiceImpl, "refreshTokenMaxAgeSeconds", 10L)
        ReflectionTestUtils.setField(tokenServiceImpl, "accessKey", "accessKey")
        ReflectionTestUtils.setField(tokenServiceImpl, "refreshKey", "refreshKey")
        given(jwtHandler.createToken(anyString(), anyString(), anyLong())).willReturn("access")

        val token = tokenServiceImpl.createAccessToken("subject")

        assertThat(token).isEqualTo("access")
        verify(jwtHandler).createToken(anyString(), anyString(), anyLong())
    }

    @Test
    fun createRefreshTokenTest() {
        given(jwtHandler.createToken(anyString(), anyString(), anyLong())).willReturn("refresh")

        val token = tokenServiceImpl.createRefreshToken("subject")

        assertThat(token).isEqualTo("refresh")
        verify(jwtHandler).createToken(anyString(), anyString(), anyLong())
    }

}
package com.onboarding.task.handler

import com.onboarding.task.service.impl.UserServiceImpl
import org.junit.jupiter.api.Test
import java.util.Base64
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JwtHandlerTest @Autowired constructor (
    private val jwtHandler: JwtHandler
){

    @Test
    fun createTokenTest() {
        var encodedKey: String = Base64.getEncoder().encodeToString("myKey!@dlajf21341234123412342134231423143214234234231423412eiwlfksajd12".toByteArray())
        var token: String = createToken(encodedKey, "subject", 60L)

        assertThat(token).contains("Bearer")

    }

    @Test
    fun extracSubjectTest() {
        val encodedKey = Base64.getEncoder().encodeToString("myKey!@dlajf21341234123412342134231423143214234234231423412eiwlfksajd12".toByteArray())
        val subject = "subject"
        val token = createToken(encodedKey, subject, 60L)

        val extractSubject = jwtHandler.extractSubject(encodedKey, token)

        assertThat(extractSubject).isEqualTo(subject)
    }


    private fun createToken(encodedKey: String, subject: String, maxAgeSeconds: Long) : String {
        return jwtHandler.createToken(
            encodedKey,
            subject,
            maxAgeSeconds
        )
    }
}
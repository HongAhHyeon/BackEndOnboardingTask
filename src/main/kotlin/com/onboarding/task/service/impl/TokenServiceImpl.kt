package com.onboarding.task.service.impl

import com.onboarding.task.handler.JwtHandler
import com.onboarding.task.service.TokenService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class TokenServiceImpl (
    private val jwtHandler: JwtHandler,
    @Value("\${jwt.max-age.access}")
    private val accessTokenMaxAgeSeconds: Long,
    @Value("\${jwt.max-age.refresh}")
    private val refreshTokenMaxAgeSeconds: Long,
    @Value("\${jwt.key.access}")
    private val accessKey: String,
    @Value("\${jwt.key.refresh}")
    private val refreshKey: String
) : TokenService {
    override fun createAccessToken(subject: String): String {
        TODO("Not yet implemented")
    }

    override fun createRefreshToken(subject: String): String {
        TODO("Not yet implemented")
    }
}
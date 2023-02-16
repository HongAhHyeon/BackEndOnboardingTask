package com.onboarding.task.service

interface TokenService {
    fun  createAccessToken(subject: String) : String
    fun createRefreshToken(subject: String) : String
}
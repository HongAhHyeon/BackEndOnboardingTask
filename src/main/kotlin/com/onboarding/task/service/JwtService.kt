package com.onboarding.task.service

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.util.Optional

interface JwtService{
    fun createAccessToken(userEmail: String) : String
    fun createRefreshToken() : String

    fun updateRefreshToken(userEmail: String, refreshToken: String)
    fun destroyRefreshToken(userEmail: String)

    fun sendAccessAndRefreshToken(response: HttpServletResponse, accessToken: String, refreshToken: String)
    fun sendAccessToken(response: HttpServletResponse, accessToken: String)

    fun extractAccessToken(request: HttpServletRequest) : Optional<String>?
    fun extractRefreshToken(request: HttpServletRequest) : Optional<String>?
    fun extractMemberEmail(accessToken: String) : Optional<String>?

    fun setAccessTokenHeader(response: HttpServletResponse, accessToken: String)
    fun setRefreshTokenHeader(response: HttpServletResponse, refreshToken: String)

    fun isTokenValid(token: String) : Boolean
}
package com.onboarding.task.handler

import com.onboarding.task.repository.UserRepository
import com.onboarding.task.service.JwtService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler

class SignInSuccessJWTProvideHandler (
    val jwtService: JwtService,
    val userRepository: UserRepository

) : SimpleUrlAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val userEmail = extractUserEmail(authentication)
        val accessToken = jwtService.createAccessToken(userEmail)
        val refreshToken = jwtService.createRefreshToken()

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken)

        userRepository.findByUserEmail(userEmail)?.updateRefreshToken(refreshToken)

        println("로그인에 성공합니다. JWT를 발급합니다. username : " + userEmail)
        println("AccessToken을 발급합니다. AccessToken : " + accessToken)
        println("RefreshToken을 발급합니다. RefreshToken : " + refreshToken)

//        response.writer.write("success")

    }

    fun extractUserEmail(authentication: Authentication) : String {
        val userDetails: UserDetails = authentication.principal as UserDetails
        return userDetails.username
    }

}
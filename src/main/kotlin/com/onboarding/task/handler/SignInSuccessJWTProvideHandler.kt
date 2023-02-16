package com.onboarding.task.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler

class SignInSuccessJWTProvideHandler (

) : SimpleUrlAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        val userDetails : UserDetails = authentication.principal as UserDetails

        println("로그인에 성공합니다. JWT를 발급합니다. username : " + userDetails.username)

        response.writer.write("success")

    }

}
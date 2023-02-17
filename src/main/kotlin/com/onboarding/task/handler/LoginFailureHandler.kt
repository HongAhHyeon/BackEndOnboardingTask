package com.onboarding.task.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler

class LoginFailureHandler(

) : SimpleUrlAuthenticationFailureHandler(){

    override fun onAuthenticationFailure(request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException) {
        response.status = HttpServletResponse.SC_OK
        response.writer.write("fail")

        println("로그인에 실패했습니다.")

    }
}
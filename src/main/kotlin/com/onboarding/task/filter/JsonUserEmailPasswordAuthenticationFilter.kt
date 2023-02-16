package com.onboarding.task.filter

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.util.StreamUtils
import java.nio.charset.StandardCharsets

class JsonUserEmailPasswordAuthenticationFilter (
    private var objectMapper: ObjectMapper,
    private val DEFAULT_SIGNIN_REQUEST_URL: String = "/signIn",
    private val HTTP_METHOD: String = "POST",
    private val CONTENT_TYPE: String = "application/json",
    private val USEREMAIL_KEY: String = "useremail",
    private val PASSWORD_KEY: String = "password",
    private val DEFAULT_SIGNIN_PATH_REQUEST_MATCHER: AntPathRequestMatcher = AntPathRequestMatcher(DEFAULT_SIGNIN_REQUEST_URL, HTTP_METHOD)
) : AbstractAuthenticationProcessingFilter(DEFAULT_SIGNIN_PATH_REQUEST_MATCHER) {


    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        if(request?.contentType == null || !request.contentType.equals(CONTENT_TYPE)) {
            throw AuthenticationServiceException("Authentication Content-Type not supported: " + request?.contentType)
        }
        
        val messageBody = StreamUtils.copyToString(request.inputStream, StandardCharsets.UTF_8)

        val userEmailPasswordMap = objectMapper.readValue(messageBody, Map::class.java)

        val userEmail = userEmailPasswordMap[USEREMAIL_KEY]
        val password = userEmailPasswordMap[PASSWORD_KEY]

        val authRequest: UsernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(userEmail, password)

        return this.authenticationManager.authenticate(authRequest)
    }
}
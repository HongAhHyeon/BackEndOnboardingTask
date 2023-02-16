package com.onboarding.task.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.onboarding.task.filter.JsonUserEmailPasswordAuthenticationFilter
import com.onboarding.task.handler.SignInFailureHandler
import com.onboarding.task.handler.SignInSuccessJWTProvideHandler
import com.onboarding.task.service.SignInService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.WebSecurityConfigurer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import java.lang.Exception

@Configuration
@EnableWebSecurity
class SecurityConfig (
    private val objectMapper: ObjectMapper,
    private val signInService: SignInService
){
    @Bean
    fun passwordEncoder() : PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity) : SecurityFilterChain {
        http
            .formLogin().disable()
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests()
            .anyRequest().authenticated()

        return http.build()
    }

    @Bean
    fun authenticationManager() : AuthenticationManager {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(passwordEncoder())
        provider.setUserDetailsService(signInService)
        return ProviderManager(provider)
    }

    @Bean
    fun jsonUserEmailPasswordSignInFilter() : JsonUserEmailPasswordAuthenticationFilter {
        val jsonUserEmailPasswordAuthenticationFilter = JsonUserEmailPasswordAuthenticationFilter(objectMapper)
        jsonUserEmailPasswordAuthenticationFilter.setAuthenticationManager(authenticationManager())
        return jsonUserEmailPasswordAuthenticationFilter
    }

    @Bean
    fun signInSuccessJWTProvideHandler() : SignInSuccessJWTProvideHandler {
        return SignInSuccessJWTProvideHandler()
    }

    @Bean
    fun signInFailurHandler() : SignInFailureHandler {
        return SignInFailureHandler()
    }
}
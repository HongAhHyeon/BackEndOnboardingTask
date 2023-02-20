package com.onboarding.task.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.onboarding.task.config.security.filter.JWTAuthenticationProcessingFilter
import com.onboarding.task.config.security.filter.JsonUserEmailPasswordAuthenticationFilter
import com.onboarding.task.handler.LoginFailureHandler
import com.onboarding.task.handler.LoginSuccessJWTProvideHandler
import com.onboarding.task.repository.MemberRepository
import com.onboarding.task.service.JwtService
import com.onboarding.task.service.LoginService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.logout.LogoutFilter
import java.lang.Exception

@Configuration
//@EnableWebSecurity
class SecurityConfig (
    private val objectMapper: ObjectMapper,
    private val loginService: LoginService,
    private val memberRepository: MemberRepository,
    private val jwtService: JwtService
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
//            .and()
//            .authorizeHttpRequests()
//            .requestMatchers("/**").permitAll()
//            .anyRequest().permitAll()


        http.addFilterAfter(jsonUserEmailPasswordLoginFilter(), LogoutFilter::class.java)
        http.addFilterBefore(jwtAuthenticationProcessingFilter(), JsonUserEmailPasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    fun authenticationManager() : AuthenticationManager {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(passwordEncoder())
        provider.setUserDetailsService(loginService)
        return ProviderManager(provider)
    }

    @Bean
    fun loginSuccessJWTProvideHandler() : LoginSuccessJWTProvideHandler {
        return LoginSuccessJWTProvideHandler(jwtService, memberRepository)
    }

    @Bean
    fun loginFailureHandler() : LoginFailureHandler {
        return LoginFailureHandler()
    }

    @Bean
    fun jsonUserEmailPasswordLoginFilter() : JsonUserEmailPasswordAuthenticationFilter {
        val jsonUserEmailPasswordLoginFilter = JsonUserEmailPasswordAuthenticationFilter(objectMapper)
        jsonUserEmailPasswordLoginFilter.setAuthenticationManager(authenticationManager())
        jsonUserEmailPasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessJWTProvideHandler())
        jsonUserEmailPasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler())
        return jsonUserEmailPasswordLoginFilter
    }

    @Bean
    fun jwtAuthenticationProcessingFilter(): JWTAuthenticationProcessingFilter {
        return JWTAuthenticationProcessingFilter(jwtService, memberRepository)
    }

}
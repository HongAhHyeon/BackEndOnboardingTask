package com.onboarding.task.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.onboarding.task.config.security.filter.JWTAuthenticationProcessingFilter
import com.onboarding.task.config.security.filter.JsonUserEmailPasswordAuthenticationFilter
import com.onboarding.task.config.security.filter.ViewsFilter
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
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
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
//            .formLogin().disable()
//            .httpBasic().disable()
//            .csrf().disable()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//            .authorizeHttpRequests()
//            .requestMatchers("/**").permitAll()
//            .anyRequest().permitAll()
        http
            .csrf().disable()
            .httpBasic().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .cors().configurationSource(corsConfigurationSource())


        http.addFilterAfter(jsonUserEmailPasswordLoginFilter(), LogoutFilter::class.java)
//        http.addFilterBefore(jwtAuthenticationProcessingFilter(), JsonUserEmailPasswordAuthenticationFilter::class.java)
        http.addFilterBefore(viewsFilter(), JsonUserEmailPasswordAuthenticationFilter::class.java)
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

//    @Bean
//    fun jwtAuthenticationProcessingFilter(): JWTAuthenticationProcessingFilter {
//        return JWTAuthenticationProcessingFilter(jwtService, memberRepository)
//    }
    @Bean
    fun viewsFilter(): ViewsFilter {
        return ViewsFilter(jwtService, memberRepository)
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val configSource = UrlBasedCorsConfigurationSource()
        val corsConfig = CorsConfiguration()
        corsConfig.allowCredentials = true // js 요청에서 credentials가 include일 때 응답하기 위해 true로 설정
        corsConfig.allowedOriginPatterns = environmentalAllowedOriginPatterns() // 요청을 허용할 ip 설정
        corsConfig.exposedHeaders = listOf() // client가 읽을 수 있는 헤더 값 리스트 설정
        corsConfig.addAllowedHeader("*") // 모든 header 요청 허용
        corsConfig.addAllowedMethod("*") // 모든 post,get,put,delete,patch 요청 허용
        configSource.registerCorsConfiguration("/**", corsConfig) // 모든 요청에 대해 corsConfig 적용
        return configSource
    }

    fun environmentalAllowedOriginPatterns(): List<String>? {
        return listOf(
            "http://localhost:[*]",
            "http://*.readnumber.tax",
            "https://*.readnumber.tax",
        )
    }

}
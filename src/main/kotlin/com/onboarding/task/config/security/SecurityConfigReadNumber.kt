//package com.onboarding.task.config.security
//
//import com.onboarding.task.config.security.handler.DelegatedAccessDeniedHandler
//import io.jsonwebtoken.JwtParser
//import org.springframework.context.annotation.Bean
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.http.SessionCreationPolicy
//import org.springframework.security.web.SecurityFilterChain
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
//import org.springframework.web.cors.CorsConfiguration
//import org.springframework.web.cors.CorsConfigurationSource
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource
//
//class SecurityConfig {
//
//
//    @Bean
//    @Throws(Exception::class)
//    fun filterChain(
//        http: HttpSecurity,
//
//        // security exception handler (GlobalExceptionHandler 와 일괄 처리)
//        accessDeniedHandler: DelegatedAccessDeniedHandler,
//        authenticationEntryPoint: DelegatedAuthenticationEntryPoint,
//        // Temp
//        adminAccessTokenProvider: AdminAccessTokenProvider,
//        adminRefreshTokenProvider: AdminRefreshTokenProvider,
//    ): SecurityFilterChain {
//
//        http
//            .authorizeHttpRequests()
//            .requestMatchers("/user-api/auth/**", "/admin-api/admins/**").permitAll()
//            .requestMatchers("/user-api/**").hasAnyRole(Authority.USER)
//            .requestMatchers("/admin-api/**").hasAnyRole(Authority.ADMIN)
//            .requestMatchers("/**").permitAll()
//            .and()
////            .addFilterBefore(
////                AdminAuthFilter("/admin-api/**", jwtParser, adminAuthHandler),
////                UsernamePasswordAuthenticationFilter::class.java
////            )
//            .addFilterBefore(
//                GlobalBaseExceptionFilter(),
//                UsernamePasswordAuthenticationFilter::class.java
//            )
//            .addFilterBefore(
//                AdminAccessTokenFilter(adminAccessTokenProvider),
//                UsernamePasswordAuthenticationFilter::class.java
//            )
//            .addFilterBefore(
//                AdminRefreshTokenFilter(adminRefreshTokenProvider),
//                UsernamePasswordAuthenticationFilter::class.java
//            )
//            .addFilterBefore(
//                UserAuthFilter("/user-api/**", symmetricCrypto, userAuthHandler),
//                UsernamePasswordAuthenticationFilter::class.java
//            )
//            .exceptionHandling()
//            .accessDeniedHandler(accessDeniedHandler)
//            .authenticationEntryPoint(authenticationEntryPoint)
//
//        http
//            .csrf().disable()
//            .httpBasic().disable()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//            .cors().configurationSource(corsConfigurationSource())
//
//        return http.build()
//    }
//
//    fun environmentalAllowedOriginPatterns(): List<String>? {
//        return listOf(
//            "http://localhost:[*]",
//            "http://*.readnumber.tax",
//            "https://*.readnumber.tax",
//        )
//    }
//
//    @Bean
//    fun corsConfigurationSource(): CorsConfigurationSource? {
//        val configSource = UrlBasedCorsConfigurationSource()
//        val corsConfig = CorsConfiguration()
//        corsConfig.allowCredentials = true // js 요청에서 credentials가 include일 때 응답하기 위해 true로 설정
//        corsConfig.allowedOriginPatterns = environmentalAllowedOriginPatterns() // 요청을 허용할 ip 설정
//        corsConfig.exposedHeaders = listOf() // client가 읽을 수 있는 헤더 값 리스트 설정
//        corsConfig.addAllowedHeader("*") // 모든 header 요청 허용
//        corsConfig.addAllowedMethod("*") // 모든 post,get,put,delete,patch 요청 허용
//        configSource.registerCorsConfiguration("/**", corsConfig) // 모든 요청에 대해 corsConfig 적용
//        return configSource
//    }
//}
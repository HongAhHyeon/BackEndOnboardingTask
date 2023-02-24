package com.onboarding.task.config.security.filter

import com.onboarding.task.entity.Member
import com.onboarding.task.repository.MemberRepository
import com.onboarding.task.service.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

class JwtAuthenticationProcessingFilter(
    private val jwtService: JwtService,
    private val memberRepository: MemberRepository,

    private val authoritiesMapper: GrantedAuthoritiesMapper = NullAuthoritiesMapper(),

    private val NO_CHECK_URL: Array<String> = arrayOf("/login", "/members/signIn", "/members/new")

) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
//        filterChain.doFilter(request, response)
//        return
        if (NO_CHECK_URL.contains(request.requestURI)) {
            filterChain.doFilter(request, response)
            return
        }

//        val headerNames = request.headerNames
//        while (headerNames.hasMoreElements()) {
//            val key = headerNames.nextElement()
//            val value = request.getHeader(key)
//            println("key : " + key +" value : " + value)
//        }
//
//        val refreshToken: Optional<String>? = jwtService.extractRefreshToken(request) ?: null
//
//        if (refreshToken != null) {
//            checkRefreshTokenAndReIssueAccessToken(response, refreshToken)
//            return
//        }

        checkAccessTokenAndAuthentication(request, response, filterChain)
    }

    private fun checkAccessTokenAndAuthentication(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

        jwtService.extractAccessToken(request).let { jwtService.extractMemberEmail(it.toString()) }
            .let { memberRepository.findByMemberEmail(it.toString()) }
            ?.let { saveAuthentication(it) }

        filterChain.doFilter(request, response)

    }

    private fun saveAuthentication(member: Member) {
        val user: UserDetails = User.builder()
            .username(member.memberEmail)
            .password(member.memberPw)
            .roles(member.role.name)
            .build()

        val authentication: Authentication = UsernamePasswordAuthenticationToken(user, null, authoritiesMapper.mapAuthorities(user.authorities))

        val context: SecurityContext = SecurityContextHolder.createEmptyContext()
        context.authentication = authentication
        SecurityContextHolder.setContext(context)
    }

    private fun checkRefreshTokenAndReIssueAccessToken(response: HttpServletResponse, refreshToken: Optional<String>?) {
        memberRepository.findByRefreshToken(refreshToken.toString())?.let { jwtService.sendAccessToken(response, jwtService.createAccessToken(it.memberEmail)) }
    }

}
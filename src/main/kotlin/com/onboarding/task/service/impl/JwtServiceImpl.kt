package com.onboarding.task.service.impl

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.onboarding.task.repository.MemberRepository
import com.onboarding.task.service.JwtService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.collections.HashMap

@Transactional
@Service
class JwtServiceImpl (

    @Value("\${jwt.secret}")
    val secret : String,
    @Value("\${jwt.access.expiration}")
    val accessTokenValidityInSeconds: Long,
    @Value("\${jwt.refresh.expiration}")
    val refreshTokenValidityInSeconds: Long,
    @Value("\${jwt.access.header}")
    val accessHeader: String,
    @Value("\${jwt.refresh.header}")
    val refreshHeader: String,

    private val ACCESS_TOKEN_SUBJECT: String = "AccessToken",
    private val REFRESH_TOKEN_SUBJECT: String = "RefreshToken",
    private val USEREMAIL_CLAIM: String = "useremail",
    private val BEARER : String = "Bearer",

    private val memberRepository: MemberRepository,
//    private val objectMapper: ObjectMapper

    ) : JwtService {
    override fun createAccessToken(userEmail: String): String {
        return JWT.create()
            .withSubject(ACCESS_TOKEN_SUBJECT)
            .withExpiresAt(Date(System.currentTimeMillis() + accessTokenValidityInSeconds * 1000))
            .withClaim(USEREMAIL_CLAIM, userEmail)
            .sign(Algorithm.HMAC512(secret))
    }

    override fun createRefreshToken(): String{
        return JWT.create()
            .withSubject(REFRESH_TOKEN_SUBJECT)
            .withExpiresAt(Date(System.currentTimeMillis() + refreshTokenValidityInSeconds * 1000))
            .sign(Algorithm.HMAC512(secret))
    }

    override fun updateRefreshToken(userEmail: String, refreshToken: String) {
        val user = memberRepository.findByMemberEmail(userEmail) ?: throw Exception("사용자 정보 없음.")
        user.updateRefreshToken(refreshToken)
    }

    override fun destroyRefreshToken(userEmail: String) {
        val user = memberRepository.findByMemberEmail(userEmail) ?: throw Exception("사용자 정보 없음.")
        user.destroyRefreshToken()
    }

    override fun sendAccessAndRefreshToken(response: HttpServletResponse, accessToken: String, refreshToken: String) {
//        response.contentType = "application/json;charset=UTF-8"
        response.status = HttpServletResponse.SC_OK

        setAccessTokenHeader(response, accessToken)
        setRefreshTokenHeader(response, refreshToken)

        val tokenMap = HashMap<String, String>()
        tokenMap[ACCESS_TOKEN_SUBJECT] = accessToken
        tokenMap[REFRESH_TOKEN_SUBJECT] = refreshToken

//        val token = objectMapper.writeValueAsString(tokenMap)
//
//        response.writer.write(token)

    }

    override fun sendAccessToken(response: HttpServletResponse, accessToken: String) {
        response.status = HttpServletResponse.SC_OK

        setAccessTokenHeader(response, accessToken)

        val tokenMap = HashMap<String, String>()
        tokenMap[ACCESS_TOKEN_SUBJECT] = accessToken
    }

    override fun extractAccessToken(request: HttpServletRequest) : Optional<String>? {
//        return Optional.ofNullable(request.getHeader(accessHeader)).map { accessToken -> accessToken.replace(BEARER, "") }.orElse(null)
        return Optional.ofNullable(request.getHeader(accessHeader)).filter {
            accessToken -> accessToken.startsWith(BEARER)}
            .map { accessToken -> accessToken.replace(BEARER, "") }
    }

    override fun extractRefreshToken(request: HttpServletRequest) : Optional<String>? {
//        return Optional.ofNullable(request.getHeader(refreshHeader)).map { refreshToken -> refreshToken.replace(BEARER, "") }.orElse(null)
        return Optional.ofNullable(request.getHeader(refreshHeader)).filter {
            refreshToken -> refreshToken.startsWith(BEARER)
        }.map { refreshToken -> refreshToken.replace(BEARER, "") }
    }

    override fun extractMemberEmail(accessToken: String) : Optional<String>? {
//        return JWT.require(Algorithm.HMAC512(secret)).build().verify(accessToken).getClaim(USEREMAIL_CLAIM).asString()
        return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secret)).build().verify(accessToken).getClaim(USEREMAIL_CLAIM).asString())
    }

    override fun setAccessTokenHeader(response: HttpServletResponse, accessToken: String) {
        response.setHeader(accessHeader, accessToken)
    }

    override fun setRefreshTokenHeader(response: HttpServletResponse, refreshToken: String) {
        response.setHeader(refreshHeader, refreshToken)
    }

    override fun isTokenValid(token: String): Boolean {
        JWT.require(Algorithm.HMAC512(secret)).build().verify(token) ?: throw Exception("유효하지 않은 토큰입니다.")
        return true
    }
}
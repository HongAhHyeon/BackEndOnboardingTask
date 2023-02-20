//package com.onboarding.task.config.security
//
//import com.onboarding.task.config.security.principal.AdminPrincipal
//import com.onboarding.task.config.security.principal.Principal
//import io.jsonwebtoken.*
//import io.jsonwebtoken.security.Keys
//import io.jsonwebtoken.security.SignatureException
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
//import org.springframework.security.core.Authentication
//import org.springframework.security.core.GrantedAuthority
//import org.springframework.security.core.authority.AuthorityUtils
//import java.nio.charset.StandardCharsets
//import java.util.*
//
//abstract class BaseTokenProvider {
//    abstract fun getExpiredSeconds(): Long
//
//    protected fun generateToken(
//        issuer: String,
//        secretKey: String,
//        expiredSeconds: Long,
//        id: Long,
//        subject: String,
//        authorId: Long,
//    ): String {
//        try {
//            val now = Date()
//            val expiredAt = Date(now.time + (expiredSeconds * 1000))
//            val key = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))
//
//            val claims = Jwts.claims()
//
//            claims.setSubject(subject)
//                .setId(id.toString())
//                .setExpiration(expiredAt)
//                .setIssuedAt(now)
//                .setIssuer(issuer)
//
//            claims["authorId"] = authorId
//
//            return Jwts.builder()
//                .setClaims(claims)
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact()
//        } catch (e: Exception) {
//            throw Exception("토큰 생성에 실패했습니다.")
//        }
//    }
//
//    protected fun getAuthenticationFromToken(
//        secretKey: String,
//        token: String,
//        validAudience: String
//    ): Authentication {
//        val claimBody = innerGetClaimBodyFromJwt(secretKey, token)
//
//        val tokenId = try {
//            claimBody.id.toLong()
//        } catch (e: Exception) {
//            throw Exception("토큰 id 정보가 올바르지 않습니다.(not long type)")
//        }
//
//        if (tokenId < 1) {
//            throw Exception("토큰 id 정보가 올바르지 않습니다.")
//        }
//
//        val authorId = claimBody["authorId"]?.let { it.toString().toLong() } ?: -1
//
//        val principal = AdminPrincipal(
//            adminId = tokenId,
//            authorId = authorId,
//            userName = "admin:$tokenId"
//        )
//
//        return UsernamePasswordAuthenticationToken(
//            principal,
//            "",
//            innerGetSimpleAuthorities()
//        )
//    }
//
//    protected fun getAuthenticationIdFromToken(
//        secretKey: String,
//        token: String,
//        validAudience: String
//    ): Long {
//        val authentication = getAuthenticationFromToken(secretKey, token, validAudience)
//        val principal = authentication.principal as Principal
//
//        return principal.getId()
//    }
//
//
//    private fun innerGetClaimBodyFromJwt(
//        secretKey: String,
//        token: String,
//    ): Claims {
//        val key = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))
//        val parser = Jwts.parserBuilder()
//            .setSigningKey(key)
//            .build()
//
//        val claimBody = try {
//            parser.parseClaimsJws(token).body
//        } catch (e: Exception) {
//            when (e) {
//                is UnsupportedJwtException -> throw Exception("지원하지 않는 argument를 사용합니다.")
//                is MalformedJwtException -> throw Exception("토큰 문자열이 유효하지 않습니다.")
//                is SignatureException -> throw Exception("signature 값이 유효하지 않습니다.")
//                is IllegalArgumentException -> throw Exception("토큰 문자열이 유효하지 않습니다.(공백)")
//                is ExpiredJwtException -> throw Exception("토큰이 만료되었습니다.")
//                else -> throw Exception("토큰 인증 과정에서 오류가 발생했습니다.")
//            }
//        }
//
//        return claimBody
//    }
//}
//package com.onboarding.task.config.security
//
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.security.core.Authentication
//import org.springframework.stereotype.Component
//
//@Component
//class AdminAccessTokenProvider : BaseTokenProvider() {
//    @Value("\${authentication.issuer}")
//    private lateinit var issuer: String
//
//    @Value("\${authentication.admin.access-token.secret-key}")
//    private lateinit var tokenSecretKey: String
//
//    @Value("\${authentication.admin.access-token.expired-seconds}")
//    private lateinit var tokenExpiredSeconds: String
//
//    override fun getExpiredSeconds(): Long {
//        return tokenExpiredSeconds.toLong()
//    }
//
//    fun generateToken(id: Long, authorId: Long, subject: TokenSubjectEnum): String {
//        return super.generateToken(
//            issuer = issuer,
//            secretKey = tokenSecretKey,
//            expiredSeconds = getExpiredSeconds(),
//            id = id,
//            subject = subject.content,
//            authorId = authorId
//        )
//    }
//
//    // FIXME: 프론트엔드 로직 확인후 삭제 필요
//    fun generateShortToken(id: Long, authorId: Long, subject: TokenSubjectEnum): String {
//        return super.generateToken(
//            issuer = issuer,
//            secretKey = tokenSecretKey,
//            expiredSeconds = 20,
//            id = id,
//            subject = subject.content,
//            authorId = authorId
//        )
//    }
//
//}
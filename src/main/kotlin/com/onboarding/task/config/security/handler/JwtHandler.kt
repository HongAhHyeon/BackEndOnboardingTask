//package com.onboarding.task.handler
//
//import io.jsonwebtoken.Claims
//import io.jsonwebtoken.Jws
//import io.jsonwebtoken.Jwts
//import io.jsonwebtoken.SignatureAlgorithm
//import org.springframework.stereotype.Component
//import java.util.Date
//
//@Component
//class JwtHandler (
//    private var type : String = "Bearer"
//){
//    fun createToken(encodedKey: String, subject: String, maxAgeSeconds: Long) : String {
//        var now : Date = Date()
//        return type + Jwts.builder()
//            .setSubject(subject)
//            .setIssuedAt(now)
//            .setExpiration(Date(now.time + maxAgeSeconds * 1000L))
//            .signWith(SignatureAlgorithm.HS256, encodedKey)
//            .compact();
//    }
//
//    fun extractSubject(encodeKey: String, token: String) : String {
//        return parse(encodeKey, token).body.subject
//    }
//
//    fun validate(encodeKey: String, token: String) : Boolean {
//        parse(encodeKey, token)
//        return true
//    }
//
//    fun parse(key: String, token: String) : Jws<Claims> {
//        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(untype(token))
//    }
//
//    fun untype(token: String) : String {
//        return token.substring(type.length)
//    }
//}
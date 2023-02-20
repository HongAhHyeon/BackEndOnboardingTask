package com.onboarding.task.service

import com.onboarding.task.repository.MemberRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class LoginService (

    private val memberRepository: MemberRepository

) : UserDetailsService {

    override fun loadUserByUsername(userEmail: String): UserDetails {
        val member = memberRepository.findByMemberEmail(userEmail) ?: throw IllegalArgumentException("사용자 정보 없음.")
        return User.builder().username(member.memberEmail)
            .password(member.memberPw)
            .roles(member.role.name)
            .build()
    }
}
package com.onboarding.task.service

import com.onboarding.task.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class SignInService (

    private val userRepository: UserRepository

) : UserDetailsService {

    override fun loadUserByUsername(userEmail: String): UserDetails {
        val user = userRepository.findByUserEmail(userEmail) ?: throw IllegalArgumentException("사용자 정보 없음.")
        return User.builder().username(user.userEmail)
            .password(user.userPw)
            .roles(user.role!!.name)
            .build()
    }

}
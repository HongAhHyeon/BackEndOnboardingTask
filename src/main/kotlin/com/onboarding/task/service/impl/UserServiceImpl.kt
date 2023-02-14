package com.onboarding.task.service.impl

import com.onboarding.task.domain.User
import com.onboarding.task.dto.request.UserCreateDto
import com.onboarding.task.repository.UserRepository
import com.onboarding.task.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    override fun createUser(createDto: UserCreateDto) {
        if(userRepository.findByUserEmail(createDto.userEmail) != null) {
            throw IllegalArgumentException("이미 있는 이메일입니다.")
        }
        val newUser = User(createDto.userEmail, createDto.userPw)
        userRepository.save(newUser)
    }

    override fun deleteUser(id: Long) {
        var user = userRepository.findById(id)
    }

    override fun signInUser(userEmail: String, userPw: String) {
    }
}
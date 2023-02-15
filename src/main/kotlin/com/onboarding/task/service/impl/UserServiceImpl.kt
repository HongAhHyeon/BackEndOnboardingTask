package com.onboarding.task.service.impl

import com.onboarding.task.entity.User
import com.onboarding.task.dto.request.UserCreateDto
import com.onboarding.task.dto.response.UserDto
import com.onboarding.task.repository.UserRepository
import com.onboarding.task.service.UserService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    @Transactional
    override fun createUser(dto: UserCreateDto) {
        if(userRepository.existsByUserEmail(dto.userEmail)) {
            throw IllegalArgumentException("이미 사용 중인 이메일입니다.")
        }

        if(userRepository.existsByUserName(dto.userName)) {
            throw IllegalArgumentException("이미 사용 중인 이름입니다.")
        }

        val newUser = User(dto.userEmail, dto.userPw, dto.userName)
        userRepository.save(newUser)
    }

    @Transactional
    override fun deleteUser(id: Long) {
        val user = userRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("사용자 정보 없음.")
        userRepository.delete(user)
    }

    @Transactional
    override fun signInUser(userEmail: String, userPw: String): UserDto {
        val user = userRepository.findByUserEmailAndUserPw(userEmail, userPw) ?: throw IllegalArgumentException("이메일 혹은 비밀번호를 확인해 주세요.")
        return UserDto.of(user)

    }
}
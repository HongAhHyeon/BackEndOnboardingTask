package com.onboarding.task.service.impl

import com.onboarding.task.dto.request.UserInfoUpdateRequest
import com.onboarding.task.dto.request.UserSignInRequest
import com.onboarding.task.entity.User
import com.onboarding.task.dto.request.UserSignUpRequest
import com.onboarding.task.dto.response.UserDto
import com.onboarding.task.dto.response.UserInfoResponse
import com.onboarding.task.repository.UserRepository
import com.onboarding.task.service.TokenService
import com.onboarding.task.service.UserService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenService: TokenService
) : UserService {

    @Transactional
    override fun signUpUser(req: UserSignUpRequest) {
        validateUserInfo(req.userEmail, req.userName)

        val newUser = req.toEntity(passwordEncoder)
        userRepository.save(newUser)
    }

    @Transactional
    override fun deleteUser(id: Long) {
        val user = userRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("사용자 정보 없음.")
        userRepository.delete(user)
    }

    @Transactional
    override fun signInUser(req: UserSignInRequest): UserDto {
        val user = userRepository.findByUserEmail(req.userEmail) ?: throw IllegalArgumentException("사용자 정보 없음.")
        validatePassword(req, user)
        val subject = createSubject(user)
        val accessToken = tokenService.createAccessToken(subject)
        val refreshToken = tokenService.createRefreshToken(subject)
        return UserDto.of(user)

    }

    override fun updateUserInfo(req: UserInfoUpdateRequest) {
        val user = userRepository.findByUserEmail(req.userEmail)
        validateUserInfo(req.userEmail, req.userName)
        user!!.updateUserName(req.userName)

    }

    override fun updateUserPw(checkPw: String, newPw: String) {

    }

    override fun getUserInfo(id: Long): UserInfoResponse {
        val user = userRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("사용자 정보 없음.")
        return UserInfoResponse.of(user)
    }

    private fun validateUserInfo(userEmail: String, userName: String) {
        if(userRepository.existsByUserEmail(userEmail)) {
            throw IllegalArgumentException("이미 사용 중인 이메일입니다.")
        }

        if(userRepository.existsByUserName(userName)) {
            throw IllegalArgumentException("이미 사용 중인 이름입니다.")
        }
    }
    private fun validatePassword(req : UserSignInRequest, user: User) {
        if(!passwordEncoder.matches(req.userPw, user.userPw)) throw IllegalArgumentException("비밀번호 오류")
    }

    private fun createSubject(user: User) : String {
        return user.id.toString()
    }
}
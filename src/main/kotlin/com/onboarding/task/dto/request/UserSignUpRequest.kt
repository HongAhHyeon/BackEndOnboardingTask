package com.onboarding.task.dto.request

import com.onboarding.task.entity.User
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import org.springframework.security.crypto.password.PasswordEncoder

data class UserSignUpRequest(
    @NotNull
    @Email
    var userEmail: String,
    @NotNull
    var userPw: String,
    @NotNull
    var userName: String
) {

    fun toEntity(encoder : PasswordEncoder) : User {
        return User (
            userEmail = userEmail,
            userPw = encoder.encode(userPw),
            userName = userName,
        )
    }
}
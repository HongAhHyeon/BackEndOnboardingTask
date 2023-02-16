package com.onboarding.task.dto.request

import com.onboarding.task.entity.User
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import org.springframework.security.crypto.password.PasswordEncoder

data class UserInfoUpdateRequest(
    @NotNull
    @Email
    var userEmail: String,
    @NotNull
    var userName: String
) {
}
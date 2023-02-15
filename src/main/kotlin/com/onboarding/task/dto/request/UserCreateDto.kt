package com.onboarding.task.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull

data class UserCreateDto(
    @NotNull
    @Email
    var userEmail: String,
    @NotNull
    var userPw: String,
    @NotNull
    var userName: String
) {
}
package com.onboarding.task.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull

data class MemberSignInRequest(
    @NotNull
    @Email
    var userEmail: String,
    @NotNull
    var userPw: String,
) {
}
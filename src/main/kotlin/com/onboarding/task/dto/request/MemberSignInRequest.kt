package com.onboarding.task.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull

data class MemberSignInRequest(
    @NotNull
    @Email
    var memberEmail: String,
    @NotNull
    var memberPw: String,
) {
    constructor() : this("", "")
}
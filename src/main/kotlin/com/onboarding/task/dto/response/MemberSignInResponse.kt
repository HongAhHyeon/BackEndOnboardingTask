package com.onboarding.task.dto.response

data class MemberSignInResponse (
    val accessToken: String,
    val refreshToken: String
) {
}
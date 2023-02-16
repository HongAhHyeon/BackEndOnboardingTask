package com.onboarding.task.dto.response

data class UserSignInResponse (
    val accessToken: String,
    val refreshToken: String
) {
}
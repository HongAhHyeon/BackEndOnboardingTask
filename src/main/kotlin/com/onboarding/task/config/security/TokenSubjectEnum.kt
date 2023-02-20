package com.onboarding.task.config.security

enum class TokenSubjectEnum(
    val content: String
) {
    signIn("signIn"),
    refresAccessToken("refreshAccessToken")
    ;
}
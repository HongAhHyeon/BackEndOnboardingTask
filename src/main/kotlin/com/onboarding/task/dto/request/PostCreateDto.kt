package com.onboarding.task.dto.request

import com.onboarding.task.entity.User

data class PostCreateDto(
    val user: User,
    val title: String,
    val content: String,
    ) {
}
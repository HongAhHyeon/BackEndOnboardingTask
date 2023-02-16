package com.onboarding.task.dto.request

import com.onboarding.task.entity.Post
import com.onboarding.task.entity.User
import jakarta.validation.constraints.NotBlank
import org.springframework.security.crypto.password.PasswordEncoder

data class PostCreateRequest(
    @NotBlank(message = "제목을 입력해 주세요.")
    val title: String,
    @NotBlank(message = "내용을 입력해 주세요")
    val content: String,
    ) {
    fun toEntity() : Post {
        return Post (
            title = title,
            content = content
        )
    }
}
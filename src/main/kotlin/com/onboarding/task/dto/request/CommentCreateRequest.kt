package com.onboarding.task.dto.request

import com.onboarding.task.entity.Comment
import com.onboarding.task.entity.Post
import com.onboarding.task.entity.User
import jakarta.validation.constraints.NotBlank
import org.springframework.security.crypto.password.PasswordEncoder

data class CommentCreateRequest(
    @NotBlank(message = "내용을 입력해 주세요")
    val content: String,
    ) {
    fun toEntity() : Comment {
        return Comment (
            content = content
        )
    }
}
package com.onboarding.task.dto.request

import com.onboarding.task.entity.Comment
import jakarta.validation.constraints.NotBlank

data class CommentCreateRequest(
    @NotBlank(message = "내용을 입력해 주세요")
    val content: String,
    ) {
    fun toEntity() : Comment {
        return Comment (
            content = content,
        )
    }

    constructor() : this("")
}
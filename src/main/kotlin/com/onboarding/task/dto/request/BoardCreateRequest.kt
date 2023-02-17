package com.onboarding.task.dto.request

import com.onboarding.task.entity.Board
import jakarta.validation.constraints.NotBlank

data class BoardCreateRequest(
    @NotBlank(message = "제목을 입력해 주세요.")
    val title: String,
    @NotBlank(message = "내용을 입력해 주세요")
    val content: String,
    ) {
    fun toEntity() : Board {
        return Board (
            title = title,
            content = content
        )
    }
}
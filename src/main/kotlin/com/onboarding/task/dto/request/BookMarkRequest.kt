package com.onboarding.task.dto.request

import com.onboarding.task.entity.Board
import com.onboarding.task.entity.BookMark
import jakarta.validation.constraints.NotBlank

data class BookMarkRequest(
    val boardId: Long,
    val memberId: Long
    ) {

}
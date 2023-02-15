package com.onboarding.task.dto.response

import com.onboarding.task.entity.Comment
import java.time.LocalDateTime

data class PostDto(
    val id: Long,
    val userName: String,
    val title: String,
    val content: String,
    val postDate: LocalDateTime,
    val modifyDate: LocalDateTime,
    val comments: MutableCollection<Comment>
) {
}
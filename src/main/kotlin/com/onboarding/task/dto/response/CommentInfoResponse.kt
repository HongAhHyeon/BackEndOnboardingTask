package com.onboarding.task.dto.response

import com.onboarding.task.entity.Comment
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime


data class CommentInfoResponse (
    val boardId: Long,
    val commentId: Long,
    val content: String,
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    val createdAt: LocalDateTime,
    val writerDto: MemberInfoResponse,
) {

    companion object {
        fun of(comment: Comment): CommentInfoResponse {
            return CommentInfoResponse(
                boardId = comment.board!!.id!!,
                commentId = comment.id!!,
                content = comment.content,
                createdAt = comment.createdAt,
                writerDto = MemberInfoResponse.of(comment.writer!!)
            )
        }
    }
}
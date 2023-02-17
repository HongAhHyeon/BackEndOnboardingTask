package com.onboarding.task.dto.response

import com.onboarding.task.entity.Comment


data class CommentInfoResponse (
    val postId: Long,
    val commentId: Long,
    val content: String,
    val writerDto: MemberInfoResponse,
) {

    companion object {
        fun of(comment: Comment): CommentInfoResponse {
            return CommentInfoResponse(
                postId = comment.board!!.id!!,
                commentId = comment.id!!,
                content = comment.content,
                writerDto = MemberInfoResponse.of(comment.writer!!),
            )
        }
    }
}
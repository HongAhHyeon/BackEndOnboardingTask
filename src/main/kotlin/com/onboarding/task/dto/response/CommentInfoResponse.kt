package com.onboarding.task.dto.response

import com.onboarding.task.entity.Comment


data class CommentInfoResponse (
    val boardId: Long,
    val commentId: Long,
    val content: String,
    val createdAt: String,
    val writerDto: MemberInfoResponse,
) {

    companion object {
        fun of(comment: Comment): CommentInfoResponse {
            return CommentInfoResponse(
                boardId = comment.board!!.id!!,
                commentId = comment.id!!,
                content = comment.content,
                createdAt = comment.createdAt.toString(),
                writerDto = MemberInfoResponse.of(comment.writer!!)
            )
        }
    }
}
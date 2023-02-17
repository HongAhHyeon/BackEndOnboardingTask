package com.onboarding.task.dto.response

import com.onboarding.task.entity.Board


data class BoardInfoResponse (
    val postId: Long,
    val title: String,
    val content: String,
    val writerDto: MemberInfoResponse,
    val comments: MutableList<CommentInfoResponse>
) {

    companion object {
        fun of(board: Board): BoardInfoResponse {
            return BoardInfoResponse(
                postId = board.id!!,
                title = board.title,
                content = board.content,
                writerDto = MemberInfoResponse.of(board.writer!!),
                comments = board.comments.stream().map { comment -> CommentInfoResponse.of(comment) }.toList()
            )
        }
    }
}
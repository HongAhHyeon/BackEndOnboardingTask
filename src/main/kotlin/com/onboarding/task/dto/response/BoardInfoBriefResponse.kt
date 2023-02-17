package com.onboarding.task.dto.response

import com.onboarding.task.entity.Board


data class BoardInfoBriefResponse (
    val title: String,
    val content: String,
    val writerName: String,
    val createdDate: String
) {

    companion object {
        fun of(board: Board): BoardInfoBriefResponse {
            return BoardInfoBriefResponse(
                title = board.title,
                content = board.content,
                writerName = board.writer!!.memberName,
                createdDate = board.createdAt.toString()
            )
        }
    }
}
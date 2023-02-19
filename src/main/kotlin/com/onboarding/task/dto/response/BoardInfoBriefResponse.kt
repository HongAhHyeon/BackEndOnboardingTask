package com.onboarding.task.dto.response

import com.onboarding.task.entity.Board


data class BoardInfoBriefResponse (
    val id: Long,
    val title: String,
    val writerName: String,
    val createdDate: String
) {

    companion object {
        fun of(board: Board): BoardInfoBriefResponse {
            return BoardInfoBriefResponse(
                id = board.id!!,
                title = board.title,
                writerName = board.writer!!.memberName,
                createdDate = board.createdAt.toString()
            )
        }
    }
}
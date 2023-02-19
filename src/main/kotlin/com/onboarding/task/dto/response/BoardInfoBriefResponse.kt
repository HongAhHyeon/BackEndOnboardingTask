package com.onboarding.task.dto.response

import com.onboarding.task.entity.Board
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime


data class BoardInfoBriefResponse (
    val id: Long,
    val title: String,
    val writerName: String,
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    val createdDate: LocalDateTime
) {

    companion object {
        fun of(board: Board): BoardInfoBriefResponse {
            return BoardInfoBriefResponse(
                id = board.id!!,
                title = board.title,
                writerName = board.writer!!.memberName,
                createdDate = board.createdAt
            )
        }
    }
}
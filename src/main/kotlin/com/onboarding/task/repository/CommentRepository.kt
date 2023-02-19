package com.onboarding.task.repository

import com.onboarding.task.dto.response.CommentInfoResponse
import com.onboarding.task.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {

    fun findCommentsByBoardId(boardId: Long) : List<Comment>
}
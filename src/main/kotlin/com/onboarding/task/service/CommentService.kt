package com.onboarding.task.service

import com.onboarding.task.dto.request.CommentCreateRequest
import com.onboarding.task.dto.request.CommentUpdateRequest
import com.onboarding.task.dto.response.CommentInfoResponse
import com.onboarding.task.entity.Comment

interface CommentService {

    fun createComment(boardId: Long, req: CommentCreateRequest)

    fun updateComment(id: Long, req: CommentUpdateRequest)

    fun getComment(id: Long) : Comment

    fun getComments(boardId: Long) : MutableList<CommentInfoResponse>

    fun deleteComment(id: Long)

}
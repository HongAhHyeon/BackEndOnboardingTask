package com.onboarding.task.service

import com.onboarding.task.dto.request.CommentCreateRequest
import com.onboarding.task.dto.request.CommentUpdateRequest
import com.onboarding.task.entity.Comment

interface CommentService {

    fun createComment(postId: Long, req: CommentCreateRequest)

    fun updateComment(id: Long, req: CommentUpdateRequest)

    fun getComment(id: Long) : Comment

    fun getComments() : List<Comment>

    fun deleteComment(id: Long)

}
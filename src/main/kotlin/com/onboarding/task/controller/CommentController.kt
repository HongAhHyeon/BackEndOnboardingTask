package com.onboarding.task.controller

import com.onboarding.task.dto.request.CommentCreateRequest
import com.onboarding.task.dto.request.CommentUpdateRequest
import com.onboarding.task.service.CommentService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller

class CommentController (
    private val commentService: CommentService
){

    @PostMapping("/{boardId}/comments/new")
    fun create(@PathVariable("boardId") boardId: Long, req: CommentCreateRequest) {
        commentService.createComment(boardId, req)
    }

    @PutMapping("/comments/{commentId}")
    fun update(@PathVariable("commentId") commentId: Long, req: CommentUpdateRequest) {
        commentService.updateComment(commentId, req)
    }

    @DeleteMapping("/comments/{commentId}")
    fun delete(@PathVariable("commentId") commentId: Long) {
        commentService.deleteComment(commentId)
    }

}
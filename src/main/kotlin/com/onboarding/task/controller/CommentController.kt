package com.onboarding.task.controller

import com.onboarding.task.dto.request.CommentCreateRequest
import com.onboarding.task.dto.request.CommentUpdateRequest
import com.onboarding.task.dto.response.CommentInfoResponse
import com.onboarding.task.service.CommentService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Controller
@RequestMapping("/boards/{boardId}/comments")
class CommentController (
    private val commentService: CommentService
){

    @PostMapping("/new")
    fun create(@PathVariable("boardId") boardId: Long, req: CommentCreateRequest) : String {
        commentService.createComment(boardId, req)
        return "redirect:/boards/$boardId"
    }

    @PutMapping("/{commentId}")
    fun update(@PathVariable("boardId") boardId: Long, @PathVariable("commentId") commentId: Long, req: CommentUpdateRequest) {
        commentService.updateComment(commentId, req)
    }

    @DeleteMapping("/{commentId}")
    fun delete(@PathVariable("boardId") boardId: Long, @PathVariable("commentId") commentId: Long) {
        commentService.deleteComment(commentId)
    }

    @GetMapping("")
    fun getComments(@PathVariable("boardId") boardId: Long) :ResponseEntity<MutableList<CommentInfoResponse>> {
        return ResponseEntity.ok(commentService.getComments(boardId))
    }

}
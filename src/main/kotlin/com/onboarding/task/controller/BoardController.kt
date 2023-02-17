package com.onboarding.task.controller

import com.onboarding.task.dto.request.BoardCreateRequest
import com.onboarding.task.dto.request.BoardUpdateRequest
import com.onboarding.task.dto.response.BoardInfoResponse
import com.onboarding.task.dto.response.BoardPagingResponse
import com.onboarding.task.entity.condition.BoardSearchCondition
import com.onboarding.task.service.BoardService
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/boards")
class BoardController(
    private val boardService: BoardService
) {

    @PostMapping("/new")
    fun create(@Valid @ModelAttribute req: BoardCreateRequest) {
        boardService.createBoard(req)
    }

    @PutMapping("/{boardId}")
    fun update(@PathVariable("boardId") boardId: Long, @ModelAttribute req: BoardUpdateRequest) {
        boardService.updateBoard(req)
    }

    @DeleteMapping("/{boardId}")
    fun delete(@PathVariable("boardId") boardId: Long) {
        boardService.deleteBoard(boardId)
    }

    @GetMapping("/{boardId}")
    fun getBoardInfo(@PathVariable("boardId") boardId: Long) : ResponseEntity<BoardInfoResponse> {
        return ResponseEntity.ok(boardService.getBoard(boardId))
    }

    @GetMapping("")
    fun search(pageable: Pageable, @ModelAttribute postSearchCondition: BoardSearchCondition): ResponseEntity<BoardPagingResponse> {
        return ResponseEntity.ok(boardService.getBoards(pageable, postSearchCondition))
    }


}
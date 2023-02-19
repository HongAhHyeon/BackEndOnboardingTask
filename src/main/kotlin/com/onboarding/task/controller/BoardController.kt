package com.onboarding.task.controller

import com.onboarding.task.dto.request.BoardCreateRequest
import com.onboarding.task.dto.request.BoardUpdateRequest
import com.onboarding.task.dto.request.CommentCreateRequest
import com.onboarding.task.dto.request.MemberSignUpRequest
import com.onboarding.task.dto.response.BoardInfoResponse
import com.onboarding.task.dto.response.BoardPagingResponse
import com.onboarding.task.entity.condition.BoardSearchCondition
import com.onboarding.task.service.BoardService
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class BoardController(
    private val boardService: BoardService
) {

    @GetMapping("/boards/new")
    fun createBoardForm(model: Model) : String {
        model.addAttribute("boardCreateRequest", BoardCreateRequest())
        return "boards/createBoardForm"
    }

    @PostMapping("/boards/new")
    fun create(@Valid @ModelAttribute req: BoardCreateRequest) : String {
        boardService.createBoard(req)
        return "redirect:/boards"
    }

    @GetMapping("/boards/{boardId}/edit")
    fun updateBoardForm(@PathVariable("boardId") boardId: Long, model: Model) : String {
        val board = boardService.getBoard(boardId)
        model.addAttribute("board", board)
        return "boards/updateBoardForm"
    }

    @PostMapping("/boards/{boardId}/edit")
    fun update(@PathVariable("boardId") boardId: Long, @ModelAttribute req: BoardUpdateRequest) : String {
        boardService.updateBoard(req)
        return "redirect:/boards"
    }

    @DeleteMapping("/boards/{boardId}")
    fun delete(@PathVariable("boardId") boardId: Long) : String {
        boardService.deleteBoard(boardId)
        return "redirect:/boards"
    }

    @GetMapping("/boards/{boardId}")
    fun getBoardInfo(@PathVariable("boardId") boardId: Long, model: Model) : String {
        val board = boardService.getBoard(boardId)
        model.addAttribute("board", board)
        model.addAttribute("commentCreateRequest", CommentCreateRequest())
        return "boards/boardDetail"
    }

//    @GetMapping("")
//    fun search(pageable: Pageable, @ModelAttribute postSearchCondition: BoardSearchCondition): ResponseEntity<BoardPagingResponse> {
//        return ResponseEntity.ok(boardService.getBoards(pageable, postSearchCondition))
//    }

    @GetMapping("/boards")
    fun getBoards(model: Model) : String {
        val boards = boardService.getBoardsSimple()
        model.addAttribute("boards", boards)
        return "boards/boardList"
    }


}
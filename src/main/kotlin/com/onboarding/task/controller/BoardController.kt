package com.onboarding.task.controller

import com.onboarding.task.dto.request.*
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
@RequestMapping("/boards")

class BoardController(
    private val boardService: BoardService
) {

    @GetMapping("/new")
    fun createBoardForm(model: Model) : String {
        model.addAttribute("boardCreateRequest", BoardCreateRequest())
        return "boards/createBoardForm"
    }

    @PostMapping("/new")
    fun create(@Valid @ModelAttribute req: BoardCreateRequest) : String {
        boardService.createBoard(req)
        return "redirect:/boards"
    }

    @GetMapping("/{id}/edit")
    fun updateBoardForm(@PathVariable("id") id: Long, model: Model) : String {
        val board = boardService.getBoard(id)
        model.addAttribute("board", board)
        return "boards/updateBoardForm"
    }

    @PostMapping("/{id}/edit")
    fun update(@PathVariable("id") id: Long, @ModelAttribute req: BoardUpdateRequest) : String {
        boardService.updateBoard(req)
        return "redirect:/boards/$id"
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long) : String {
        boardService.deleteBoard(id)
        return "redirect:/boards"
    }

    @GetMapping("/{id}")
    fun getBoardInfo(@PathVariable("id") id: Long, model: Model) : String {
        val board = boardService.getBoard(id)
        model.addAttribute("board", board)
        model.addAttribute("commentCreateRequest", CommentCreateRequest())
        model.addAttribute("bookmark", BookMarkRequest())
        return "boards/boardDetail"
    }

//    @GetMapping("")
//    fun search(pageable: Pageable, @ModelAttribute postSearchCondition: BoardSearchCondition): ResponseEntity<BoardPagingResponse> {
//        return ResponseEntity.ok(boardService.getBoards(pageable, postSearchCondition))
//    }

    @GetMapping
    fun getBoards(model: Model) : String {
        val boards = boardService.getBoardsSimple()
        model.addAttribute("boards", boards)
        return "boards/boardList"
    }


}
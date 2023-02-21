package com.onboarding.task.controller

import com.onboarding.task.dto.request.*
import com.onboarding.task.dto.response.BoardInfoResponse
import com.onboarding.task.dto.response.BoardPagingResponse
import com.onboarding.task.entity.condition.BoardSearchCondition
import com.onboarding.task.service.BoardService
import com.onboarding.task.service.BookMarkService
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@RestController
class BookMarkController(
    private val bookMarkService: BookMarkService
) {

    @PostMapping("/bookmark/mark")
    fun markBoard(@RequestBody req: BookMarkRequest) {
        bookMarkService.markBoard(req)
//        return "redirect:/boards/${req.boardId}"
    }

    @PutMapping("/bookmark/unmark")
    fun unmarkBoard(@RequestBody req: BookMarkRequest) {
        bookMarkService.unmarkBoard(req)
//        return "redirect:/boards/${req.boardId}"
    }


}
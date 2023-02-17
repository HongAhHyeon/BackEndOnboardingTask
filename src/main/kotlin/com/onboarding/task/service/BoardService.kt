package com.onboarding.task.service

import com.onboarding.task.dto.request.BoardCreateRequest
import com.onboarding.task.dto.request.BoardUpdateRequest
import com.onboarding.task.dto.response.BoardInfoResponse
import com.onboarding.task.dto.response.BoardPagingResponse
import com.onboarding.task.entity.condition.BoardSearchCondition
import org.springframework.data.domain.Pageable

interface BoardService {

    fun createBoard(req: BoardCreateRequest)

    fun updateBoard(dto : BoardUpdateRequest)

    fun deleteBoard(id: Long)

    fun getBoard(id: Long) : BoardInfoResponse

    fun getBoards(pageable: Pageable, boardSearchCondition: BoardSearchCondition): BoardPagingResponse
}
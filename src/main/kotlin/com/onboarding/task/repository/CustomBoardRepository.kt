package com.onboarding.task.repository

import com.onboarding.task.dto.response.BoardInfoBriefResponse
import com.onboarding.task.dto.response.BoardInfoResponse
import com.onboarding.task.entity.Board
import com.onboarding.task.entity.condition.BoardSearchCondition
import org.springframework.data.domain.Page

interface CustomBoardRepository {

    fun search(boardSearchCondition: BoardSearchCondition, pageable: org.springframework.data.domain.Pageable) : Page<Board>

    fun getBoardsBrief() : MutableList<BoardInfoBriefResponse>

    fun getMyBoards() : MutableList<BoardInfoBriefResponse>

    fun getMyBookMark() : MutableList<BoardInfoBriefResponse>

}
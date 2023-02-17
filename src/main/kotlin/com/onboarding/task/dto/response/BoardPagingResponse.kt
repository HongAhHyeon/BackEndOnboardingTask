package com.onboarding.task.dto.response

import com.onboarding.task.entity.Board
import org.springframework.data.domain.Page

data class BoardPagingResponse(
    val totalPageCount: Int,
    val currentPageCount: Int,
    val totalElementCount: Long,
    val currentPageElementCount: Int,

    val briefPosts: MutableList<BoardInfoBriefResponse.Companion>


){

    companion object {
        fun of(searchResults: Page<Board>): BoardPagingResponse {
            return BoardPagingResponse(
                totalPageCount = searchResults.totalPages,
                currentPageCount = searchResults.number,
                totalElementCount = searchResults.totalElements,
                currentPageElementCount = searchResults.numberOfElements,
                briefPosts = searchResults.content.stream().map { BoardInfoBriefResponse }.toList()

            )
        }
    }

}
package com.onboarding.task.repository

import com.onboarding.task.entity.BookMark
import com.onboarding.task.enum.BookMarkStatus
import org.springframework.data.jpa.repository.JpaRepository

interface BookMarkRepository : JpaRepository<BookMark, Long> {

    fun findByBoardIdAndMemberIdAndStatus(boardId: Long, memberId: Long, status: BookMarkStatus) : BookMark?
}
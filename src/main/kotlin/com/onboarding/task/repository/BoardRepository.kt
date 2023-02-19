package com.onboarding.task.repository

import com.onboarding.task.entity.Board
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository : JpaRepository<Board, Long>, CustomBoardRepository {

    @EntityGraph(attributePaths = ["writer"])
    fun findWithWriterById(id: Long) : Board?
}
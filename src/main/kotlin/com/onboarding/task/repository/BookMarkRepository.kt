package com.onboarding.task.repository

import com.onboarding.task.entity.BookMark
import org.springframework.data.jpa.repository.JpaRepository

interface BookMarkRepository : JpaRepository<BookMark, Long> {
}
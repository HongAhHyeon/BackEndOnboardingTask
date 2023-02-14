package com.onboarding.task.repository

import com.onboarding.task.domain.BookMark
import org.springframework.data.jpa.repository.JpaRepository

interface BookMarkRepository : JpaRepository<BookMark, Long> {
}
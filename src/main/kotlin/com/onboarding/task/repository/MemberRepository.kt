package com.onboarding.task.repository

import com.onboarding.task.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member, Long> {

    fun existsByMemberEmail(memberEmail: String): Boolean
    fun existsByMemberName(memberName: String): Boolean
    fun findByMemberEmail(memberEmail: String): Member?
    fun findByRefreshToken(refreshToken: String?) : Member?

}
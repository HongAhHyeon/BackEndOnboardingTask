package com.onboarding.task.dto.response

import com.onboarding.task.entity.Member

data class UserDto(
    val id: Long,
    val userEmail: String,
    val userPw: String,
    val userName: String
) {

    companion object {
        fun of(member: Member): UserDto {
            return UserDto(
                id = member.id!!,
                userEmail = member.memberEmail,
                userPw = member.memberPw,
                userName = member.memberName
            )
        }
    }

}
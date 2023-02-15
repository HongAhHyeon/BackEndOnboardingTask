package com.onboarding.task.dto.response

import com.onboarding.task.entity.User

data class UserDto(
    val id: Long,
    val userEmail: String,
    val userPw: String,
    val userName: String
) {

    companion object {
        fun of(user: User): UserDto {
            return UserDto(
                id = user.id!!,
                userEmail = user.userEmail,
                userPw = user.userPw,
                userName = user.userName
            )
        }
    }

}
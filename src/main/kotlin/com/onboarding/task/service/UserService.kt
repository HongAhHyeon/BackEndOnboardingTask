package com.onboarding.task.service

import com.onboarding.task.dto.request.UserCreateDto
import com.onboarding.task.dto.response.UserDto

interface UserService {

  fun createUser(dto: UserCreateDto)

  fun deleteUser(id: Long)

  fun signInUser(userEmail: String, userPw: String) : UserDto

}
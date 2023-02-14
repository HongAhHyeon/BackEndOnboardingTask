package com.onboarding.task.service

import com.onboarding.task.dto.request.UserCreateDto

interface UserService {

  fun createUser(createDto: UserCreateDto)

  fun deleteUser(id: Long)

  fun signInUser(userEmail: String, userPw: String)

}
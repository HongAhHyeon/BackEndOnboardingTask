package com.onboarding.task.service

import com.onboarding.task.dto.request.UserInfoUpdateRequest
import com.onboarding.task.dto.request.UserSignInRequest
import com.onboarding.task.dto.request.UserSignUpRequest
import com.onboarding.task.dto.response.UserDto
import com.onboarding.task.dto.response.UserInfoResponse

interface UserService {

  fun signUpUser(req: UserSignUpRequest)

  fun deleteUser(id: Long)

  fun signInUser(req : UserSignInRequest) : UserDto

  fun updateUserInfo(req: UserInfoUpdateRequest)

  fun updateUserPw(checkPw: String, newPw: String)

  fun getUserInfo(id: Long) : UserInfoResponse

}
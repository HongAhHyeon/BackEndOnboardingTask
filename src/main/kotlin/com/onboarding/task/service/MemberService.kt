package com.onboarding.task.service

import com.onboarding.task.dto.request.MemberInfoUpdateRequest
import com.onboarding.task.dto.request.MemberSignInRequest
import com.onboarding.task.dto.request.MemberSignUpRequest
import com.onboarding.task.dto.response.UserDto
import com.onboarding.task.dto.response.MemberInfoResponse

interface MemberService {

  fun signUpUser(req: MemberSignUpRequest)

  fun deleteUser(id: Long)

  fun signInUser(req : MemberSignInRequest) : UserDto

  fun updateUserInfo(req: MemberInfoUpdateRequest)

  fun updateUserPw(checkPw: String, newPw: String)

  fun getUserInfo(id: Long) : MemberInfoResponse

}
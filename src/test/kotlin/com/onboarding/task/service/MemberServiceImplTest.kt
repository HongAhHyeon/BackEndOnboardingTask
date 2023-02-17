package com.onboarding.task.service

import com.onboarding.task.dto.request.MemberSignUpRequest
import com.onboarding.task.repository.MemberRepository
import com.onboarding.task.service.impl.MemberServiceImpl
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MemberServiceImplTest @Autowired constructor(
    private val memberServiceImpl: MemberServiceImpl,
    private val memberRepository: MemberRepository,
) {
    @Test
    fun saveUserTest() {
        val dto1 = MemberSignUpRequest("aa@aa.com", "1111", "aa")
        val dto2 = MemberSignUpRequest("aa1@aa.com", "1234", "aa")

//        userServiceImpl.createUser(dto1)
//        userServiceImpl.createUser(dto2)

    }

//    @Test
//    fun deleteUserTest() {
//        val dto1 = UserCreateDto("aa@aa.com", "1111")
//        val dto2 = UserCreateDto("2a@aa.com", "1111")
//
//        userServiceImpl.createUser(dto1)
//        userServiceImpl.createUser(dto2)
//        userServiceImpl.deleteUser(2L)
//
//    }


}
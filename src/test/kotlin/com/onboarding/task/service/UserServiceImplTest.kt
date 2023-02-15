package com.onboarding.task.service

import com.onboarding.task.dto.request.UserCreateDto
import com.onboarding.task.repository.UserRepository
import com.onboarding.task.service.impl.UserServiceImpl
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat

@SpringBootTest
class UserServiceImplTest @Autowired constructor(
    private val userServiceImpl: UserServiceImpl,
    private val userRepository: UserRepository
) {
    @Test
    fun saveUserTest() {
        val dto1 = UserCreateDto("aa@aa.com", "1111", "aa")
        val dto2 = UserCreateDto("aa1@aa.com", "1234", "aa")

        userServiceImpl.createUser(dto1)
        userServiceImpl.createUser(dto2)

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
package com.onboarding.task.controller

import com.onboarding.task.dto.request.UserCreateDto
import com.onboarding.task.dto.request.UserInfoUpdateRequest
import com.onboarding.task.dto.request.UserSignUpRequest
import com.onboarding.task.dto.response.UserInfoResponse
import com.onboarding.task.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@Controller
@RequestMapping("/users")
class UserController (
    private val userService: UserService
) {

    @GetMapping("/signUp")
    fun createUserForm() : String {
        return "/createUserForm"
    }

    /**
     * 회원 가입
     */
    @PostMapping("/signUp")
    @ResponseStatus(HttpStatus.OK)
    fun signUp(@Valid @RequestBody req: UserSignUpRequest) {
        userService.signUpUser(req)
    }

    /**
     * 회원 정보 수정 ??
     */
    @PutMapping("/user")
    fun updateUserInfo(@Valid @RequestBody req: UserInfoUpdateRequest) {
        userService.updateUserInfo(req)
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping("/user")
    fun deleteUser(@Valid @RequestBody id: Long) {
        userService.deleteUser(id)
    }

    /**
     * 회원 정보 조회
     */
    @GetMapping("/{id}")
    fun getUserInfo(@Valid @PathVariable("id") id: Long) : ?? {
        val user = userService.getUserInfo(id)
    }
}
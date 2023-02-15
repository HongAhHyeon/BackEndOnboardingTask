//package com.onboarding.task.controller
//
//import com.onboarding.task.dto.request.UserCreateDto
//import com.onboarding.task.service.UserService
//import org.springframework.stereotype.Controller
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.RequestBody
//import org.springframework.web.bind.annotation.RequestMapping
//
//@Controller
//@RequestMapping("/users")
//class UserController (
//    private val userService: UserService
//) {
//
//    @GetMapping("/new")
//    fun createUserForm() : String {
//        return "/createUserForm"
//    }
//
//    @PostMapping("/new")
//    fun createUser(@RequestBody dto: UserCreateDto){
//        userService.createUser(dto)
//    }
//}
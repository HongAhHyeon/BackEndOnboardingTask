package com.onboarding.task.controller

import com.onboarding.task.dto.request.MemberInfoUpdateRequest
import com.onboarding.task.dto.request.MemberSignUpRequest
import com.onboarding.task.service.MemberService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/members")
class MemberController (
    private val memberService: MemberService
) {

    @GetMapping("/new")
    fun createUserForm(model: Model) : String {
        model.addAttribute("memberSignUpRequest", MemberSignUpRequest())
        return "members/createMemberForm"
    }


    /**
     * 회원 가입
     */
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.OK)
    fun signUp(@Valid req: MemberSignUpRequest, result: BindingResult) : String {
        if(result.hasErrors()) {
            return "members/createMemberForm"
        }
        memberService.signUpUser(req)
        return "redirect:/"
    }

    /**
     * 회원 정보 수정 ??
     */
    @PutMapping("/{id}")
    fun updateUserInfo(@Valid @PathVariable("id") id: Long, req: MemberInfoUpdateRequest) {
        memberService.updateUserInfo(req)
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping("/{id}")
    fun deleteUser(@Valid @PathVariable("id") id: Long) {
        memberService.deleteUser(id)
    }

    /**
     * 회원 정보 조회
     */
    @GetMapping("/{id}")
    fun getUserInfo(@Valid @PathVariable("id") id: Long, model: Model) : String {
        val member = memberService.getUserInfo(id)
        model.addAttribute("myInfo", member)
        return "members/myPage"
    }
}
package com.onboarding.task.service.impl

import com.onboarding.task.dto.request.MemberInfoUpdateRequest
import com.onboarding.task.dto.request.MemberSignInRequest
import com.onboarding.task.entity.Member
import com.onboarding.task.dto.request.MemberSignUpRequest
import com.onboarding.task.dto.response.UserDto
import com.onboarding.task.dto.response.MemberInfoResponse
import com.onboarding.task.repository.MemberRepository
//import com.onboarding.task.service.TokenService
import com.onboarding.task.service.MemberService
import com.onboarding.task.util.SecurityUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
//    private val tokenService: TokenService
) : MemberService {

    @Transactional
    override fun signUpUser(req: MemberSignUpRequest) {
        validateUserInfo(req.memberEmail, req.memberName)

        val newUser = req.toEntity(passwordEncoder)
        memberRepository.save(newUser)
    }

    @Transactional
    override fun deleteUser(id: Long) {
        val user = memberRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("사용자 정보 없음.")
        memberRepository.delete(user)
    }

    @Transactional
    override fun signInUser(req: MemberSignInRequest): UserDto {
        val user = memberRepository.findByMemberEmail(req.memberEmail) ?: throw IllegalArgumentException("사용자 정보 없음.")
        validatePassword(req, user)
        val subject = createSubject(user)
//        val accessToken = tokenService.createAccessToken(subject)
//        val refreshToken = tokenService.createRefreshToken(subject)
        return UserDto.of(user)

    }
    @Transactional
    override fun updateUserInfo(req: MemberInfoUpdateRequest) {
        val user = memberRepository.findByMemberEmail(req.userEmail)
        validateUserInfo(req.userEmail, req.userName)
        user!!.updateMemberName(req.userName)

    }

    override fun updateUserPw(checkPw: String, newPw: String) {
        val user = memberRepository.findByMemberEmail(SecurityUtil.getSignInUsername()) ?: throw Exception("사용자 정보 없음.")
        if(!user.validatePw(passwordEncoder, checkPw)) throw Exception("비밀번호가 일치하지 않습니다.")
        user.updateMemberPw(passwordEncoder, newPw)
    }

    @Transactional(readOnly = true)
    override fun getUserInfo(id: Long): MemberInfoResponse {
        val user = memberRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("사용자 정보 없음.")
        return MemberInfoResponse.of(user)
    }

    private fun validateUserInfo(userEmail: String, userName: String) {
        if(memberRepository.existsByMemberEmail(userEmail)) {
            throw IllegalArgumentException("이미 사용 중인 이메일입니다.")
        }

        if(memberRepository.existsByMemberName(userName)) {
            throw IllegalArgumentException("이미 사용 중인 이름입니다.")
        }
    }
    private fun validatePassword(req : MemberSignInRequest, member: Member) {
        if(!passwordEncoder.matches(req.memberPw, member.memberPw)) throw IllegalArgumentException("비밀번호 오류")
    }

    private fun createSubject(member: Member) : String {
        return member.id.toString()
    }
}
package com.onboarding.task.entity

import com.onboarding.task.enum.BookMarkStatus
import com.onboarding.task.enum.MemberRole
import jakarta.persistence.*
import org.springframework.security.crypto.password.PasswordEncoder

@Entity
class Member (
    val memberEmail: String,

    var memberPw: String,
    var memberName: String,

    @Enumerated(EnumType.STRING)
    val role: MemberRole = MemberRole.USER,

    private var refreshToken: String? = null,

    @OneToMany(mappedBy = "writer", cascade = [CascadeType.ALL], orphanRemoval = true)
    val boards: MutableList<Board> = mutableListOf(),

    @OneToMany(mappedBy = "writer", cascade = [CascadeType.ALL], orphanRemoval = true)
    val comments: MutableList<Comment> = mutableListOf(),

    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL], orphanRemoval = true)
    val bookMark: MutableList<BookMark> = mutableListOf(),

    @Id @GeneratedValue
    @Column(name = "member_id")
    val id: Long? = null

) : BaseTimeEntity() {

    fun updateMemberPw(passwordEncoder: PasswordEncoder, pw: String) {
        this.memberPw = passwordEncoder.encode(pw)
    }

    fun updateMemberName(name: String) {
        this.memberName = name
    }

    fun updateRefreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
    }

    fun destroyRefreshToken() {
        this.refreshToken = null
    }

    fun addBoard(board: Board) {
        boards.add(board)
    }
    fun addComment(comment: Comment) {
        comments.add(comment)
    }

    fun markBoard(board: Board) {
        this.bookMark.add(BookMark(this, board, BookMarkStatus.MARKED))
    }

    fun unmarkBoard(boardId: Long) {
        this.bookMark.first { it.board.id == boardId }.unmarkPost()
    }

    fun remarkBoard(boardId: Long) {
        this.bookMark.first{it.board.id == boardId}.markPost()
    }

    // 비밀번호 암호화
    fun encodePw(passwordEncoder: PasswordEncoder) {
        this.memberPw = passwordEncoder.encode(memberPw)
    }

    // 비밀번호 일치 여부
    fun validatePw(passwordEncoder: PasswordEncoder, checkPw: String) : Boolean {
        return passwordEncoder.matches(checkPw, memberPw)
    }

}
package com.onboarding.task.entity

import com.onboarding.task.enum.UserRole
import jakarta.persistence.*
import org.springframework.security.crypto.password.PasswordEncoder

@Entity
class User (
    val userEmail: String,

    var userPw: String,
    var userName: String,

    @Enumerated(EnumType.STRING)
    val role: UserRole? = UserRole.USER,

    var refreshToken: String? = null,

    @OneToMany(mappedBy = "writer", cascade = [CascadeType.ALL], orphanRemoval = true)
    val posts: MutableList<Post> = mutableListOf(),

    @OneToMany(mappedBy = "writer", cascade = [CascadeType.ALL], orphanRemoval = true)
    val comments: MutableList<Comment> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val bookMark: MutableList<BookMark> = mutableListOf(),

    @Id @GeneratedValue
    @Column(name = "user_id")
    val id: Long? = null

) : BaseTimeEntity() {

    fun updateUserPw(passwordEncoder: PasswordEncoder, pw: String) {
        this.userPw = passwordEncoder.encode(pw)
    }

    fun updateUserName(name: String) {
        this.userName = name
    }

    fun updateRefreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
    }

    fun destroyRefreshToken() {
        this.refreshToken = null
    }

    fun addPost(post: Post) {
        posts.add(post)
    }
    fun addComment(comment: Comment) {
        comments.add(comment)
    }

    fun markPost(post: Post) {
        this.bookMark.add(BookMark(this, post))
    }

    // 비밀번호 암호화
    fun encodePw(passwordEncoder: PasswordEncoder) {
        this.userPw = passwordEncoder.encode(userPw)
    }

    // 비밀번호 일치 여부
    fun validatePw(passwordEncoder: PasswordEncoder, checkPw: String) : Boolean {
        return passwordEncoder.matches(checkPw, userPw)
    }

}
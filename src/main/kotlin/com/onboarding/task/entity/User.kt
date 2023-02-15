package com.onboarding.task.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import org.springframework.security.crypto.password.PasswordEncoder

@Entity
class User (
    var userEmail: String,
    var userPw: String,
    var userName: String,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val bookMark: MutableList<BookMark> = mutableListOf(),

    @Id @GeneratedValue
    @Column(name = "user_id")
    val id: Long? = null
) : BaseTimeEntity() {
    fun updateUserEmail(email: String) {
        this.userEmail = email
    }

    fun updateUserPw(passwordEncoder: PasswordEncoder, pw: String) {
        this.userPw = passwordEncoder.encode(pw)
    }

    fun updateUserName(name: String) {
        this.userName = name
    }

    fun markPost(post: Post) {
        this.bookMark.add(BookMark(this, post))
    }

    // 비밀번호 암호화
    fun encodePw(passwordEncoder: PasswordEncoder) {
        this.userPw = passwordEncoder.encode(userPw)
    }

}
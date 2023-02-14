package com.onboarding.task.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
class User (
    var userEmail: String,
    var userPw: String,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val bookMark: MutableList<BookMark> = mutableListOf(),

    @Id @GeneratedValue
    @Column(name = "user_id")
    val id: Long? = null
) {

    fun updateUserEmail(email: String) {
        this.userEmail = email
    }

    fun updateUserPw(pw: String) {
        this.userPw = pw
    }

    fun markPost(post: Post) {
        this.bookMark.add(BookMark(this, post))
    }

}
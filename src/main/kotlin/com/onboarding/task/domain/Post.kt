package com.onboarding.task.domain

import jakarta.persistence.*
import java.time.LocalDateTime


class Post (
    @Id @GeneratedValue
    @Column(name = "post_id")
    val id: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,
    val title: String,
    val content: String,
    val postDate: LocalDateTime,
    val modifyDate: LocalDateTime? = null,

    @OneToMany(mappedBy = "post")
    val comments: MutableCollection<Comment> = mutableListOf()

) {
}
package com.onboarding.task.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Post (
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    var title: String,
    var content: String,
    val postDate: LocalDateTime,
    var modifyDate: LocalDateTime? = null,

    @OneToMany(mappedBy = "post")
    var comments: MutableCollection<Comment> = mutableListOf(),

    @Id @GeneratedValue
    @Column(name = "post_id")
    val id: Long? = null

) {
}
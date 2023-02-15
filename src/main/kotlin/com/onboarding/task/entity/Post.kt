package com.onboarding.task.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Post (
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    var title: String,
    var content: String,

    @OneToMany(mappedBy = "post")
    var comments: MutableCollection<Comment> = mutableListOf(),

    @Id @GeneratedValue
    @Column(name = "post_id")
    val id: Long? = null

) : BaseTimeEntity() {
    fun addComment(comment: Comment) {
        comments.add(comment)
    }
}
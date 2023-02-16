package com.onboarding.task.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Post (
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    var writer: User? = null,

    var title: String,

    var content: String,
    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], orphanRemoval = true)
    var comments: MutableList<Comment> = mutableListOf(),

    @Id @GeneratedValue
    @Column(name = "post_id")
    val id: Long? = null


) : BaseTimeEntity() {

    fun updateTitle(title: String) {
        this.title = title
    }

    fun updateContent(content: String) {
        this.content = content
    }

    fun confirmWriter(writer: User) {
        this.writer = writer
        writer.posts.add(this)
    }
    fun addComment(comment: Comment) {
        comments.add(comment)
    }
}
package com.onboarding.task.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import java.time.LocalDateTime
@Entity
class Comment(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    var writer: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    var post: Post,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parent: Comment,

    var content: String,

    var isDeleted: Boolean = false,

    @OneToMany(mappedBy = "parent")
    var childrenComments : MutableList<Comment>,

    @Id @GeneratedValue
    @Column(name = "comment_id")
    val id: Long? = null


) : BaseTimeEntity() {

    fun updateContent(content: String) {
        this.content = content
    }

    fun delete() {
        this.isDeleted = true
    }

    fun confirmWriter(writer: User) {
        this.writer = writer
        writer.comments.add(this)
    }

    fun confirmPost(post: Post) {
        this.post = post
        post.comments.add(this)
    }

    fun Parent(parent: Comment) {
        this.parent = parent
        parent.childrenComments.add(this)
    }

    fun addChildren(child: Comment) {
        childrenComments.add(child)
    }

}
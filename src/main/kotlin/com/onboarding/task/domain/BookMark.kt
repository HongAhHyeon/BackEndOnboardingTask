package com.onboarding.task.domain

import com.onboarding.task.enum.BookMarkStatus
import jakarta.persistence.*
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter.All

@Entity
class BookMark(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    val post: Post,

    @Enumerated(EnumType.STRING)
    var status: BookMarkStatus = BookMarkStatus.MARKED,

    @Id @GeneratedValue
    @Column(name = "bookmark_id")
    val id: Long? = null
) {

  fun markPost() {
    this.status = BookMarkStatus.MARKED
  }

  fun unmarkPost() {
    this.status = BookMarkStatus.UNMARKED
  }
}
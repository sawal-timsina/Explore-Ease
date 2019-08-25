package com.codeace.exploreease.entities

data class UserPost(
    var user: User = User(),
    var post: Post = Post(),
    var likes: List<Like> = listOf(),
    var comments: List<Comment> = listOf()
)
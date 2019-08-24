package com.codeace.exploreease.entities

data class UserPost(
    val user: User = User(),
    var post: List<Post> = listOf(),
    var likes: List<Like> = listOf(),
    var comments: List<Comment> = listOf()
)
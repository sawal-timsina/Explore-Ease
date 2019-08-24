package com.codeace.exploreease.entities

data class Post(
    val key: String? = "",
    val userName: String? = "",
    val userAvatar: String? = "",
    var description: String? = "",
    val dataTime: String? = "",
    val imageUri: String? = "",
    val location: String? = "",
    var likes: MutableList<String>? = mutableListOf(),
    var comments: MutableList<Comment>? = mutableListOf()
)
package com.codeace.exploreease.entities

data class Post(
    val uid: String? = "",
    val userName: String? = "",
    val userAvatar: String? = "",
    var description: String? = "",
    val dataTime: String? = "",
    val imageUri: String? = "",
    val locationId: String? = "",
    var likes: MutableList<String>? = mutableListOf(),
    var comments: MutableList<Comment>? = mutableListOf()
)
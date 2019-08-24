package com.codeace.exploreease.entities

data class Comment(
    val uid: String? = "",
    val userName: String? = "",
    val userAvatar: String? = "",
    var comment: String? = ""
)
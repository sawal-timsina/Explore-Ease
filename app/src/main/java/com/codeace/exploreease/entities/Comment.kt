package com.codeace.exploreease.entities

import java.io.Serializable

data class Comment(
    var key: String = "",
    var uid: String = "",
    var comment: String = ""
) : Serializable
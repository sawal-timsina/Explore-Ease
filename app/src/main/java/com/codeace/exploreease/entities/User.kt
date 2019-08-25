package com.codeace.exploreease.entities

import java.io.Serializable

data class User(
    var uid: String = "",
    var userAvatar: String = "",
    var userName: String = "",
    var points: Int = 0
) : Serializable

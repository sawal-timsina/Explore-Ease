package com.codeace.exploreease.entities

import java.io.Serializable

data class Post(
    var dateTime: String = "",
    var description: String = "",
    var imageUri: String = "",
    var key: String = "",
    var locationId: String = "",
    var locationName: String = "",
    var id: String = ""
) : Serializable
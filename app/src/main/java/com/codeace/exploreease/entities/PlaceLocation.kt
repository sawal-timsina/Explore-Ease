package com.codeace.exploreease.entities

import java.io.Serializable


data class PlaceLocation(
    var locationId: String = "",
    var LocationName: String = "",
    var descrption: String = "",
    var lat: Double = 0.0,
    var long: Double = 0.0
) : Serializable

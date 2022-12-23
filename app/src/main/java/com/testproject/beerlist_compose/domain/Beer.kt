package com.testproject.beerlist_compose.domain


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Beer(
    @Json(name="id")
    val id : String,
    @Json(name="name")
    val name: String,
    @Json(name="tagline")
    val tagline: String,
    @Json (name = "image_url")
    val image_url: String,
    @Json (name ="first_brewed")
    val first_brewed: String,
    @Json (name = "description")
    val description: String,
    var expanded : Boolean = false
)




package com.abdalla.altarifiappfinal.model

import com.squareup.moshi.Json

data class Quote(
    @Json(name = "title")
    val title: String = "",

    @Json(name = "quotes")
    var quotes: List<String> = emptyList()
)
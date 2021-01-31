package com.example.heroesapp_v1.model

import com.google.gson.annotations.SerializedName


data class ApiHeroResponse(

    val code: Int,
    val status: String,
    @SerializedName("data")
    val data: Data
)


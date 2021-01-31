package com.example.heroesapp_v1.model

import com.google.gson.annotations.SerializedName


data class Data(

    @SerializedName("results")
    val heroes: List<Hero>
)

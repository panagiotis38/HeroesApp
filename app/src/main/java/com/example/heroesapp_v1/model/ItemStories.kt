package com.example.heroesapp_v1.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemStories(
    val name: String,
    val resourceURI: String,
    val type: String
): Parcelable
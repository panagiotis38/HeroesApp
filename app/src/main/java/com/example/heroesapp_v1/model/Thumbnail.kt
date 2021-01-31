package com.example.heroesapp_v1.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Thumbnail(
    val extension: String,
    val path: String
): Parcelable {

    fun getImageUrl(): String {
        return "$path.$extension"
    }
}
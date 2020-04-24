package com.example.heroesapp_v1.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemSeries(
    val name: String,
    val resourceURI: String
): Parcelable
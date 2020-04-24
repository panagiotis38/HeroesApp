package com.example.heroesapp_v1.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hero(
    val id: Int,
    val name: String,
    val thumbnail: Thumbnail,
    val comics: Comics,
    val series: Series,
    val stories: Stories,
    val events: Events
): Parcelable {

    constructor() : this(
        1,
        "EmptyName",
        Thumbnail("",""),
        Comics(1,"", listOf(ItemComic ("","")),1),
        Series(1,"", listOf(ItemSeries ("","")),1),
        Stories(1,"", listOf(ItemStories ("","","")),1),
        Events(1,"", listOf(ItemEvent ("","")),1)
    )
}



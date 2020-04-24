package com.example.heroesapp_v1.front.adapters

import android.widget.ImageButton

interface HeroClickListener {
    fun onHeroClicked (position: Int, isFavorite: Boolean)
    fun onFavoriteHeroClicked (position: Int, view: ImageButton)
}
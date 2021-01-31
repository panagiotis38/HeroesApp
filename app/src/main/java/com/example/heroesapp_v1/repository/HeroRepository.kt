package com.example.heroesapp_v1.repository

import com.example.heroesapp_v1.model.Hero

interface HeroRepository {

    // NW functions
    suspend fun getHeroList(offset: Int, limit: Int): List<Hero>
    suspend fun searchHeroList(offset: Int, nameStartsWith: String): List<Hero>

    //DB functions
    fun addFavoriteHero (id: Int)
    fun deleteFavoriteHero(id: Int)
    fun isHeroFavorite (id:Int): Boolean
}
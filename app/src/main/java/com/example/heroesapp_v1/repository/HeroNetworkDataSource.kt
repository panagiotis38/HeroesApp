package com.example.heroesapp_v1.repository

import com.example.heroesapp_v1.model.Hero

interface HeroNetworkDataSource {

    suspend fun fetchHeroList(offset: Int, limit: Int): List<Hero>
    suspend fun searchHeroList(offset: Int, nameStartsWith: String): List<Hero>
}
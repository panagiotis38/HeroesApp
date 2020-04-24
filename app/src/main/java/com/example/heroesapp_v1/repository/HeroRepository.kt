package com.example.heroesapp_v1.repository

import com.example.heroesapp_v1.database.FavoriteHeroesDao
import com.example.heroesapp_v1.model.Hero

class HeroRepository(
    private val heroNetworkDataSource: HeroNetworkDataSource,
    private val favoriteHeroesDao: FavoriteHeroesDao
) {

    suspend fun getHeroList(offset: Int, limit: Int): List<Hero> {
        return heroNetworkDataSource.fetchHeroList(offset,limit)
    }

    suspend fun searchHeroList(offset: Int, nameStartsWith: String): List<Hero> {
        return heroNetworkDataSource.searchHeroList(offset,nameStartsWith)
    }


    fun addDeleteFavoriteHero(id: Int) {

        if (favoriteHeroesDao.isFavorite(id)) {
            favoriteHeroesDao.delete(id)
        } else {
            favoriteHeroesDao.insert(id)
        }
    }

    fun isHeroFavorite (id:Int): Boolean {
        return favoriteHeroesDao.isFavorite(id)
    }
}
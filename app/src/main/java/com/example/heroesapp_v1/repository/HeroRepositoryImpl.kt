package com.example.heroesapp_v1.repository

import android.util.Log
import com.example.heroesapp_v1.database.FavoriteHeroesDbMock
import com.example.heroesapp_v1.model.Hero
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeroRepositoryImpl @Inject constructor(
    private val heroNetworkDataSource: HeroNetworkDataSource,
    private val database: FavoriteHeroesDbMock
    //, private val favoriteHeroesDao: FavoriteHeroesDao
): HeroRepository {

    /**companion object {
        @Volatile
        private var instance: HeroRepositoryImpl? = null

        fun getInstance(heroNetworkDataSource: HeroNetworkDataSourceImpl, dbInstance: FavoriteHeroesDbMock): HeroRepositoryImpl {
            return instance ?: HeroRepositoryImpl(heroNetworkDataSource, dbInstance)
                .also { instance = it }
        }
        fun getInstance(): HeroRepositoryImpl? {
            return if (instance != null) {
                instance
            } else {
                Log.e("Empty Instance","Empty instance requested by HeroViewModel")
                null
            }
        }
    }*/


    override suspend fun getHeroList(offset: Int, limit: Int): List<Hero> {
        return heroNetworkDataSource.fetchHeroList(offset,limit)
    }

    override suspend fun searchHeroList(offset: Int, nameStartsWith: String): List<Hero> {
        return heroNetworkDataSource.searchHeroList(offset,nameStartsWith)
    }



    override fun addFavoriteHero (id: Int) {
        database.insert(id)
    }

    override fun deleteFavoriteHero(id: Int) {
        database.delete(id)
    }

    override fun isHeroFavorite (id:Int): Boolean {
        return database.isFavorite(id)
    }
}
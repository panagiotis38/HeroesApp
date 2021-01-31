package com.example.heroesapp_v1.database


import javax.inject.Inject
import javax.inject.Singleton


class FavoriteHeroesDbMock() {

    companion object {
        @Volatile
        private var instance: FavoriteHeroesDbMock? = null
        private val lock = Any()
        fun getInstance(): FavoriteHeroesDbMock {
            return instance ?: synchronized(lock) {
                instance ?: FavoriteHeroesDbMock()
            }
        }
    }

    private var favoriteHeroList = ArrayList<Int>()

    fun insert(id: Int) {
        favoriteHeroList.add(id)
    }

    fun delete(id: Int) {
        favoriteHeroList.remove(id)
    }

    fun getFavoriteIndex(id: Int): Int {
        return favoriteHeroList.indexOf(id)
    }

    fun isFavorite(id: Int): Boolean {
        return favoriteHeroList.contains(id)
    }
}
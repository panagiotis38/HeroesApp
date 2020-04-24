package com.example.heroesapp_v1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteHeroes::class],
    version = 1
)

abstract class FavoriteHeroesDB: RoomDatabase() {
    abstract fun favoriteHeroesDao(): FavoriteHeroesDao

    companion object {

        @Volatile private var instance: FavoriteHeroesDB? = null

        private val lock = Any()

        fun getInstance(context: Context): FavoriteHeroesDB {
            return instance ?: synchronized(lock) {
                instance ?: buildDB(context)
                    .also { instance = it }
            }
        }

        private fun buildDB(context: Context): FavoriteHeroesDB {
            return Room.databaseBuilder(context,
                    FavoriteHeroesDB::class.java,"favoriteHeroes.db")
                .build()
        }
    }
}
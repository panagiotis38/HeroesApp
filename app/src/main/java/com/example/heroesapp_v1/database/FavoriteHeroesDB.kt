/**package com.example.heroesapp_v1.database

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

        /** Volatile means that when the value of instance is change
         * all threads have immediate access to that value.
         * If it was not Volatile, each thread writes the new
         * value first to the thread's cache and then to main memory.
         * This can potentially cause synchronisation issues*/

        private val lock = Any()

        fun getInstance(context: Context): FavoriteHeroesDB {
            return instance ?: synchronized(lock) {
                instance ?: buildDB(context)
                    .also { instance = it }
            }
        }

        /**If instance != null return instance
         * else lock instance in order to madify it:
         * if instance != null (means another thread has modified it before we locked) return instance
         * else build DB and assign the returned value to instance*/

        private fun buildDB(context: Context): FavoriteHeroesDB {
            return Room.databaseBuilder(context,
                    FavoriteHeroesDB::class.java,"favoriteHeroes.db")
                .build()
        }
    }
}*/
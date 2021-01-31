/**package com.example.heroesapp_v1.database

import androidx.room.*

@Dao
interface FavoriteHeroesDao {
    @Query("insert into favorite_heroes (id) values(:hero_id)")
    fun insert(hero_id : Int)
    @Query("delete from favorite_heroes where id= :hero_id")
    fun delete(hero_id: Int)
    @Query("select count (id) from favorite_heroes where id = :hero_id")
    fun isFavorite(hero_id : Int) : Int
    /**
    @Query("select * from favorite_heroes where id = :hero_id")
    fun isFavorite(hero_id : Int) : Boolean
    */
}*/
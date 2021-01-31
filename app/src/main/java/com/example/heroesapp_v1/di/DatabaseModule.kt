package com.example.heroesapp_v1.di

import android.content.Context
import com.example.heroesapp_v1.api.MarvelApiService
import com.example.heroesapp_v1.database.FavoriteHeroesDbMock
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    companion object {
        @Provides
        fun provideDatabaseInstance(): FavoriteHeroesDbMock {
            return FavoriteHeroesDbMock.getInstance()
        }
    }
}
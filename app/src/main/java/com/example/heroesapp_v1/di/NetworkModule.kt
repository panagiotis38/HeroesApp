package com.example.heroesapp_v1.di

import android.content.Context
import com.example.heroesapp_v1.api.MarvelApiService
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {

    companion object {
        @Provides
        fun provideApiService(appContext: Context): MarvelApiService {
            return MarvelApiService.invoke(appContext)
        }
    }
}
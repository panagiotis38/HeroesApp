package com.example.heroesapp_v1.di

import android.content.Context
import com.example.heroesapp_v1.api.MarvelApiService
import com.example.heroesapp_v1.repository.HeroNetworkDataSource
import com.example.heroesapp_v1.repository.HeroNetworkDataSourceImpl
import com.example.heroesapp_v1.repository.HeroRepository
import com.example.heroesapp_v1.repository.HeroRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class HeroModule {

    @Binds
    abstract fun provideRepository(repository: HeroRepositoryImpl): HeroRepository
    @Binds
    abstract fun provideNetworkDataSource(networkDataSource: HeroNetworkDataSourceImpl): HeroNetworkDataSource

}
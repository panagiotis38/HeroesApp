package com.example.heroesapp_v1.di

import android.content.Context
import com.example.heroesapp_v1.front.activities.HeroActivity
import com.example.heroesapp_v1.front.activities.HomeActivity
import com.example.heroesapp_v1.front.fragments.HeroFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component (modules = [HeroModule::class, NetworkModule::class, DatabaseModule::class, AppSubcomponents::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create (@BindsInstance appContext: Context): AppComponent
    }

    fun heroComponent(): HeroComponent.Factory
    fun heroesComponent(): HeroesComponent.Factory

}
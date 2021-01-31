package com.example.heroesapp_v1.di

import com.example.heroesapp_v1.front.activities.HomeActivity
import com.example.heroesapp_v1.front.fragments.HeroFragment
import dagger.Subcomponent

@HomeActivityScope
@Subcomponent
interface HeroesComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): HeroesComponent
    }

    fun inject (activity: HomeActivity)
    fun inject (fragment: HeroFragment)
}
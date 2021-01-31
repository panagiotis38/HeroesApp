package com.example.heroesapp_v1.di

import com.example.heroesapp_v1.front.activities.HeroActivity
import dagger.Subcomponent

@HeroActivityScope
@Subcomponent
interface HeroComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): HeroComponent
    }


    fun inject (activity: HeroActivity)
}
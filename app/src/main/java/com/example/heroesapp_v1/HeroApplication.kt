package com.example.heroesapp_v1

import android.app.Application
import com.example.heroesapp_v1.di.DaggerAppComponent
import com.example.heroesapp_v1.di.AppComponent

class HeroApplication: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

}
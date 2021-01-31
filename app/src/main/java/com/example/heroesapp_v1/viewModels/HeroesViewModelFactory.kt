/**package com.example.heroesapp_v1.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.heroesapp_v1.exceptions.UnknownViewModelClassException
import com.example.heroesapp_v1.repository.HeroRepository
import javax.inject.Provider

class HeroesViewModelFactory (
    //private val heroRepository: HeroRepository
    private val provider: Provider<HeroesViewModel>
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HeroesViewModel::class.java)) {
            //return HeroesViewModel(heroRepository) as T
            return provider.get() as T
        }
        throw UnknownViewModelClassException("Unknown ViewModel class")
    }
}*/
package com.example.heroesapp_v1.viewModels

import androidx.lifecycle.ViewModel
import com.example.heroesapp_v1.di.HeroActivityScope
import com.example.heroesapp_v1.model.Hero
import com.example.heroesapp_v1.repository.HeroRepository
import javax.inject.Inject

@HeroActivityScope
class HeroViewModel @Inject constructor(
    private val heroRepository: HeroRepository
): ViewModel() {

    //private val heroRepository = HeroRepositoryImpl.getInstance()

    fun addDeleteFavoriteHero(hero: Hero) {
        if (!heroRepository.isHeroFavorite(hero.id)) {
            heroRepository.addFavoriteHero(hero.id)
            hero.isFavorite = true
        } else {
            heroRepository.deleteFavoriteHero(hero.id)
            hero.isFavorite = false
        }
        /**if (heroRepository != null) {

        }*/
    }

    fun isHeroFavorite(hero: Hero): Boolean {
        return heroRepository.isHeroFavorite(hero.id)
    }

}
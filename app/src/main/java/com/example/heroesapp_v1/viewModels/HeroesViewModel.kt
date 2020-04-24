package com.example.heroesapp_v1.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.heroesapp_v1.utils.Constants
import com.example.heroesapp_v1.database.FavoriteHeroesDB
import com.example.heroesapp_v1.model.Hero
import com.example.heroesapp_v1.repository.HeroNetworkDataSource
import com.example.heroesapp_v1.repository.HeroRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HeroesViewModel (context: Context) : ViewModel() {

    private val heroNetworkDataSource = HeroNetworkDataSource(context)
    private val dbInstance = FavoriteHeroesDB.getInstance(context)
    private val heroesDao = dbInstance.favoriteHeroesDao()
    private val heroRepository = HeroRepository(heroNetworkDataSource, heroesDao)

    private val heroList = MutableLiveData<List<Hero>>()
    private val searchedHeroList = MutableLiveData<List<Hero>>()
    private val emptyListResponse = ArrayList<Hero>()
    var isHeroFavorite = MutableLiveData<Boolean>()

    init {
        emptyListResponse.add(Hero())
    }


    fun getHeroList(page: Int): LiveData<List<Hero>> {

        GlobalScope.launch(Dispatchers.IO) {
            val networkResponse =
                heroRepository.getHeroList(page * Constants.limit, Constants.limit)
            if (!networkResponse.isNullOrEmpty()) {
                heroList.postValue(networkResponse)
            }
        }
        return heroList
    }

    fun searchHeroList(page: Int, nameStartsWith: String): LiveData<List<Hero>> {

        GlobalScope.launch(Dispatchers.IO) {
            val networkResponse =
                heroRepository.searchHeroList(page * Constants.limit, nameStartsWith)
            if (!networkResponse.isNullOrEmpty()) {
                searchedHeroList.postValue(networkResponse)
            } else {
                searchedHeroList.postValue(emptyListResponse)
            }
        }
        return searchedHeroList
    }


    fun addDeleteFavoriteHero(id: Int) {

        GlobalScope.launch(Dispatchers.IO) {
            heroRepository.addDeleteFavoriteHero(id)
        }
    }

    fun isHeroFavorite(id: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            isHeroFavorite.postValue(withContext(Dispatchers.Default) {
                heroRepository.isHeroFavorite(id)
            })
        }
    }
}


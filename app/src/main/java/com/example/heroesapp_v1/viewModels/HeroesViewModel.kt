package com.example.heroesapp_v1.viewModels

import androidx.lifecycle.*
import com.example.heroesapp_v1.di.HomeActivityScope
import com.example.heroesapp_v1.front.utils.Constants
import com.example.heroesapp_v1.model.Hero
import com.example.heroesapp_v1.repository.HeroRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HomeActivityScope
class HeroesViewModel @Inject constructor (
    //application: Application,
    private val heroRepository: HeroRepository
) : ViewModel() {

    //private val dbInstance = FavoriteHeroesDB.getInstance(application.applicationContext)
    //private val heroesDao = dbInstance.favoriteHeroesDao()
    //private val heroNetworkDataSource = HeroNetworkDataSourceImpl(application.applicationContext)
    //private val dbInstance = FavoriteHeroesDbMock.getInstance()
    //private val heroRepository = HeroRepositoryImpl.getInstance(heroNetworkDataSource, dbInstance)

    private val heroList = MutableLiveData<List<Hero>>()
    private val currentHeroList = ArrayList<Hero>()

    private val searchedHeroList = MutableLiveData<List<Hero>>()
    private val emptyListResponse = ArrayList<Hero>()

    init {
        emptyListResponse.add(Hero())
    }


    fun getHeroList(): LiveData<List<Hero>> {
        return heroList
    }


    fun retrieveNewHeroDataSet(page: Int) {
        viewModelScope.launch {
            val networkResponse =
                heroRepository.getHeroList(page * Constants.limit, Constants.limit)
            if (!networkResponse.isNullOrEmpty()) {
                currentHeroList.addAll(networkResponse)
                heroList.postValue(currentHeroList)
            }
        }
    }

    fun searchHeroList(page: Int, nameStartsWith: String): LiveData<List<Hero>> {
        viewModelScope.launch(Dispatchers.IO) {
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



    fun addDeleteFavoriteHero(hero: Hero) {

        val index = currentHeroList.indexOf(hero)
        if (!heroRepository.isHeroFavorite(hero.id)) {
            heroRepository.addFavoriteHero(hero.id)
            currentHeroList[index].isFavorite = true
        } else {
            heroRepository.deleteFavoriteHero(hero.id)
            currentHeroList[index].isFavorite = false
        }
        refreshFavoritesByCheckingMockDb()
        //heroList.postValue(currentHeroList)
    }

    fun isHeroFavorite(hero: Hero): Boolean {
        return heroRepository.isHeroFavorite(hero.id)
    }

    private fun refreshFavoritesByCheckingMockDb() {
        for (i in currentHeroList.indices) {
            currentHeroList[i].isFavorite = heroRepository.isHeroFavorite(currentHeroList[i].id)
        }
        heroList.postValue(currentHeroList)

        /** Just for debugging purposes
        for (i in currentHeroList.indices) {
        if (currentHeroList[i].isFavorite) {
        Log.e("Heroes found favorites:","Hero with name ${currentHeroList[i].name}")
        }
        }*/
    }
}










    /***END of DB mock*****/


    /**fun addDeleteFavoriteHero(hero: Hero) {
        //val index = currentHeroList.indexOf(hero)
        viewModelScope.launch(Dispatchers.IO) {
            if (heroRepository.isHeroFavorite(hero.id)) {
                heroRepository.deleteFavoriteHero(hero.id)
                Log.e("IsFavorite?","heroRepository.isHeroFavorite == TRUE --- Hero with name:${hero.name} deleted from DB")
            } else {
                heroRepository.addFavoriteHero(hero.id)
                Log.e("IsFavorite?","heroRepository.isHeroFavorite == FALSE --- Hero with name ${hero.name} added to DB")
            }
        }
        refreshFavoritesByCheckingDb()
    }*/

    /**fun isHeroFavorite(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            isHeroFavorite.postValue(withContext(Dispatchers.Default) {
                heroRepository.isHeroFavorite(id)
            })
        }
    }

    private fun refreshFavoritesByCheckingDb() {
        /**Change the below. Have to open stream in DB*/
        viewModelScope.launch {
            for (i in currentHeroList.indices) {
                currentHeroList[i].isFavorite = heroRepository.isHeroFavorite(currentHeroList[i].id)
            }
            // To be deleted
            for (i in currentHeroList.indices) {
                if (currentHeroList[i].isFavorite) {
                    Log.e("Heroes found favorites:","Hero with name ${currentHeroList[i].name}")
                }
            }
        }
        heroList.postValue(currentHeroList)
    }*/







package com.example.heroesapp_v1.repository

import android.content.Context
import android.util.Log
import com.example.heroesapp_v1.api.MarvelApiService
import com.example.heroesapp_v1.utils.NoConnectivityException
import com.example.heroesapp_v1.model.Hero


class HeroNetworkDataSource (context: Context) {

    private val apiService = MarvelApiService(context)
    private val fetchedHeroList = mutableListOf<Hero>()
    private val searchedHeroList = mutableListOf<Hero>()

    suspend fun fetchHeroList(offset: Int, limit: Int): List<Hero> {
        try{
            val heroResponse = apiService.getHeroes(offset,limit).await()

            if (heroResponse.code == 200){
                fetchedHeroList.addAll(heroResponse.data.heroes)
            }
        }
        catch (e: NoConnectivityException)  {
            Log.e("Connectivity","No internet connectivity",e)
        }
        return fetchedHeroList
    }

    suspend fun searchHeroList(offset: Int, nameStartsWith: String): List<Hero> {
        try{
            val heroResponse = apiService.searchHeroes(offset,nameStartsWith).await()

            if (heroResponse.code == 200){
                searchedHeroList.addAll(heroResponse.data.heroes)
            }
        }
        catch (e: NoConnectivityException)  {
            Log.e("Connectivity","No internet connectivity",e)
        }
        return searchedHeroList
    }
}
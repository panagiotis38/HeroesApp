package com.example.heroesapp_v1.repository

import android.content.Context
import android.util.Log
import com.example.heroesapp_v1.api.MarvelApiService
import com.example.heroesapp_v1.exceptions.NoConnectivityException
import com.example.heroesapp_v1.exceptions.RetrofitUnauthorisedException
import com.example.heroesapp_v1.front.utils.CustomEvent
import com.example.heroesapp_v1.model.Hero
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject


class HeroNetworkDataSourceImpl @Inject constructor (
    private val apiService: MarvelApiService
) : HeroNetworkDataSource {

    //private val apiService = MarvelApiService(context)
    private var fetchedHeroList: List<Hero> = ArrayList()
    private val searchedHeroList = mutableListOf<Hero>()

    override suspend fun fetchHeroList(offset: Int, limit: Int): List<Hero> {
        try{
            val heroResponse = apiService.getHeroes(offset,limit).await()
            //Log.e("Api_response",heroResponse.toString())

            if (heroResponse.code == 200){
                fetchedHeroList = heroResponse.data.heroes
            }
        }
        catch (e: NoConnectivityException)  {
            Log.e("Connectivity","No internet connectivity",e)
        }
        catch (e: RetrofitUnauthorisedException) {
            EventBus.getDefault().post(
                CustomEvent(
                    "Error 401. Please continue scrolling"
                )
            )
            Log.e("401 Exception","Authorization error",e)
        }
        return fetchedHeroList
    }

    override suspend fun searchHeroList(offset: Int, nameStartsWith: String): List<Hero> {
        try{
            val heroResponse = apiService.searchHeroes(offset,nameStartsWith).await()

            if (heroResponse.code == 200){
                searchedHeroList.addAll(heroResponse.data.heroes)
            }
        }
        catch (e: NoConnectivityException)  {
            Log.e("Connectivity","No internet connectivity",e)
        }
        catch (e: RetrofitUnauthorisedException) {
            EventBus.getDefault().post(
                CustomEvent(
                    "Error 401. Please search again"
                )
            )
            Log.e("401 Exception","Authorization error",e)
        }
        return searchedHeroList
    }
}
package com.example.heroesapp_v1.viewModels

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.heroesapp_v1.api.MarvelApiService
import com.example.heroesapp_v1.database.FavoriteHeroesDbMock
import com.example.heroesapp_v1.front.utils.Constants
import com.example.heroesapp_v1.model.*
import com.example.heroesapp_v1.repository.HeroNetworkDataSourceImpl
import com.example.heroesapp_v1.repository.HeroRepositoryImpl
import junit.framework.Assert.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
//import org.robolectric.RobolectricTestRunner

//@RunWith(RobolectricTestRunner::class)

class HeroesViewModelTest {


    private val testDispatcher = TestCoroutineDispatcher()

    //private lateinit var appContext: Context
    @Mock
    private lateinit var apiService: MarvelApiService

    private lateinit var heroNetworkDataSource: HeroNetworkDataSourceImpl
    private lateinit var dbInstance: FavoriteHeroesDbMock
    private lateinit var heroRepository: HeroRepositoryImpl

    private lateinit var viewModel: HeroesViewModel
    //private var heroList: List<Hero>? = null

    private lateinit var hero1: Hero // Hero with id = 1
    private lateinit var hero2: Hero // Hero with id = 2

    //private val dataSet = mutableListOf<Hero>()
    private lateinit var apiHeroResponse: ApiHeroResponse



    @Before
    fun setUp() {

        initDataSet()
        initHeroes()

        Dispatchers.setMain(testDispatcher)
        //appContext = ApplicationProvider.getApplicationContext()
        //apiService = MarvelApiService.invoke(appContext)
        MockitoAnnotations.initMocks(this)
        heroNetworkDataSource = HeroNetworkDataSourceImpl(apiService)
        dbInstance = FavoriteHeroesDbMock()
        heroRepository = HeroRepositoryImpl(heroNetworkDataSource, dbInstance)

        runBlocking {
            viewModel = HeroesViewModel(heroRepository)
            `when`(apiService.getHeroes(0,Constants.limit)).thenReturn(async { apiHeroResponse })
            viewModel.retrieveNewHeroDataSet(0)
        }




        //viewModel.getHeroList().observeForever{}




        /**
        runBlocking {
            withContext(Dispatchers.Default) {
                viewModel.retrieveNewHeroDataSet(0)
            }
            heroList = viewModel.getHeroList().value
        }*/





        //MockitoAnnotations.initMocks(this)
        //viewModel.getHeroList().observeForever(observer)
        //initDataSet()
    }

    @After
    fun tearDown() {
    }


    @Test
    fun retrieveNewHeroDataSet() {

        val heroList = viewModel.getHeroList().value
        assertNull(heroList)
        //assertTrue(viewModel.getHeroList().hasObservers())
        assertTrue(heroList?.get(0)?.name == "EmptyName")
    }

  /***
    @Test
    fun addFavoriteHero() {
        viewModel.addDeleteFavoriteHero(hero1)
        assertTrue(dbInstance.isFavorite(hero1.id))
    }

    @Test
    fun deleteFavoriteHero() {
        dbInstance.insert(hero2.id)
        viewModel.addDeleteFavoriteHero(hero2)
        assertTrue(!dbInstance.isFavorite(hero2.id))
    }*/


    private fun initHeroes() {
        hero1 = Hero(
            1, "EmptyName",
            false,
            Thumbnail("", ""),
            Comics(1, "", listOf(ItemComic("", "")), 1),
            Series(1, "", listOf(ItemSeries("", "")), 1),
            Stories(1, "", listOf(ItemStories("", "", "")), 1),
            Events(1, "", listOf(ItemEvent("", "")), 1)
        )
        hero2 = Hero(
            2, "EmptyName",
            false,
            Thumbnail("", ""),
            Comics(1, "", listOf(ItemComic("", "")), 1),
            Series(1, "", listOf(ItemSeries("", "")), 1),
            Stories(1, "", listOf(ItemStories("", "", "")), 1),
            Events(1, "", listOf(ItemEvent("", "")), 1)
        )

    }


    private fun initDataSet() {
        val dataSet = mutableListOf<Hero>()
        for (i in 0..Constants.limit) {
            dataSet.add(Hero(i,"EmptyName",
                false,
                Thumbnail("",""),
                Comics(1,"", listOf(ItemComic ("","")),1),
                Series(1,"", listOf(ItemSeries ("","")),1),
                Stories(1,"", listOf(ItemStories ("","","")),1),
                Events(1,"", listOf(ItemEvent ("","")),1)
            ))
        }

        apiHeroResponse = ApiHeroResponse(200,"ok",Data(dataSet))

    }
}
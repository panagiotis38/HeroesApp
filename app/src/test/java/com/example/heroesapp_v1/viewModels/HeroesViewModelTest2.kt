/**package com.example.heroesapp_v1.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import com.example.heroesapp_v1.front.utils.Constants
import com.example.heroesapp_v1.database.FavoriteHeroesDB
import com.example.heroesapp_v1.model.*
import com.example.heroesapp_v1.repository.HeroNetworkDataSourceImpl
import com.example.heroesapp_v1.repository.HeroRepositoryImpl
import junit.framework.Assert.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class HeroesViewModelTest2 {

    lateinit var heroNetworkDataSource: HeroNetworkDataSourceImpl

    lateinit var heroRepository: HeroRepositoryImpl
    @Mock
    lateinit var observer: Observer<List<Hero>>

    private val application: Application = ApplicationProvider.getApplicationContext()

    private lateinit var viewModel: HeroesViewModel


    private val dataSet = mutableListOf<Hero>()
    private val heroList = MutableLiveData<List<Hero>>()






    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        heroNetworkDataSource = HeroNetworkDataSourceImpl(application)
        val dbInstance = FavoriteHeroesDB.getInstance(application)
        val heroesDao = dbInstance.favoriteHeroesDao()
        //heroRepository = HeroRepository(heroNetworkDataSource, heroesDao)
        viewModel = HeroesViewModel(application)

        viewModel.getHeroList().observeForever(observer)
        initDataSet()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getHeroList() {
        runBlocking {
            `when`(heroRepository.getHeroList(0,10)).thenReturn(dataSet)
            val ds = viewModel.retrieveNewHeroDataSet(0)
            assertNull(ds)
            assertTrue(viewModel.getHeroList().hasObservers())
        }
    }

    @Test
    fun retrieveNewHeroDataSet() {
    }

    @Test
    fun addDeleteFavoriteHero() {
    }

    private fun initDataSet() {

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
    }
}*/


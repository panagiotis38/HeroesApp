package com.example.heroesapp_v1.front.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.heroesapp_v1.HeroApplication
import com.example.heroesapp_v1.front.utils.Constants
import com.example.heroesapp_v1.front.utils.CustomEvent
import com.example.heroesapp_v1.R
import com.example.heroesapp_v1.di.HeroesComponent
import com.example.heroesapp_v1.front.fragments.HeroFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class HomeActivity : AppCompatActivity() {

    lateinit var heroesComponent: HeroesComponent

    private lateinit var heroFragment: HeroFragment

    override fun onCreate(savedInstanceState: Bundle?) {

        heroesComponent = (application as HeroApplication).appComponent.heroesComponent().create()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val isHeroFavorite = intent.getBooleanExtra("isFavorite",false)

        heroFragment = HeroFragment.newInstance()
        val bundle = Bundle()
        bundle.putBoolean("isFavorite",isHeroFavorite)
        heroFragment.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, heroFragment, "heroList")
            .commit()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.toolbar_menu,menu)
        val searchItem = menu?.findItem(R.id.menu_search)
        if (searchItem != null) {

            val searchView = searchItem.actionView as SearchView
            searchView.queryHint = Constants.heroSearchHint
            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null && newText != "") {
                        val searchString = newText.toLowerCase()
                        //heroFragment.onHeroSearch(searchString)
                    }
                    return true
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    @Subscribe (threadMode = ThreadMode.MAIN)
    fun onEventReceived(event: CustomEvent) {
        Log.e("ERROR 401","Received 401 unauthorized for 5 consecutive times")
        Toast.makeText(this, event.data, Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }


}


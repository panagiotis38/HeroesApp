package com.example.heroesapp_v1.front.activities

import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.heroesapp_v1.utils.Constants
import com.example.heroesapp_v1.R
import com.example.heroesapp_v1.front.fragments.HeroFragment


class HomeActivity : AppCompatActivity() {

    private lateinit var heroFragment: HeroFragment

    override fun onCreate(savedInstanceState: Bundle?) {
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
                        heroFragment.onHeroSearch(searchString)
                    }
                    return true
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }
}


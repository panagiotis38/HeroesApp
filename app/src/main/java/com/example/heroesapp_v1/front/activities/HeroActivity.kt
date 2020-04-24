package com.example.heroesapp_v1.front.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.heroesapp_v1.utils.Constants
import com.example.heroesapp_v1.R
import com.example.heroesapp_v1.front.adapters.ExpandableListAdapter
import com.example.heroesapp_v1.front.utils.Communicator.Companion.isHeroInDetailsFavorite
import com.example.heroesapp_v1.model.Hero
import com.example.heroesapp_v1.viewModels.HeroesViewModel
import kotlinx.android.synthetic.main.activity_hero.*


class HeroActivity : AppCompatActivity() {


    private val groupTitle: MutableList<String> = ArrayList()
    private val body: MutableList<MutableList<String>> = ArrayList()
    private var listOfComics: MutableList<String> = ArrayList()
    private var listOfSeries: MutableList<String> = ArrayList()
    private var listOfStories: MutableList<String> = ArrayList()
    private var listOfEvents: MutableList<String> = ArrayList()

    private lateinit var viewModel: HeroesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero)

        viewModel = HeroesViewModel(this)
        val hero = intent.getParcelableExtra<Hero>("hero")
        isHeroInDetailsFavorite = intent.getBooleanExtra("isFavorite",false)

        if (hero != null) {
            bindViewsToLayout(hero, isHeroInDetailsFavorite)
            observeFavorites()
            addFavoriteHeroClickListener(hero.id)
            setUpExpandableList(hero)
        } else {
            Toast.makeText(this,"Hero object is null",Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeFavorites() {
        viewModel.isHeroFavorite.observe(this, Observer{
            isHeroInDetailsFavorite = it
            if (isHeroInDetailsFavorite) {
                image_button_details.setBackgroundResource(R.drawable.ic_star_hero_favorite)
            } else {
                image_button_details.setBackgroundResource(R.drawable.ic_star_hero_not_favorite)
            }
        })
    }


    private fun bindViewsToLayout(hero: Hero, isFavorite: Boolean) {

        text_view_hero_name.text = hero.name
        if(isFavorite) {
            image_button_details.setBackgroundResource(R.drawable.ic_star_hero_favorite)
        }
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_default_hero_img)
            .error(R.drawable.ic_error_hero_img)

        Glide.with(this)
            .applyDefaultRequestOptions(requestOptions)
            .load(hero.thumbnail.getImageUrl()).into(image_view_hero_details)
    }

    private fun setUpExpandableList(hero: Hero) {

        for (i in hero.comics.items.indices) {
            listOfComics.add(hero.comics.items[i].name)
        }
        for (i in hero.series.items.indices) {
            listOfSeries.add(hero.series.items[i].name)
        }
        for (i in hero.stories.items.indices) {
            listOfStories.add(hero.stories.items[i].name)
        }
        for (i in hero.events.items.indices) {
            listOfEvents.add(hero.events.items[i].name)
        }

        text_view_exp_list_title.text = Constants.participationTitle

        groupTitle.add("Comics")
        groupTitle.add("Series")
        groupTitle.add("Stories")
        groupTitle.add("Events")

        body.add(listOfComics)
        body.add(listOfSeries)
        body.add(listOfStories)
        body.add(listOfEvents)

        expandable_list_view.setAdapter(ExpandableListAdapter(this,groupTitle,body))
    }


    private fun addFavoriteHeroClickListener(heroId: Int) {
        image_button_details.setOnClickListener {
            viewModel.addDeleteFavoriteHero(heroId)
            viewModel.isHeroFavorite(heroId)
        }
    }
}

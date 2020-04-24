package com.example.heroesapp_v1.front.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.heroesapp_v1.utils.Constants
import com.example.heroesapp_v1.R
import com.example.heroesapp_v1.model.Hero
import kotlinx.android.synthetic.main.list_item_heroe.view.*


class HeroesAdapter(private val heroClickListener: HeroClickListener) :  RecyclerView.Adapter<ViewHolder>(){

    private var itemsToDisplay : List<Hero> = emptyList()
    private var favoriteList = ArrayList<Boolean>()
    var areMoreDataAvailable = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_heroe, parent, false),
            heroClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewsToViewHolder(itemsToDisplay[position], favoriteList[position])
    }

    override fun getItemCount(): Int {
        return itemsToDisplay.size
    }

    fun getDataSet(): List<Hero> {
        return itemsToDisplay
    }

    fun updateFavoriteList (position: Int, isFavorite: Boolean) {
        favoriteList[position] = isFavorite
        notifyItemChanged(position)
    }

    fun setData (heroList: List<Hero>) {
        // Check if no more data from API exist
        if (heroList.isNullOrEmpty()) {
            areMoreDataAvailable = false
            return
        }
        // Set initial data to list
        if (itemsToDisplay.isEmpty() || heroList.size == Constants.limit) {
            itemsToDisplay = heroList
            for (i in heroList.indices) {
                favoriteList.add(false)
            }
            notifyDataSetChanged()
        }
        // Append new data to list
        else {
            val newData = heroList.subList(itemCount,heroList.size)
            (itemsToDisplay as ArrayList).addAll(newData)
            for (i in 0 until Constants.limit) {
                favoriteList.add(false)
            }
            notifyDataSetChanged()
        }
    }

    fun clearData() {
        val size = itemsToDisplay.size
        (itemsToDisplay as ArrayList).clear()
        notifyItemRangeRemoved(0, size)
    }
}




class ViewHolder constructor( itemView: View, private val heroClickListener: HeroClickListener): RecyclerView.ViewHolder(itemView) {

    private val heroName = itemView.hero_name
    private val heroImg = itemView.hero_image
    private val favoriteButton = itemView.image_button_list

    fun bindViewsToViewHolder(hero: Hero, isFavorite: Boolean) {

        heroName.text = hero.name
        if (isFavorite) {
            favoriteButton.setBackgroundResource(R.drawable.ic_star_hero_favorite)
        } else {
            favoriteButton.setBackgroundResource(R.drawable.ic_star_hero_not_favorite)
        }

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_default_hero_img)
            .error(R.drawable.ic_error_hero_img)

        Glide.with(itemView.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(hero.thumbnail.getImageUrl()).into(heroImg)

        itemView.setOnClickListener {
            heroClickListener.onHeroClicked(adapterPosition, isFavorite)
        }

        favoriteButton.setOnClickListener {
            heroClickListener.onFavoriteHeroClicked(adapterPosition, favoriteButton)
        }
    }
}







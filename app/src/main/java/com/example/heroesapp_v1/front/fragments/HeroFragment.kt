package com.example.heroesapp_v1.front.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heroesapp_v1.utils.Constants
import com.example.heroesapp_v1.R
import com.example.heroesapp_v1.front.activities.HeroActivity
import com.example.heroesapp_v1.front.adapters.EndlessScrollListener
import com.example.heroesapp_v1.front.adapters.HeroClickListener
import com.example.heroesapp_v1.front.adapters.HeroesAdapter
import com.example.heroesapp_v1.front.utils.Communicator.Companion.isHeroInDetailsFavorite
import com.example.heroesapp_v1.front.utils.HeroListItemDecoration
import com.example.heroesapp_v1.viewModels.HeroesViewModel


class HeroFragment : Fragment(), HeroClickListener {

    private lateinit var heroAdapter: HeroesAdapter
    private lateinit var viewModel: HeroesViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var scrollListener: EndlessScrollListener
    private lateinit var progressBar: ProgressBar
    private var adapterPosition = -1



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_hero, container, false)
        val context = activity as Context
        viewModel = HeroesViewModel(context)
        linearLayoutManager = LinearLayoutManager(context)
        heroAdapter = HeroesAdapter(this)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)

        initRecyclerView(linearLayoutManager,recyclerView)
        getHeroListFromApi(0,viewModel)
        setUpScrollListener(recyclerView,linearLayoutManager, viewModel, progressBar)
        observeFavorites()

        return  view
    }


    override fun onResume() {
        super.onResume()
        if (adapterPosition != -1) {
            if (isHeroInDetailsFavorite) {
                heroAdapter.updateFavoriteList(adapterPosition, true)
            } else {
                heroAdapter.updateFavoriteList(adapterPosition, false)
            }
        }
    }


    fun onHeroSearch(searchString: String){
        heroAdapter.clearData()
        scrollListener.resetState()
        searchHeroListFromApi(0,searchString,viewModel)
    }


    private fun initRecyclerView(linearLayoutManager: LinearLayoutManager, recyclerView: RecyclerView) {
        recyclerView.apply {
            adapter = heroAdapter
            layoutManager = linearLayoutManager
            val decoration = HeroListItemDecoration(Constants.cardViewDecorationPadding)
            addItemDecoration(decoration)
        }
    }


    private fun setUpScrollListener(recyclerView: RecyclerView, linearLayoutManager: LinearLayoutManager, viewModel: HeroesViewModel, progressBar: ProgressBar){
        // Init scrollListener. Retain an instance to be able to call resetState() for fresh searches
        scrollListener = object: EndlessScrollListener(linearLayoutManager, progressBar){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                if (heroAdapter.areMoreDataAvailable) {
                    getHeroListFromApi(page, viewModel)
                }
            }
        }
        recyclerView.addOnScrollListener(scrollListener)
    }


    private fun getHeroListFromApi(page: Int, viewModel: HeroesViewModel) {
        viewModel.getHeroList(page)
            .observe(viewLifecycleOwner, Observer {
                heroAdapter.setData(it)
            })
    }

    private fun searchHeroListFromApi(page: Int, nameStartsWith: String, viewModel: HeroesViewModel) {
        viewModel.searchHeroList(page,nameStartsWith)
            .observe(this, Observer {
                if (!it.isNullOrEmpty()){
                    if (it[it.lastIndex].id == 1) {
                        val noResultFragment = NoSearchResultFragment()
                        adapterPosition = -1
                        activity?.supportFragmentManager
                            ?.beginTransaction()
                            ?.addToBackStack("heroList")
                            ?.replace(R.id.container, noResultFragment, "noResultFragment")
                            ?.commit()
                    }
                }
                heroAdapter.setData(it)
            })
    }


    override fun onHeroClicked(position: Int, isFavorite: Boolean) {
        val hero = heroAdapter.getDataSet()[position]
        adapterPosition = position

        val intent = Intent(context,HeroActivity::class.java)
        intent.putExtra("hero", hero)
        intent.putExtra("isFavorite", isFavorite)
        startActivity(intent)
    }


    override fun onFavoriteHeroClicked(position: Int, view: ImageButton) {
        val heroId = heroAdapter.getDataSet()[position].id
        viewModel.addDeleteFavoriteHero(heroId)
        adapterPosition = position
        viewModel.isHeroFavorite(heroId)
    }


    private fun observeFavorites() {
        viewModel.isHeroFavorite.observe(viewLifecycleOwner, Observer{
            val isFavorite = it
            if (isFavorite) {
                heroAdapter.updateFavoriteList(adapterPosition, true)
            } else {
                heroAdapter.updateFavoriteList(adapterPosition, false)
            }
        })
    }

    companion object {
        // returns a new instance of HeroFragment
        @JvmStatic
        fun newInstance(): HeroFragment {
            return HeroFragment()
        }
    }
}


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
import com.example.heroesapp_v1.HeroApplication
import com.example.heroesapp_v1.front.utils.Constants
import com.example.heroesapp_v1.R
import com.example.heroesapp_v1.front.activities.HeroActivity
import com.example.heroesapp_v1.front.activities.HomeActivity
import com.example.heroesapp_v1.front.adapters.EndlessScrollListener
import com.example.heroesapp_v1.front.adapters.HeroClickListener
import com.example.heroesapp_v1.front.adapters.HeroesAdapter
import com.example.heroesapp_v1.front.utils.Communicator
import com.example.heroesapp_v1.front.utils.HeroListItemDecoration
import com.example.heroesapp_v1.viewModels.HeroesViewModel
import javax.inject.Inject


class HeroFragment : Fragment(), HeroClickListener {

    private lateinit var heroAdapter: HeroesAdapter

    @Inject
    lateinit var viewModel: HeroesViewModel

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var scrollListener: EndlessScrollListener
    private lateinit var progressBar: ProgressBar
    //private var savedAdapterPosition = -1
    //private lateinit var savedHero: Hero


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as HomeActivity).heroesComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_hero, container, false)
        val context = activity as Context

        // Changed on 30/04
        //viewModel = ViewModelProvider(this).get(HeroesViewModel::class.java)

        linearLayoutManager = LinearLayoutManager(context)
        heroAdapter = HeroesAdapter(this)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)

        initRecyclerView(recyclerView)
        observeHeroList()
        //observeFavorites()
        // Retrieve initial data
        viewModel.retrieveNewHeroDataSet(0)


        setUpScrollListener(recyclerView)


        return  view
    }

    override fun onResume() {
        super.onResume()
        if (Communicator.rememberAdapterPosition != -1) {
            heroAdapter.updateFavoriteHero(Communicator.rememberHero, Communicator.rememberAdapterPosition)
        }
    }


    /**fun onHeroSearch(searchString: String){
        heroAdapter.clearData()
        scrollListener.resetState()
        searchHeroListFromApi(0,searchString,viewModel)
    }*/


    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.apply {
            adapter = heroAdapter
            layoutManager = linearLayoutManager
            val decoration = HeroListItemDecoration(Constants.cardViewDecorationPadding)
            addItemDecoration(decoration)
        }
    }


    private fun setUpScrollListener(recyclerView: RecyclerView){
        // Init scrollListener. Retain an instance to be able to call resetState() for fresh searches
        scrollListener = object: EndlessScrollListener(linearLayoutManager, progressBar){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                /**if (heroAdapter.areMoreDataAvailable) {

                }*/
                viewModel.retrieveNewHeroDataSet(page)
            }
        }
        recyclerView.addOnScrollListener(scrollListener)
    }



/**
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
    }*/


    override fun onHeroClicked(position: Int, isFavorite: Boolean) {
        val hero = heroAdapter.getDataSet()[position]
        Communicator.rememberAdapterPosition = position
        Communicator.rememberHero = hero

        val intent = Intent(context,HeroActivity::class.java)
        intent.putExtra("hero", hero)
        //intent.putExtra("isFavorite", isFavorite)
        startActivity(intent)
    }


    override fun onFavoriteHeroClicked(position: Int, view: ImageButton) {
        val hero = heroAdapter.getDataSet()[position]
        viewModel.addDeleteFavoriteHero(hero)
        //adapterPosition = position
        //viewModel.isHeroFavorite(hero.id)
    }


    /**private fun observeFavorites() {
        viewModel.getFavoriteHeroList()
            .observe(viewLifecycleOwner, Observer {
                heroAdapter.setFavorites(it)
            })
    }*/

    private fun observeHeroList() {
        viewModel.getHeroList()
            .observe(viewLifecycleOwner, Observer {
                heroAdapter.setData(it)
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

/**override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)

requireActivity().onBackPressedDispatcher.addCallback(this, object:OnBackPressedCallback(true) {
override fun handleOnBackPressed() {
activity?.runOnUiThread {
Toast.makeText(context, "Back button was pressed", Toast.LENGTH_SHORT).show()
}
}
})
}*/

/**private fun preserveData() {
preservedHeroList = heroAdapter.getItemsToDisplay()
//preservedFavoriteList = heroAdapter.

}*/



/**viewModel.isHeroFavorite.observe(viewLifecycleOwner, Observer{
val isFavorite = it
if (isFavorite) {
heroAdapter.updateFavoriteList(adapterPosition, true)
/**activity?.runOnUiThread {
Toast.makeText(context, "Hero is favorite", Toast.LENGTH_SHORT).show()
}*/
} else {
heroAdapter.updateFavoriteList(adapterPosition, false)
/**activity?.runOnUiThread {
Toast.makeText(context, "Hero is not favorite", Toast.LENGTH_SHORT).show()
}*/
}
})*/

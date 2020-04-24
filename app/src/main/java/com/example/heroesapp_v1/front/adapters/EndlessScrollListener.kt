package com.example.heroesapp_v1.front.adapters


import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class EndlessScrollListener ( linearLayoutManager: LinearLayoutManager,
                                       progressBar: ProgressBar
): RecyclerView.OnScrollListener() {

    private val visibleThreshold = 7
    private var currentPage = 0
    private var previousTotalItemsCount = 0
    private var isLoading = true
    private val startingPageIndex = 0
    private val layoutManager = linearLayoutManager
    private val loadingBar = progressBar


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

        val totalItemCount = layoutManager.itemCount
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

        // If true the list is invalid and we should reset to the initial state
        if (totalItemCount < previousTotalItemsCount) {
            currentPage = startingPageIndex
            previousTotalItemsCount = totalItemCount
            if (totalItemCount == 0) isLoading = true
        }

        // If it is loading and the dataSet has changed then the loading has been finished
        if (isLoading && (totalItemCount > previousTotalItemsCount)) {
            isLoading = false
            loadingBar.visibility = View.GONE
            previousTotalItemsCount = totalItemCount
        }

        // If it is not loading and we have breached the threshold we need to fetch more data
        if (!isLoading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
            currentPage++
            onLoadMore (currentPage,totalItemCount, recyclerView)
            isLoading = true
            loadingBar.visibility = View.VISIBLE
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

        // Check if we have reached the bottom of the list
        if (!recyclerView.canScrollVertically(1)) {
            isLoading = false
            loadingBar.visibility = View.GONE
        }
    }

    fun resetState() {
        currentPage = startingPageIndex
        previousTotalItemsCount = 0
        isLoading = true
        loadingBar.visibility = View.VISIBLE
    }

    abstract fun onLoadMore (page: Int, totalItemsCount: Int, view: RecyclerView)

}
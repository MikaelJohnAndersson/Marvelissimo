package com.ecutbildning.marvelissimo.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ecutbildning.marvelissimo.R
import com.ecutbildning.marvelissimo.adapters.ComicRecycleViewAdapter


import com.ecutbildning.marvelissimo.dtos.Comic
import com.ecutbildning.marvelissimo.dtos.ComicResponse

import com.ecutbildning.marvelissimo.services.MarvelAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*

private const val LIMIT = 50
private const val GRID_SPAN_COUNT = 3

class ComicsSearchFragment : Fragment(), SearchFragment {

    private var offset = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadMoreData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)
        setUpRecycleView(rootView)
        return rootView
    }

    private fun setUpRecycleView(rootView: View, comicList: MutableList<Comic> = mutableListOf() ){
        val layoutManager = GridLayoutManager(activity, GRID_SPAN_COUNT)
        val recyclerView = rootView.recyclerView
        recyclerView.layoutManager = layoutManager
        val adapter = ComicRecycleViewAdapter(activity as Context, comicList) { comic -> onItemClicked(comic)}
        recyclerView.adapter = adapter

        //TODO: Implement solution for earlier versions, or change min SDK
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            recyclerView.setOnScrollChangeListener { _, _, _, _, _ ->
                run {
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                    //Start loading more characters if scroll is at bottom
                    if (((pastVisibleItems + visibleItemCount) >= totalItemCount) && !adapter.loading) {
                        loadMoreData()
                        adapter.loading = true
                    }
                }
            }
        }
    }

    override fun loadMoreData() {
        MarvelAPI.getComics().getAllComics(LIMIT, offset)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response: ComicResponse? ->
                if (response != null) {
                    val adapter = recyclerView.adapter as ComicRecycleViewAdapter
                    adapter.comics.addAll(response.data.results)
                    adapter.notifyDataSetChanged()
                    adapter.loading = false
                    Toast.makeText(activity, "Loading more comics...", Toast.LENGTH_LONG).show()
                }
            }
        offset += LIMIT
    }

    private fun onItemClicked(comic: Comic){
        activity?.supportFragmentManager?.beginTransaction()
           // ?.replace(R.id.container, CharacterInfoFragment.newInstance(comic))
            ?.commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ComicsSearchFragment()
    }


}
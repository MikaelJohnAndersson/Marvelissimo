package com.ecutbildning.marvelissimo.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ecutbildning.marvelissimo.R
import com.ecutbildning.marvelissimo.adapters.ComicRecycleViewAdapter


import com.ecutbildning.marvelissimo.dtos.Comic
import com.ecutbildning.marvelissimo.dtos.ComicResponse

import com.ecutbildning.marvelissimo.services.MarvelAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_character_search.*
import kotlinx.android.synthetic.main.fragment_character_search.view.*

class ComicsSearchFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MarvelAPI.getComics().getAllComics()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response: ComicResponse? ->
                if (response != null) {
                    val comicList : List<Comic> = response.data.results
                    //Getting adapter and setting data source to character list from response
                    val adapter = recyclerView.adapter as ComicRecycleViewAdapter
                    adapter.comics = comicList
                    adapter.notifyDataSetChanged()
                }
            }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_character_search, container, false)
        setUpRecycleView(rootView)
        return rootView
    }
    private fun setUpRecycleView(rootView: View, comicList: List<Comic> = mutableListOf() ){
        val layoutManager = GridLayoutManager(activity, 3)
        val recyclerView = rootView.recyclerView
        recyclerView.layoutManager = layoutManager
        val adapter = ComicRecycleViewAdapter(activity as Context, comicList) { comic -> onItemClicked(comic)}
        recyclerView.adapter = adapter
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
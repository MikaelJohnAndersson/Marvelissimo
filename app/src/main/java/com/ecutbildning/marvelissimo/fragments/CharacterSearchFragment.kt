package com.ecutbildning.marvelissimo.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ecutbildning.marvelissimo.R
import com.ecutbildning.marvelissimo.adapters.CharacterRecycleViewAdapter
import com.ecutbildning.marvelissimo.dtos.Character
import com.ecutbildning.marvelissimo.dtos.Response
import com.ecutbildning.marvelissimo.services.MarvelAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_character_search.*
import kotlinx.android.synthetic.main.fragment_character_search.view.*

class CharacterSearchFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MarvelAPI.getService().getAllCharacters()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response: Response? ->
                if (response != null) {
                    val characterList : List<Character> = response.data.results
                    //Getting adapter and setting data source to character list from response
                    val adapter = recyclerView.adapter as CharacterRecycleViewAdapter
                    adapter.characters = characterList
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

    private fun onItemClicked(character: Character){
         activity?.supportFragmentManager?.beginTransaction()
            ?.add(R.id.container, CharacterInfoFragment.newInstance(character))
             ?.addToBackStack(null)
            ?.commit()
    }

    private fun setUpRecycleView(rootView: View, characterList: List<Character> = mutableListOf() ){
        val layoutManager = GridLayoutManager(activity, 3)
        val recyclerView = rootView.recyclerView
        recyclerView.layoutManager = layoutManager
        val adapter = CharacterRecycleViewAdapter(activity as Context, characterList) { character -> onItemClicked(character)}
        recyclerView.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = CharacterSearchFragment()
    }
}

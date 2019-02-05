package com.ecutbildning.marvelissimo.fragments

import android.content.Context
import android.os.Build
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


private const val LIMIT = 50
private const val GRID_SPAN_COUNT = 3

class CharacterSearchFragment : Fragment() {

    private var offset = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadMoreCharacters()
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
            ?.replace(R.id.container, CharacterInfoFragment.newInstance(character))
            ?.commit()
    }

    private fun setUpRecycleView(rootView: View, characterList: MutableList<Character> = mutableListOf() ){
        val layoutManager = GridLayoutManager(activity, GRID_SPAN_COUNT)
        val recyclerView = rootView.recyclerView
        recyclerView.layoutManager = layoutManager
        val adapter = CharacterRecycleViewAdapter(activity as Context, characterList) { character -> onItemClicked(character)}
        recyclerView.adapter = adapter

        //TODO: Implement solution for earlier versions, or change min SDK
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            recyclerView.setOnScrollChangeListener { _, _, _, _, _ ->
                run {
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                    //Start loading more characters at span count * {ROW} from bottom
                    if (((pastVisibleItems + visibleItemCount) + (GRID_SPAN_COUNT * 4) >= totalItemCount) && !adapter.loading) {
                        loadMoreCharacters()
                        adapter.loading = true
                    }
                }
            }
        }
    }

    private fun loadMoreCharacters(){
        MarvelAPI.getService().getAllCharacters(LIMIT, offset)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response: Response? ->
                if (response != null) {
                    val adapter = recyclerView.adapter as CharacterRecycleViewAdapter
                    adapter.characters.addAll(response.data.results)
                    adapter.notifyDataSetChanged()
                    adapter.loading = false
                }
            }
        offset += LIMIT
    }

    companion object {
        @JvmStatic
        fun newInstance() = CharacterSearchFragment()
    }
}

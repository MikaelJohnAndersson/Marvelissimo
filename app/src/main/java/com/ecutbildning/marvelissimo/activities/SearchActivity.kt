package com.ecutbildning.marvelissimo.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ecutbildning.marvelissimo.R
import com.ecutbildning.marvelissimo.dtos.Response
import com.ecutbildning.marvelissimo.services.MarvelAPI
import android.support.v7.widget.LinearLayoutManager
import com.ecutbildning.marvelissimo.adapters.CharacterRecycleViewAdapter
import com.ecutbildning.marvelissimo.dtos.Character
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers;
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        //Initializing recycleview with empty list
        setUpRecycleView(mutableListOf())

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

    private fun setUpRecycleView(characterList: List<Character>){
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager

        val adapter = CharacterRecycleViewAdapter(this, characterList )
        recyclerView.adapter = adapter
    }
}

package com.ecutbildning.marvelissimo.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.ecutbildning.marvelissimo.R
import com.ecutbildning.marvelissimo.dtos.Response
import com.ecutbildning.marvelissimo.services.MarvelAPI

import com.ecutbildning.marvelissimo.adapters.CharacterRecycleViewAdapter
import com.ecutbildning.marvelissimo.dtos.Character
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers;
import kotlinx.android.synthetic.main.fragment_character_search.*

//TODO: Convert this class into fragment
class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_character_search)

        //Initializing recycleview with empty list
        //TODO: Better solution for initializing recyclerView?
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

    private fun onItemClicked(character: Character){
        val intent = Intent(this, InfoActivity::class.java).apply {
            action = Intent.ACTION_SEND
            putExtra("CHARACTER", character)
        }
        // Verify that the intent will resolve to an activity
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun setUpRecycleView(characterList: List<Character>){
        val layoutManager = GridLayoutManager(this, 3)

        recyclerView.layoutManager = layoutManager

        val adapter = CharacterRecycleViewAdapter(this, characterList) { character -> onItemClicked(character)}
        recyclerView.adapter = adapter
    }
}

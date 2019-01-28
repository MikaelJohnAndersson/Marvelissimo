package com.ecutbildning.marvelissimo.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ecutbildning.marvelissimo.R
import com.ecutbildning.marvelissimo.dtos.Response
import com.ecutbildning.marvelissimo.services.MarvelAPI
import android.os.StrictMode
import com.ecutbildning.marvelissimo.dtos.Character
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers;


class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val characters : Observable<Response> = MarvelAPI.getService().getAllCharacters()

        characters
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response: Response? ->
            if (response != null) {
                val characterList : List<Character> = response.data.results
                Log.d("SEARCH_ACTIVITY", "Response count: ${response.data.count}")
                characterList.forEach {character ->
                    Log.d("SEARCH_ACTIVITY", "Character id: ${ character.id}\nCharacter name: ${character.name}\nCharacter description: ${character.description}")
                }
            }
        }

    }
}

package com.ecutbildning.marvelissimo.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ecutbildning.marvelissimo.R
import com.ecutbildning.marvelissimo.dtos.Response
import com.ecutbildning.marvelissimo.services.MarvelAPI
import io.reactivex.Observable
import android.os.StrictMode
import com.ecutbildning.marvelissimo.dtos.Character


class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        //Cancel detection of all possible slow/uneffective thread operations
        //TODO: Implement async operation?
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val characters : Observable<Response>  = MarvelAPI.getService().getAllCharacters()

        characters.subscribe { response: Response? ->
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

package com.ecutbildning.marvelissimo.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.ecutbildning.marvelissimo.R
import com.ecutbildning.marvelissimo.dtos.Character
import com.ecutbildning.marvelissimo.dtos.Response
import com.ecutbildning.marvelissimo.services.MarvelAPI
import com.google.gson.internal.bind.TypeAdapters.CHARACTER
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val character: Character = intent.getParcelableExtra("CHARACTER")

        val title = findViewById<TextView>(R.id.title)
        title.text = character.name
        val infoImg = findViewById<ImageView>(R.id.info_img)
        Picasso.get()
            .load(character.thumbnail.getUrl())
            .fit()
            .into(infoImg)


      



    }
    override fun

    }


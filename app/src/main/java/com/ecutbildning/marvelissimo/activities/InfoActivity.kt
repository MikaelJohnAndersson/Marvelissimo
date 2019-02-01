package com.ecutbildning.marvelissimo.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*
import com.ecutbildning.marvelissimo.R
import com.ecutbildning.marvelissimo.adapters.ExpansionPanelAdapter
import com.ecutbildning.marvelissimo.dtos.Character
import com.ecutbildning.marvelissimo.dtos.Response
import com.ecutbildning.marvelissimo.services.MarvelAPI
import com.google.gson.internal.bind.TypeAdapters.CHARACTER
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {

    internal var expandableListView: ExpandableListView? = null
    internal var adapter: ExpandableListAdapter? = null
    internal var titleList: List<String>? = null


    val data: HashMap<String, List<String>>
        get() {
            val listData = HashMap<String, List<String>>()

            val description = ArrayList<String>()
            description.add("Whatever description")

            val comics = ArrayList<String>()
            comics.add("whatever comic")

            val stories = ArrayList<String>()
            stories.add("whatever stories")

            val events = ArrayList<String>()
            events.add("whatever events")

            val series = ArrayList<String>()
            series.add("Whatever serie")

            listData["Description"] = description
            listData["Comics"] = comics
            listData["Stories"] = stories
            listData["Events"] = events
            listData["Series"] = series

            return listData
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

         val character: Character =  intent.getParcelableExtra("CHARACTER")

        val title = findViewById<TextView>(R.id.title)
        title.text = character.name
        val infoImg = findViewById<ImageView>(R.id.info_img)
        Picasso.get()
            .load(character.thumbnail.getUrl())
            .fit()
            .into(infoImg)


        //----------------------------------
        //expansion panel with info
        //----------------------------------
        expandableListView = findViewById(R.id.expandableListView)
        if (expandableListView != null) {
            val listData = data
            titleList = ArrayList(listData.keys)
            adapter = ExpansionPanelAdapter(this, titleList as ArrayList<String>, listData)
            expandableListView!!.setAdapter(adapter)

            expandableListView!!.setOnGroupExpandListener { groupPosition ->
                Toast.makeText(
                    applicationContext,
                    (titleList as ArrayList<String>)[groupPosition] + " List Expanded.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            expandableListView!!.setOnGroupCollapseListener { groupPosition ->
                Toast.makeText(
                    applicationContext,
                    (titleList as ArrayList<String>)[groupPosition] + " List Collapsed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            //To Use if child is clickable:

            /*expandableListView!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
                Toast.makeText(
                    applicationContext,
                    "Clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + listData[(titleList as ArrayList<String>)[groupPosition]]!!.get(
                        childPosition
                    ),
                    Toast.LENGTH_SHORT
                ).show()
                false
            }*/
        }


    }

}


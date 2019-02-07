package com.ecutbildning.marvelissimo.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import com.ecutbildning.marvelissimo.R
import com.ecutbildning.marvelissimo.adapters.ExpansionPanelAdapter
import com.ecutbildning.marvelissimo.dtos.Character
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_info.view.*

private const val CHARACTER = "CHARACTER"

class CharacterInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var character: Character? = null

    private var expandableListView: ExpandableListView? = null
    private var adapter: ExpandableListAdapter? = null
    private var titleList: List<String>? = null

    private val data: HashMap<String, List<String>>
        get() {
            val listData = HashMap<String, List<String>>()

            listData["Comics"] = ArrayList()
            listData["Stories"] = ArrayList()
            listData["Events"] = ArrayList()
            listData["Series"] = ArrayList()

            return listData
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            character = it.getParcelable(CHARACTER)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_info, container, false)

        val title = rootView.title
        title.text = character?.name
        val infoImg = rootView.info_img
        Picasso.get()
            .load(character?.thumbnail?.getUrl())
            .fit()
            .into(infoImg)

        expandableListView = rootView.expandableListView
        val listData = data
        if (expandableListView != null) {
           titleList = ArrayList(listData.keys)
           adapter = ExpansionPanelAdapter(activity as Context, titleList as ArrayList<String>, listData)
           expandableListView!!.setAdapter(adapter)
        }

            if(!character?.description.isNullOrEmpty()){
                val description = rootView.description
                description.text = character?.description
            }
            if (!character?.comics?.items.isNullOrEmpty()){
                val comicinfo = character?.comics?.items?.joinToString("\n") { it.name }
                val comicData = listData["Comics"] as ArrayList<String>
                comicData.add(comicinfo!!)
             /* val comics = rootView.comics
                comics.text = comicinfo*/
            }
            if(!character?.stories?.items.isNullOrEmpty()){
                val storiesinfo = character?.stories?.items?.joinToString("\n") { it.name }
                val storiesData = listData["Stories"] as ArrayList<String>
                storiesData.add(storiesinfo!!)
               /* val stories = rootView.stories
                stories.text = storiesinfo*/
            }
            if(!character?.events?.items.isNullOrEmpty()){
                val eventinfo = character?.events?.items?.joinToString("\n") { it.name }
                val eventData = listData["Events"] as ArrayList<String>
                eventData.add(eventinfo!!)
                /*val events = rootView.events
                events.text = eventinfo*/
            }
            if(!character?.series?.items.isNullOrEmpty()){
                val seriesinfo = character?.series?.items?.joinToString("\n") { it.name }
                val seriesData = listData["Series"] as ArrayList<String>
                seriesData.add(seriesinfo!!)
               /* val series = rootView.series
                series.text = seriesinfo*/
            }
        return rootView
    }
    companion object {
        @JvmStatic
        fun newInstance(character: Character) =
            CharacterInfoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(CHARACTER, character)
                }
            }
    }
}

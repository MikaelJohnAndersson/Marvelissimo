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
import kotlinx.android.synthetic.main.fragment_character_info.view.*

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
        arguments?.let {
            character = it.getParcelable(CHARACTER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_character_info, container, false)

        val title = rootView.title
        title.text = character?.name
        val infoImg = rootView.info_img
        Picasso.get()
            .load(character?.thumbnail?.getUrl())
            .fit()
            .into(infoImg)

        expandableListView = rootView.expandableListView
        if (expandableListView != null) {
            val listData = data
            titleList = ArrayList(listData.keys)
            adapter = ExpansionPanelAdapter(activity as Context, titleList as ArrayList<String>, listData)
            expandableListView!!.setAdapter(adapter)
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
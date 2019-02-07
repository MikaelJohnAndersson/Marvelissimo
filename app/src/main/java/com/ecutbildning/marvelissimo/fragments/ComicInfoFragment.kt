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

import com.ecutbildning.marvelissimo.dtos.Comic
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_info.view.*

private const val COMIC = "COMIC"

class ComicInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var comic: Comic? = null

    private var expandableListView: ExpandableListView? = null
    private var adapter: ExpandableListAdapter? = null
    private var titleList: List<String>? = null

    private val data: HashMap<String, List<String>>
        get() {
            val listData = HashMap<String, List<String>>()

            listData["Characters"] = ArrayList()
            listData["Creators"] = ArrayList()
            listData["Stories"] = ArrayList()
            listData["Events"] = ArrayList()

            return listData
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            comic = it.getParcelable(COMIC)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_info, container, false)

        val title = rootView.title
        title.text = comic?.title
        val infoImg = rootView.info_img
        Picasso.get()
            .load(comic?.thumbnail?.getUrl())
            .fit()
            .into(infoImg);

       expandableListView = rootView.expandableListView
        val listData = data
        if (expandableListView != null) {
            titleList = ArrayList(listData.keys)
            adapter = ExpansionPanelAdapter(activity as Context, titleList as ArrayList<String>, listData)
            expandableListView!!.setAdapter(adapter)
        }

        if(!comic?.description.isNullOrEmpty()){
            val description = rootView.description
            description.text = comic?.description
        }
        if(!comic?.characters?.items.isNullOrEmpty()){
            val characterInfo = comic?.characters?.items?.joinToString("\n") { it.name }
            val characterData = listData["Characters"] as ArrayList<String>
            characterData.add(characterInfo!!)
        }
        if (!comic?.creators?.items.isNullOrEmpty()){
            val creatorsInfo = comic?.creators?.items?.joinToString("\n") { it.name }
            val creatorsData = listData["Creators"] as ArrayList<String>
            creatorsData.add(creatorsInfo!!)
        }
        if(!comic?.stories?.items.isNullOrEmpty()){
            val storiesInfo = comic?.stories?.items?.joinToString("\n") { it.name }
            val storiesData = listData["Stories"] as ArrayList<String>
            storiesData.add(storiesInfo!!)
        }
        if(!comic?.events?.items.isNullOrEmpty()){
            val eventInfo = comic?.events?.items?.joinToString("\n") { it.name }
            val eventData = listData["Events"] as ArrayList<String>
            eventData.add(eventInfo!!)
        }
        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance(comic: Comic) =
            ComicInfoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(COMIC, comic)
                }
            }
    }
}

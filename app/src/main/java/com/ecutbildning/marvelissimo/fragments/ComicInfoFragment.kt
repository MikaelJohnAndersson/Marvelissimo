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
import com.ecutbildning.marvelissimo.dtos.Thumbnail
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_info.view.*

class ComicInfoFragment : InfoFragment() {

    override val INFO_OBJECT: String
        get() = "COMIC"

    override val data = hashMapOf<String, List<String>>(
        "Characters" to ArrayList(),
        "Creators" to ArrayList(),
        "Stories" to ArrayList(),
        "Events" to ArrayList()
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = super.onCreateView(inflater, container, savedInstanceState)

        val comic = infoObject as Comic

        val title = rootView?.title
        title?.text = comic.title

        val infoImg = rootView?.info_img
        Picasso.get()
            .load(comic.thumbnail.getUrl(Thumbnail.LANDSCAPE_MEDIUM))
            .fit()
            .into(infoImg)

        if (!comic.description.isNullOrEmpty()) {
            val description = rootView?.description
            description?.text = comic.description
        }

        val characterData = data["Characters"] as ArrayList<String>
        if (!comic.characters.items.isNullOrEmpty()) {
            val characterInfo = comic.characters.items.joinToString("\n") { it.name }
            characterData.add(characterInfo)
        } else characterData.add("No characters found")

        val creatorsData = data["Creators"] as ArrayList<String>
        if (!comic.creators.items.isNullOrEmpty()) {
            val creatorsInfo = comic.creators.items.joinToString("\n") { it.name }
            creatorsData.add(creatorsInfo)
        } else creatorsData.add("No creators found")

        val storiesData = data["Stories"] as ArrayList<String>
        if (!comic.stories.items.isNullOrEmpty()) {
            val storiesInfo = comic.stories.items.joinToString("\n") { it.name }
            storiesData.add(storiesInfo)
        } else creatorsData.add("No stories found")

        val eventData = data["Events"] as ArrayList<String>
        if (!comic.events.items.isNullOrEmpty()) {
            val eventInfo = comic.events.items.joinToString("\n") { it.name }
            eventData.add(eventInfo)
        } else creatorsData.add("No events found")

        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance(comic: Comic) =
            ComicInfoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(INFO_OBJECT, comic)
                }
            }
    }
}

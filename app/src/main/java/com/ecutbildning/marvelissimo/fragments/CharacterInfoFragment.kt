package com.ecutbildning.marvelissimo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ecutbildning.marvelissimo.dtos.Character
import com.ecutbildning.marvelissimo.dtos.Thumbnail
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_info.view.*

class CharacterInfoFragment : InfoFragment() {

    override val INFO_OBJECT: String
        get() = "CHARACTER"

    override val data: HashMap<String, List<String>>
        get() {
            val listData = HashMap<String, List<String>>()

            listData["Comics"] = ArrayList()
            listData["Stories"] = ArrayList()
            listData["Events"] = ArrayList()
            listData["Series"] = ArrayList()

            return listData
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = super.onCreateView(inflater, container, savedInstanceState)

        val listData = data
        val character = infoObject as Character

        val title = rootView?.title
        title?.text = character.name

        val infoImg = rootView?.info_img
        Picasso.get()
            .load(character.thumbnail.getUrl(Thumbnail.LANDSCAPE_MEDIUM))
            .fit()
            .into(infoImg)

        if(!character.description.isEmpty()){
            val description = rootView?.description
            description?.text = character.description
        }

        if (!character.comics.items.isNullOrEmpty()) {
            val comicinfo = character.comics.items.joinToString("\n") { it.name }
            val comicData = listData["Comics"] as ArrayList<String>
            comicData.add(comicinfo)
            /* val comics = rootView.comics
               comics.text = comicinfo*/
        }
        if (!character.stories.items.isNullOrEmpty()) {
            val storiesinfo = character.stories.items.joinToString("\n") { it.name }
            val storiesData = listData["Stories"] as ArrayList<String>
            storiesData.add(storiesinfo)
            /* val stories = rootView.stories
             stories.text = storiesinfo*/
        }
        if (!character.events.items.isNullOrEmpty()) {
            val eventinfo = character.events.items.joinToString("\n") { it.name }
            val eventData = listData["Events"] as ArrayList<String>
            eventData.add(eventinfo)
            /*val events = rootView.events
            events.text = eventinfo*/
        }
        if (!character.series.items.isNullOrEmpty()) {
            val seriesinfo = character.series.items.joinToString("\n") { it.name }
            val seriesData = listData["Series"] as ArrayList<String>
            seriesData.add(seriesinfo)
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
                    putParcelable(INFO_OBJECT, character)
                }
            }
    }
}

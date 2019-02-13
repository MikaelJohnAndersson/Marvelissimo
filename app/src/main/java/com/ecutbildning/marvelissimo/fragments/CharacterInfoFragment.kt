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

    override val data = hashMapOf<String, List<String>>(
        "Comics" to ArrayList(),
        "Stories" to ArrayList(),
        "Events" to ArrayList(),
        "Series" to ArrayList()
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = super.onCreateView(inflater, container, savedInstanceState)

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

        val comicData = data["Comics"] as ArrayList<String>
        if (!character.comics.items.isNullOrEmpty()) {
            val comicinfo = character.comics.items.joinToString("\n") { it.name }
            comicData.add(comicinfo)
        } else comicData.add("No comics found")

        val storiesData = data["Stories"] as ArrayList<String>
        if (!character.stories.items.isNullOrEmpty()) {
            val storiesinfo = character.stories.items.joinToString("\n") { it.name }
            storiesData.add(storiesinfo)
        } else storiesData.add("No stories found")

        val eventData = data["Events"] as ArrayList<String>
        if (!character.events.items.isNullOrEmpty()) {
            val eventinfo = character.events.items.joinToString("\n") { it.name }
            eventData.add(eventinfo)
        } else eventData.add("No events found")

        val seriesData = data["Series"] as ArrayList<String>
        if (!character.series.items.isNullOrEmpty()) {
            val seriesinfo = character.series.items.joinToString("\n") { it.name }
            seriesData.add(seriesinfo)
        } else seriesData.add("No series found")


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

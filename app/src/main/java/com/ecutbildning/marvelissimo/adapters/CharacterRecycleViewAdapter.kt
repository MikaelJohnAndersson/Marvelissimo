package com.ecutbildning.marvelissimo.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.ecutbildning.marvelissimo.R
import com.ecutbildning.marvelissimo.activities.InfoActivity
import com.ecutbildning.marvelissimo.dtos.Character
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_layout.view.*




class CharacterRecycleViewAdapter(val context: Context, var characters : List<Character>, private val itemClickListener: (Character) -> Unit) : RecyclerView.Adapter<CharacterRecycleViewAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterRecycleViewAdapter.CharacterViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterRecycleViewAdapter.CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.bind(character, itemClickListener)
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    inner class CharacterViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bind(character: Character, clickListener: (Character) -> Unit) {
            itemView.title.text = character.name
            itemView.summary.text = character.description
            Picasso.get()
                .load(character.thumbnail.getUrl())
                .fit()
                .into(itemView.thumbnail)
            itemView.setOnClickListener { clickListener(character)}
        }

    }
}
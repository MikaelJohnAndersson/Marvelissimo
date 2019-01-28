package com.ecutbildning.marvelissimo.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ecutbildning.marvelissimo.R
import com.ecutbildning.marvelissimo.dtos.Character
import kotlinx.android.synthetic.main.list_item_layout.view.*


class CharacterRecycleViewAdapter(val context: Context, var characters : List<Character>) : RecyclerView.Adapter<CharacterRecycleViewAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterRecycleViewAdapter.CharacterViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterRecycleViewAdapter.CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.itemView.title.text = character.name
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    inner class CharacterViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
}
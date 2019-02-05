package com.ecutbildning.marvelissimo.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ecutbildning.marvelissimo.R
import com.ecutbildning.marvelissimo.dtos.Comic
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_layout.view.*

class ComicRecycleViewAdapter (val context: Context, var comics : List<Comic>, private val itemClickListener: (Comic) -> Unit) : RecyclerView.Adapter<ComicRecycleViewAdapter.ComicViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicRecycleViewAdapter.ComicViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false)
        return ComicViewHolder(view)
    }

    override fun getItemCount(): Int {
        return comics.size
    }

    override fun onBindViewHolder(holder: ComicRecycleViewAdapter.ComicViewHolder, position: Int) {
        val comic = comics[position]
        holder.bind(comic, itemClickListener)
    }
    inner class ComicViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bind(comic: Comic, clickListener: (Comic) -> Unit) {
            itemView.list_item_title.text = comic.name
            Picasso.get()
                .load(comic.thumbnail.getUrl())
                .fit()
                .into(itemView.thumbnail)
            itemView.setOnClickListener { clickListener(comic)}
        }

    }
}
package com.ecutbildning.marvelissimo.adapters

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ecutbildning.marvelissimo.R
import com.ecutbildning.marvelissimo.dtos.Comic
import com.ecutbildning.marvelissimo.dtos.Thumbnail
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_layout.view.*

class ComicRecycleViewAdapter(private val itemClickListener: (Comic) -> Unit) :
    PagedListAdapter<Comic, ComicRecycleViewAdapter.ComicViewHolder>(comicDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicRecycleViewAdapter.ComicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_layout, parent, false)
        return ComicViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComicRecycleViewAdapter.ComicViewHolder, position: Int) {
        val comic = getItem(position)
        if(comic != null){
            holder.bind(comic, itemClickListener)
        }
    }

    companion object {
        val comicDiff = object : DiffUtil.ItemCallback<Comic>(){
            override fun areItemsTheSame(oldItem: Comic?, newItem: Comic?): Boolean {
                return oldItem?.id == newItem?.id
            }

            override fun areContentsTheSame(oldItem: Comic?, newItem: Comic?): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ComicViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bind(comic: Comic, clickListener: (Comic) -> Unit) {
            itemView.list_item_title.text = comic.title
            Picasso.get()
                .load(comic.thumbnail.getUrl(Thumbnail.STANDARD_MEDIUM))
                .fit()
                .into(itemView.thumbnail)
            itemView.setOnClickListener { clickListener(comic)}
        }

    }
}
package com.sergeybukarev.moviestestapp.presentation.screens.popularmovies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sergeybukarev.domain.dto.ShortMovie
import com.sergeybukarev.moviestestapp.R
import com.sergeybukarev.moviestestapp.databinding.ItemMovieBinding
import toothpick.InjectConstructor

@InjectConstructor
class PopularMoviesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items = ArrayList<ShortMovie>()
    lateinit var onItemClick: (Int) -> Unit

    override fun getItemCount() = items.size

    fun addItems(newItems: List<ShortMovie>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        val itemHolder = holder as ViewHolder
        itemHolder.bind(item, onItemClick)
    }

    class ViewHolder(val views: ItemMovieBinding) : RecyclerView.ViewHolder(views.root) {
        fun bind(item: ShortMovie, onItemClick: (Int) -> Unit) {
            Glide.with(views.imageView).load(item.backdropImage).centerCrop().placeholder(R.drawable.image_placeholder).into(views.imageView)
            views.titleView.text = item.originalTitle
            views.dateView.text = item.releaseDate
            views.root.setOnClickListener { onItemClick(item.id) }
        }
    }
}


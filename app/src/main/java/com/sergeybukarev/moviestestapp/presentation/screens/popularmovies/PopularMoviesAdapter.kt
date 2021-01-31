package com.sergeybukarev.moviestestapp.presentation.screens.popularmovies

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sergeybukarev.domain.dto.ShortMovie
import com.sergeybukarev.domain.dto.ShortMoviePage
import com.sergeybukarev.moviestestapp.R
import com.sergeybukarev.moviestestapp.databinding.ItemMovieBinding
import com.sergeybukarev.moviestestapp.gateway.mappers.DateMapper
import toothpick.InjectConstructor

@InjectConstructor
class PopularMoviesAdapter(
    private val dateMapper: DateMapper,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items = ArrayList<ShortMovie>()
    lateinit var onItemClick: (Int) -> Unit

    override fun getItemCount() = items.size

    fun updatePages(pages: List<ShortMoviePage>) {
        items.clear()
        items.addAll(pages.map { it.movies }.flatten())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        val itemHolder = holder as ViewHolder
        itemHolder.bind(item, dateMapper, onItemClick)
    }

    class ViewHolder(val views: ItemMovieBinding) : RecyclerView.ViewHolder(views.root) {
        fun bind(item: ShortMovie, dateMapper: DateMapper, onItemClick: (Int) -> Unit) {
            Glide.with(views.imageView).load(item.backdropImage).centerCrop().placeholder(R.drawable.image_placeholder).into(views.imageView)
            views.titleView.text = item.originalTitle
            views.dateView.text = dateMapper.mapDate(item.releaseDate)
            views.markView.text = item.ratingPercentage.toString()
            views.markProgressView.progress = item.ratingPercentage
            val hue = views.markProgressView.angle / 3
            views.markProgressView.color = Color.HSVToColor(floatArrayOf(hue, 1F, 1F))
            views.root.setOnClickListener { onItemClick(item.id) }
        }
    }
}


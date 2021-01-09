package com.sergeybukarev.moviestestapp.presentation.screens.moviedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sergeybukarev.domain.dto.Cast
import com.sergeybukarev.moviestestapp.R
import com.sergeybukarev.moviestestapp.databinding.ItemCastBinding
import toothpick.InjectConstructor

@InjectConstructor
class CastAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items = ArrayList<Cast>()

    override fun getItemCount() = items.size

    fun addItems(newItems: List<Cast>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        val itemHolder = holder as ViewHolder
        itemHolder.bind(item)
    }

    class ViewHolder(val views: ItemCastBinding) : RecyclerView.ViewHolder(views.root) {
        fun bind(item: Cast) {
            Glide.with(views.imageView).load(item.avatar).centerCrop().placeholder(R.drawable.image_placeholder).into(views.imageView)
            views.titleView.text = item.name
            views.descriptionView.text = item.character
        }
    }
}

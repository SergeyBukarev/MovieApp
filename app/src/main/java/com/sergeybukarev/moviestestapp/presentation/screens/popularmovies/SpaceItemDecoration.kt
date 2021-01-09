package com.sergeybukarev.moviestestapp.presentation.screens.popularmovies

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class SpacesItemDecoration(private val space: Int) : ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val itemPosition = parent.getChildLayoutPosition(view)

        outRect.bottom = space
        outRect.top = space

        if (itemPosition % 2 == 0) {
            outRect.right = space / 2
            outRect.left = space
        } else {
            outRect.right = space
            outRect.left = space / 2
        }
    }
}

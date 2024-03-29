package com.net.feedingroom.ui.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecoration(private val left: Int,
                            private val top: Int,
                            private val right: Int,
                            private val bottom: Int): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) != 0){
            outRect.top = top
            outRect.left = left
            outRect.right = right
            outRect.bottom = bottom
        }
    }
}
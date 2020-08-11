package com.kakacat.minitool.common.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemDecoration(private val horizontal: Int, private val vertical: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val layoutManager = parent.layoutManager as LinearLayoutManager?
        if (layoutManager!!.orientation == LinearLayoutManager.VERTICAL) {
            outRect.top = vertical
            outRect.left = horizontal
            outRect.right = horizontal
        } else {
            if (parent.getChildAdapterPosition(view) == layoutManager.itemCount - 1) {
                outRect.right = horizontal
            }
            outRect.top = vertical
            outRect.left = horizontal
            outRect.bottom = vertical
        }
    }
}
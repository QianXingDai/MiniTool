package com.kakacat.minitool.common.ui

import android.view.MotionEvent
import android.view.View

interface RecycleViewListener{
    interface OnItemClick {
        fun onClick(v: View?, position: Int)
    }

    interface OnTouch{
        fun onTouch(v: View?, event: MotionEvent?)
    }

    interface OnItemLongClick{
        fun onLongClick(v: View?, position: Int)
    }
}


package com.kakacat.minitool.common.ui.view

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.PopupWindow

open class MyPopupWindow(context: Context, contentView: View?, width: Int, height: Int) : PopupWindow(contentView, width, height) {

    private val window: Window
    private val layoutParams: WindowManager.LayoutParams
    private var alpha = 0.6f

    init {
        val activity = context as Activity
        isOutsideTouchable = true //  设置触摸外面就取消弹窗
        isTouchable = true
        isFocusable = true
        animationStyle = android.R.style.Animation_Dialog
        window = activity.window
        layoutParams = window.attributes
        setOnDismissListener {
            layoutParams.alpha = 1.0f
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            window.attributes = layoutParams
        }
    }

    override fun showAtLocation(parent: View, gravity: Int, x: Int, y: Int) {
        layoutParams.alpha = alpha
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        window.attributes = layoutParams
        super.showAtLocation(parent, gravity, x, y)
    }

    fun setAlpha(alpha: Float) {
        this.alpha = alpha
    }
}
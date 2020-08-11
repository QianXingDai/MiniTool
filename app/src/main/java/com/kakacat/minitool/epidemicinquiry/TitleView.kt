package com.kakacat.minitool.epidemicinquiry

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.kakacat.minitool.R

class TitleView(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var tvTitle: TextView? = null
    private var tvCount: TextView? = null
    private var title: String? = null
    private var count = 0
    private var countColor = 0

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : this(context, attrs, defStyleAttr, 0) {
        init(context,attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        fun initProperties(){
            val a = context.obtainStyledAttributes(attrs, R.styleable.TitleView)
            title = a.getString(R.styleable.TitleView_title)
            count = a.getInteger(R.styleable.TitleView_count, -1)
            countColor = a.getColor(R.styleable.TitleView_count_text_color, context.getColor(android.R.color.black))
            a.recycle()
        }

        initProperties()
        LayoutInflater.from(context).inflate(R.layout.title_view_layout, this)
        tvTitle = findViewById(R.id.tv_title)
        tvCount = findViewById(R.id.tv_count)
        setTitle(title)
        setCount(count)
        setCountColor(countColor)
    }

    fun setTitle(title: String?) {
        this.title = title
        tvTitle!!.text = title
    }

    fun setCount(count: Int) {
        this.count = count
        tvCount!!.text = count.toString()
    }

    fun setCountColor(color: Int) {
        countColor = color
        tvCount!!.setTextColor(color)
    }
}
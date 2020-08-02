package com.kakacat.minitool.epidemicinquiry;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.kakacat.minitool.R;

public class TitleView extends LinearLayout {

    private Context context;

    private TextView tvTitle;
    private TextView tvCount;

    private String title;
    private int count;
    private int countColor;

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleView);
        title = a.getString(R.styleable.TitleView_title);
        count = a.getInteger(R.styleable.TitleView_count, -1);
        countColor = a.getColor(R.styleable.TitleView_count_text_color, context.getColor(android.R.color.black));
        a.recycle();

        View view = LayoutInflater.from(context).inflate(R.layout.title_view_layout, null);
        tvTitle = view.findViewById(R.id.tv_title);
        tvCount = view.findViewById(R.id.tv_count);

        setTitle(title);
        setCount(count);
        setCountColor(countColor);
    }

    public void setTitle(String title) {
        this.title = title;
        tvTitle.setText(title);
    }

    public void setCount(int count) {
        this.count = count;
        tvCount.setText(String.valueOf(count));
    }

    public void setCountColor(int color) {
        this.countColor = color;
        tvCount.setTextColor(color);
    }
}

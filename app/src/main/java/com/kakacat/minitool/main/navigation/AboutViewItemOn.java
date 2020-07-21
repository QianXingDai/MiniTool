package com.kakacat.minitool.main.navigation;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kakacat.minitool.R;
import com.kakacat.minitool.common.RecycleViewItemOnClickListener;
import com.kakacat.minitool.main.adapter.AboutItemAdapter;
import com.kakacat.minitool.main.model.AboutItem;
import com.kakacat.minitool.util.SystemUtil;
import com.kakacat.minitool.util.ui.MyPopupWindow;

import java.util.ArrayList;
import java.util.List;

public class AboutViewItemOn extends MyPopupWindow implements RecycleViewItemOnClickListener {

    private static AboutViewItemOn aboutView;
    private View contentView;
    private Context context;


    public AboutViewItemOn(Context context, View contentView, int width, int height) {
        super(context, contentView, width, height);
        this.contentView = contentView;
        this.context = context;
    }

    public static AboutViewItemOn getInstance(Activity activity, View contentView, int width, int height){
        if(aboutView == null){
            aboutView = new AboutViewItemOn(activity, contentView, width, height);
            aboutView.initView();
        }
        return aboutView;
    }


    private void initView(){
        RecyclerView recyclerView = contentView.findViewById(R.id.rv_about);
        List<AboutItem> list = new ArrayList<>();

        list.add(new AboutItem(R.drawable.ic_black_star,"给我好评"));
        list.add(new AboutItem(R.drawable.ic_version,"版本状态"));

        AboutItemAdapter aboutItemAdapter = new AboutItemAdapter(list);
        aboutItemAdapter.setOnClickListener(this);
        recyclerView.setAdapter(aboutItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void onClick(View v, int position) {
        switch (position){
            case 0:{
                SystemUtil.openMarket(context);
                break;
            }
            default:
                break;
        }
    }




}

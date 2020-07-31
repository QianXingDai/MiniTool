package com.kakacat.minitool.epidemicinquiry.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.GridLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kakacat.minitool.R;
import com.kakacat.minitool.epidemicinquiry.Contract;
import com.kakacat.minitool.epidemicinquiry.TitleView;
import com.kakacat.minitool.epidemicinquiry.adapter.DomesticAdapter;

public class MyFragment extends Fragment {

    private static final String[] titles = {"现有确诊","现有疑似"};
    private static final int[] countTextColors = {R.color.light4,R.color.light2};

    private Context context;
    private Contract.Presenter presenter;
    private DomesticAdapter adapter;
    ExpandableListView expandableListView;

    public MyFragment(Contract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myfragment_layout1,container,false);
        this.context = getContext();

        initTitleView(view);
        initExpandableListView(view);

        return view;
    }

    private void initTitleView(View contentView){
        GridLayout titleViewContainer = contentView.findViewById(R.id.title_view_container);
        for(int i = 0; i < titles.length; i++){
            TitleView titleView = new TitleView(context);
            titleView.setTitle(titles[i]);
            titleView.setCountColor(countTextColors[i]);
            titleView.setCount(0);
            titleViewContainer.addView(titleView);
        }
    }

    private void initExpandableListView(View contentView){
        expandableListView = contentView.findViewById(R.id.expand_list_view);
        adapter = new DomesticAdapter(context,presenter.getGroupList());
        expandableListView.setAdapter(adapter);
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public void updateView(){
        adapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(expandableListView);
    }
}

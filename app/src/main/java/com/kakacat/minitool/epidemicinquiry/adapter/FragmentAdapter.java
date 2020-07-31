package com.kakacat.minitool.epidemicinquiry.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.kakacat.minitool.epidemicinquiry.fragment.MyFragment;

import java.util.List;

public class FragmentAdapter extends FragmentStateAdapter {

    private List<MyFragment> myFragmentList;

    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity,List<MyFragment> myFragmentList) {
        super(fragmentActivity);
        this.myFragmentList = myFragmentList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return myFragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return myFragmentList.size();
    }
}

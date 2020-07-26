package com.kakacat.minitool.main.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.kakacat.minitool.main.MainContract;
import com.kakacat.minitool.main.fragment.DailyFragment;
import com.kakacat.minitool.main.fragment.GeekFragment;
import com.kakacat.minitool.main.fragment.MyFragment;

public class FragmentAdapter extends FragmentStateAdapter {

    private MyFragment[] myFragments;
    private MainContract.Presenter presenter;

    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity, MainContract.Presenter presenter) {
        super(fragmentActivity);
        this.presenter = presenter;
        init();
    }

    private void init(){
        myFragments = new MyFragment[2];
        myFragments[0] = new DailyFragment(presenter);
        myFragments[1] = new GeekFragment(presenter);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return myFragments[position];
    }

    @Override
    public int getItemCount() {
        return myFragments.length;
    }
}

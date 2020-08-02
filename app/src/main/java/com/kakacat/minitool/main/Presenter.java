package com.kakacat.minitool.main;

import com.kakacat.minitool.main.model.MainItem;
import com.kakacat.minitool.main.model.Model;

import java.util.List;

public class Presenter implements MainContract.Presenter {

    private MainContract.View mainView;
    private Model model;

    public Presenter(MainContract.View mainView) {
        this.mainView = mainView;
        this.model = Model.getInstance();
    }

    @Override
    public void initData() {
        model.initData();
    }

    public List<MainItem> getDailyList() {
        return model.getDailyList();
    }

    public List<MainItem> getGeekList() {
        return model.getGeekList();
    }
}

package com.kakacat.minitool.main;

import com.kakacat.minitool.main.model.MainItem;
import com.kakacat.minitool.main.model.Model;

import java.util.List;

public class Presenter implements MainContract.Presenter {

    private Model model;

    public Presenter() {
        this.model = new Model();
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

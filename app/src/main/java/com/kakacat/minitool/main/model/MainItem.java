package com.kakacat.minitool.main.model;

public class MainItem {
    private int titleId;
    private int iconId;
    private int noteId;

    public MainItem(int titleId, int iconId, int noteId) {
        this.titleId = titleId;
        this.iconId = iconId;
        this.noteId = noteId;
    }

    public int getTitleId() {
        return titleId;
    }

    public int getIconId() {
        return iconId;
    }

    public int getNoteId() {
        return noteId;
    }

}

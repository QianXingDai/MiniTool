package com.kakacat.minitool.common.ui;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.kakacat.minitool.common.util.UiUtil;

public class RecycleViewScrollListener extends RecyclerView.OnScrollListener{

    private int lastItemPosition;
    private int layoutManagerType;
    private int[] lastPositions;
    private OnSwipeUpRefresh onSwipeUpRefresh;

    public RecycleViewScrollListener(OnSwipeUpRefresh onSwipeUpRefresh) {
        lastItemPosition = 0;
        layoutManagerType = -1;
        this.onSwipeUpRefresh = onSwipeUpRefresh;
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        assert layoutManager != null;
        if(layoutManagerType == -1){
            if(layoutManager instanceof LinearLayoutManager){
                layoutManagerType = LayoutManagerType.LinearLayoutManager;
            }else if(layoutManager instanceof GridLayoutManager){
                layoutManagerType = LayoutManagerType.GridLayoutManager;
            }else if(layoutManager instanceof StaggeredGridLayoutManager){
                layoutManagerType = LayoutManagerType.StaggeredGridLayoutManager;
            }else{
                throw new RuntimeException("UnSupport LayoutManager");
            }
        }
        switch (layoutManagerType){
            case LayoutManagerType.LinearLayoutManager:{
                lastItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            }
            case LayoutManagerType.GridLayoutManager:{
                lastItemPosition = ((GridLayoutManager)layoutManager).findLastVisibleItemPosition();
                break;
            }
            case LayoutManagerType.StaggeredGridLayoutManager:{
                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager)layoutManager;
                if(lastPositions == null){
                    lastPositions = new int[manager.getSpanCount()];
                }
                manager.findLastVisibleItemPositions(lastPositions);
                for (int lastPosition : lastPositions) {
                    lastItemPosition = Math.max(lastPosition,lastItemPosition);
                }
                break;
            }
            default:
                break;
        }

        if(onSwipeUpRefresh != null){
            if(newState == RecyclerView.SCROLL_STATE_IDLE && layoutManager.getChildCount() > 0 && lastItemPosition >= layoutManager.getItemCount() -1){
                showLoading(recyclerView);
                onSwipeUpRefresh.loadMore();
            }
        }
    }

    private void showLoading(@NonNull RecyclerView recyclerView){
        UiUtil.showLoading(recyclerView.getContext(),recyclerView);
    }

    public interface OnSwipeUpRefresh{
        void loadMore();
    }

    private interface LayoutManagerType{
        int LinearLayoutManager = 0;
        int GridLayoutManager = 1;
        int StaggeredGridLayoutManager = 2;
    }
}

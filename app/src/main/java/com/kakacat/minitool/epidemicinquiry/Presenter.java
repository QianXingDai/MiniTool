package com.kakacat.minitool.epidemicinquiry;

import com.kakacat.minitool.common.constant.Result;
import com.kakacat.minitool.epidemicinquiry.model.GroupBean;
import com.kakacat.minitool.epidemicinquiry.model.Model;

import java.util.List;

public class Presenter implements Contract.Presenter {

    private Contract.View view;
    private Model model;
    private OnRequestCallback callback;

    public Presenter(Contract.View view) {
        this.view = view;
    }

    @Override
    public void initData() {
        requestData();
    }

    @Override
    public void requestData(){
        getModel().sendRequest(getOnRequestCallback());
    }

    @Override
    public List<GroupBean> getGroupList() {
        return getModel().getGroupBeanList();
    }

    private OnRequestCallback getOnRequestCallback(){
        if(callback == null){
            callback = new OnRequestCallback() {
                @Override
                public void onRequestSuccess(int result) {
                    if(result == Result.HANDLE_SUCCESS){
                        view.onUpdateViewSuccessful();
                    }else{
                        view.onUpdateViewError();
                    }
                }

                @Override
                public void onRequestError() {
                    view.onUpdateViewError();
                }
            };
        }
        return callback;
    }

    private Model getModel(){
        if(model == null){
            model = new Model();
        }
        return model;
    }

    public interface OnRequestCallback{
        void onRequestSuccess(int result);
        void onRequestError();
    }
}

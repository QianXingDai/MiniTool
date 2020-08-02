package com.kakacat.minitool.epidemicinquiry;

import com.kakacat.minitool.common.base.IPresenter;
import com.kakacat.minitool.common.base.IView;
import com.kakacat.minitool.epidemicinquiry.model.GroupBean;

import java.util.List;

public interface Contract {

    interface Presenter extends IPresenter {
        void requestData();

        List<GroupBean> getGroupList();
    }

    interface View extends IView<Presenter> {
        void onUpdateViewSuccessful();

        void onUpdateViewError(String error);
    }
}

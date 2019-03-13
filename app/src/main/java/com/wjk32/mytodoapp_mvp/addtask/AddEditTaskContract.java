package com.wjk32.mytodoapp_mvp.addtask;

import com.wjk32.mytodoapp_mvp.BasePresenter;
import com.wjk32.mytodoapp_mvp.BaseView;

/**
 * Created by Jikang Wang on 3/10/19.
 */
public interface AddEditTaskContract {

    interface View extends BaseView<Presenter> {
        void showEmptyTaskError();

        void showTasksList();

        void setTitle(String title);

        void setDescription(String description);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        
        void saveTask(String title, String description);

        void populateTask();

        boolean isDataMissing();
    }
}

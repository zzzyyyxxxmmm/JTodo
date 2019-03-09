package com.wjk32.mytodoapp_mvp.tasks;

import com.wjk32.mytodoapp_mvp.BasePresenter;
import com.wjk32.mytodoapp_mvp.BaseView;

/**
 * Created by Jikang Wang on 3/9/19.
 */
public interface TasksContract {

    interface View extends BaseView<Presenter> {
        
    }

    interface Presenter extends BasePresenter {
        
    }
    
}
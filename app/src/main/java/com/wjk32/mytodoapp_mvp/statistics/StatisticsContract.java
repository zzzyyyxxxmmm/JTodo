package com.wjk32.mytodoapp_mvp.statistics;

import com.wjk32.mytodoapp_mvp.BasePresenter;
import com.wjk32.mytodoapp_mvp.BaseView;

/**
 * Created by Jikang Wang on 3/11/19.
 */
public interface StatisticsContract {

    interface View extends BaseView<Presenter> {

        void setProgressIndicator(boolean active);

        void showStatistics(int numberOfIncompleteTasks, int numberOfCompletedTasks);

        void showLoadingStatisticsError();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

    }
}
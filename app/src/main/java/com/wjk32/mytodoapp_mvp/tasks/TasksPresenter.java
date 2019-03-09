package com.wjk32.mytodoapp_mvp.tasks;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;
/**
 * Created by Jikang Wang on 3/9/19.
 */
public class TasksPresenter implements TasksContract.Presenter {

    private final TasksContract.View mTasksView;

    public TasksPresenter(@NonNull TasksContract.View tasksView) {
        mTasksView = checkNotNull(tasksView, "tasksView cannot be null!");
        mTasksView.setPresenter(this);
    }
    
    @Override
    public void start() {
        
    }
}

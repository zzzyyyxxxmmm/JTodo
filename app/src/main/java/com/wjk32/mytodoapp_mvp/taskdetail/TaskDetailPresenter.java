package com.wjk32.mytodoapp_mvp.taskdetail;

import com.google.common.base.Strings;
import com.wjk32.mytodoapp_mvp.data.Task;
import com.wjk32.mytodoapp_mvp.data.source.TasksDataSource;
import com.wjk32.mytodoapp_mvp.data.source.TasksRepository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Jikang Wang on 3/11/19.
 */
public class TaskDetailPresenter implements TaskDetailContract.Presenter {

    private final TasksRepository mTasksRepository;

    private final TaskDetailContract.View mTaskDetailView;

    @Nullable
    private String mTaskId;

    public TaskDetailPresenter(@Nullable String taskId,
                               @NonNull TasksRepository tasksRepository,
                               @NonNull TaskDetailContract.View taskDetailView) {
        mTaskId = taskId;
        mTasksRepository = checkNotNull(tasksRepository, "tasksRepository cannot be null!");
        mTaskDetailView = checkNotNull(taskDetailView, "taskDetailView cannot be null!");

        mTaskDetailView.setPresenter(this);
    }

    @Override
    public void start() {
        openTask();
    }

    private void openTask() {
        if (Strings.isNullOrEmpty(mTaskId)) {
            mTaskDetailView.showMissingTask();
            return;
        }

        mTaskDetailView.setLoadingIndicator(true);
        mTasksRepository.getTask(mTaskId, new TasksDataSource.GetTaskCallback() {
            @Override
            public void onTaskLoaded(Task task) {
                // The view may not be able to handle UI updates anymore
                if (!mTaskDetailView.isActive()) {
                    return;
                }
                mTaskDetailView.setLoadingIndicator(false);
                if (null == task) {
                    mTaskDetailView.showMissingTask();
                } else {
                    showTask(task);
                }
            }

            @Override
            public void onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                if (!mTaskDetailView.isActive()) {
                    return;
                }
                mTaskDetailView.showMissingTask();
            }
        });
    }

    @Override
    public void editTask() {
        if (Strings.isNullOrEmpty(mTaskId)) {
            mTaskDetailView.showMissingTask();
            return;
        }
        mTaskDetailView.showEditTask(mTaskId);
    }

    @Override
    public void deleteTask() {
        if (Strings.isNullOrEmpty(mTaskId)) {
            mTaskDetailView.showMissingTask();
            return;
        }
        mTasksRepository.deleteTask(mTaskId);
        mTaskDetailView.showTaskDeleted();
    }

    @Override
    public void completeTask() {
        if (Strings.isNullOrEmpty(mTaskId)) {
            mTaskDetailView.showMissingTask();
            return;
        }
        mTasksRepository.completeTask(mTaskId);
        mTaskDetailView.showTaskMarkedComplete();
    }

    @Override
    public void activateTask() {
        if (Strings.isNullOrEmpty(mTaskId)) {
            mTaskDetailView.showMissingTask();
            return;
        }
        mTasksRepository.activateTask(mTaskId);
        mTaskDetailView.showTaskMarkedActive();
    }

    private void showTask(@NonNull Task task) {
        String title = task.getTitle();
        String description = task.getDescription();

        if (Strings.isNullOrEmpty(title)) {
            mTaskDetailView.hideTitle();
        } else {
            mTaskDetailView.showTitle(title);
        }

        if (Strings.isNullOrEmpty(description)) {
            mTaskDetailView.hideDescription();
        } else {
            mTaskDetailView.showDescription(description);
        }
        mTaskDetailView.showCompletionStatus(task.isCompleted());
    }
}
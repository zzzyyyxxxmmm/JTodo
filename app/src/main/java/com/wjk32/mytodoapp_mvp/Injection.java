package com.wjk32.mytodoapp_mvp;

import android.content.Context;

import com.wjk32.mytodoapp_mvp.data.source.TasksRepository;
import com.wjk32.mytodoapp_mvp.data.source.local.TasksLocalDataSource;
import com.wjk32.mytodoapp_mvp.data.source.local.ToDoDatabase;
import com.wjk32.mytodoapp_mvp.data.source.remote.TasksRemoteDataSource;
import com.wjk32.mytodoapp_mvp.util.AppExecutors;

import androidx.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Jikang Wang on 3/10/19.
 */
public class Injection {

    public static TasksRepository provideTasksRepository(@NonNull Context context) {
        checkNotNull(context);
        ToDoDatabase database = ToDoDatabase.getInstance(context);
        return TasksRepository.getInstance(TasksRemoteDataSource.getInstance(),
                TasksLocalDataSource.getInstance(new AppExecutors(),
                        database.taskDao()));
    }
}

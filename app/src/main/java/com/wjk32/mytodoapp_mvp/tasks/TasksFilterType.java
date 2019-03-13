package com.wjk32.mytodoapp_mvp.tasks;

/**
 * Created by Jikang Wang on 3/11/19.
 */
public enum TasksFilterType {
    /**
     * Do not filter tasks.
     */
    ALL_TASKS,

    /**
     * Filters only the active (not completed yet) tasks.
     */
    ACTIVE_TASKS,

    /**
     * Filters only the completed tasks.
     */
    COMPLETED_TASKS
}

package com.wjk32.mytodoapp_mvp.addedittask;

import com.wjk32.mytodoapp_mvp.addtask.AddEditTaskContract;
import com.wjk32.mytodoapp_mvp.addtask.AddEditTaskPresenter;
import com.wjk32.mytodoapp_mvp.data.Task;
import com.wjk32.mytodoapp_mvp.data.source.TasksDataSource;
import com.wjk32.mytodoapp_mvp.data.source.TasksRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Jikang Wang on 3/11/19.
 */
public class AddEditTaskPresenterTest {
    @Mock
    private TasksRepository mTasksRepository;

    @Mock
    private AddEditTaskContract.View mAddEditTaskView;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<TasksDataSource.GetTaskCallback> mGetTaskCallbackCaptor;

    private AddEditTaskPresenter mAddEditTaskPresenter;

    @Before
    public void setupMocksAndView() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // The presenter wont't update the view unless it's active.
        when(mAddEditTaskView.isActive()).thenReturn(true);
    }

    @Test
    public void createPresenter_setsThePresenterToView(){
        // Get a reference to the class under test
        mAddEditTaskPresenter = new AddEditTaskPresenter(
                null, mTasksRepository, mAddEditTaskView, true);

        // Then the presenter is set to the view
        verify(mAddEditTaskView).setPresenter(mAddEditTaskPresenter);
    }

    @Test
    public void saveNewTaskToRepository_showsSuccessMessageUi() {
        // Get a reference to the class under test
        mAddEditTaskPresenter = new AddEditTaskPresenter(
                null, mTasksRepository, mAddEditTaskView, true);

        // When the presenter is asked to save a task
        mAddEditTaskPresenter.saveTask("New Task Title", "Some Task Description");

        // Then a task is saved in the repository and the view updated
        verify(mTasksRepository).saveTask(any(Task.class)); // saved to the model
        verify(mAddEditTaskView).showTasksList(); // shown in the UI
    }

    @Test
    public void saveTask_emptyTaskShowsErrorUi() {
        // Get a reference to the class under test
        mAddEditTaskPresenter = new AddEditTaskPresenter(
                null, mTasksRepository, mAddEditTaskView, true);

        // When the presenter is asked to save an empty task
        mAddEditTaskPresenter.saveTask("", "");

        // Then an empty not error is shown in the UI
        verify(mAddEditTaskView).showEmptyTaskError();
    }

    @Test
    public void saveExistingTaskToRepository_showsSuccessMessageUi() {
        // Get a reference to the class under test
        mAddEditTaskPresenter = new AddEditTaskPresenter(
                "1", mTasksRepository, mAddEditTaskView, true);

        // When the presenter is asked to save an existing task
        mAddEditTaskPresenter.saveTask("Existing Task Title", "Some Task Description");

        // Then a task is saved in the repository and the view updated
        verify(mTasksRepository).saveTask(any(Task.class)); // saved to the model
        verify(mAddEditTaskView).showTasksList(); // shown in the UI
    }

    @Test
    public void populateTask_callsRepoAndUpdatesView() {
        Task testTask = new Task("TITLE", "DESCRIPTION");
        // Get a reference to the class under test
        mAddEditTaskPresenter = new AddEditTaskPresenter(testTask.getId(),
                mTasksRepository, mAddEditTaskView, true);

        // When the presenter is asked to populate an existing task
        mAddEditTaskPresenter.populateTask();

        // Then the task repository is queried and the view updated
        verify(mTasksRepository).getTask(eq(testTask.getId()), mGetTaskCallbackCaptor.capture());
        assertThat(mAddEditTaskPresenter.isDataMissing(), is(true));

        // Simulate callback
        mGetTaskCallbackCaptor.getValue().onTaskLoaded(testTask);

        verify(mAddEditTaskView).setTitle(testTask.getTitle());
        verify(mAddEditTaskView).setDescription(testTask.getDescription());
        assertThat(mAddEditTaskPresenter.isDataMissing(), is(false));
    }
}

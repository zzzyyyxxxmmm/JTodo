package com.wjk32.mytodoapp_mvp.statistics;
import android.content.Intent;

import com.wjk32.mytodoapp_mvp.R;
import com.wjk32.mytodoapp_mvp.data.FakeTasksRemoteDataSource;
import com.wjk32.mytodoapp_mvp.data.Task;
import com.wjk32.mytodoapp_mvp.data.source.TasksRepository;
import com.wjk32.mytodoapp_mvp.util.EspressoIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
/**
 * Created by Jikang Wang on 3/11/19.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class StatisticsScreenTest {

    /**
     * {@link ActivityTestRule} is a JUnit {@link Rule @Rule} to launch your activity under test.
     *
     * <p>
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @Rule
    public ActivityTestRule<StatisticsActivity> mStatisticsActivityTestRule =
            new ActivityTestRule<>(StatisticsActivity.class, true, false);

    /**
     * Setup your test fixture with a fake task id. The {@link TaskDetailActivity} is started with
     * a particular task id, which is then loaded from the service API.
     *
     * <p>
     * Note that this test runs hermetically and is fully isolated using a fake implementation of
     * the service API. This is a great way to make your tests more reliable and faster at the same
     * time, since they are isolated from any outside dependencies.
     */
    @Before
    public void intentWithStubbedTaskId() {
        // Given some tasks
        TasksRepository.destroyInstance();
        FakeTasksRemoteDataSource.getInstance().addTasks(new Task("Title1", "", false));
        FakeTasksRemoteDataSource.getInstance().addTasks(new Task("Title2", "", true));

        // Lazily start the Activity from the ActivityTestRule
        Intent startIntent = new Intent();
        mStatisticsActivityTestRule.launchActivity(startIntent);
    }

    /**
     * Prepare your test fixture for this test. In this case we register an IdlingResources with
     * Espresso. IdlingResource resource is a great way to tell Espresso when your app is in an
     * idle state. This helps Espresso to synchronize your test actions, which makes tests
     * significantly more reliable.
     */
    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }

    @Test
    public void Tasks_ShowsNonEmptyMessage() {
        // Check that the active and completed tasks text is displayed
        String expectedActiveTaskText = ApplicationProvider.getApplicationContext()
                .getString(R.string.statistics_active_tasks);
        onView(withText(containsString(expectedActiveTaskText))).check(matches(isDisplayed()));
        String expectedCompletedTaskText = ApplicationProvider.getApplicationContext()
                .getString(R.string.statistics_completed_tasks);
        onView(withText(containsString(expectedCompletedTaskText))).check(matches(isDisplayed()));
    }
}

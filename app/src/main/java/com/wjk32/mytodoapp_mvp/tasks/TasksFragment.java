package com.wjk32.mytodoapp_mvp.tasks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wjk32.mytodoapp_mvp.R;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Jikang Wang on 3/9/19.
 */
public class TasksFragment extends Fragment implements TasksContract.View {
    
    private TasksContract.Presenter mPresenter;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    public static TasksFragment newInstance() {
        return new TasksFragment();
    }

    @Override
    public void setPresenter(TasksContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tasks_frag, container, false);

        setHasOptionsMenu(true);

        return root;
    }
    
}

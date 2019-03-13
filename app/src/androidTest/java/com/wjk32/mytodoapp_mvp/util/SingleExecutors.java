package com.wjk32.mytodoapp_mvp.util;

import java.util.concurrent.Executor;

import androidx.annotation.NonNull;


/**
 * Created by Jikang Wang on 3/11/19.
 */
public class SingleExecutors extends AppExecutors {
    private static Executor instant = new Executor() {
        @Override
        public void execute(@NonNull Runnable command) {
            command.run();
        }
    };

    public SingleExecutors() {
        super(instant, instant, instant);
    }
}

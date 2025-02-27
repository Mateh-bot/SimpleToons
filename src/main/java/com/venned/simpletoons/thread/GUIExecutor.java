package com.venned.simpletoons.thread;

import java.util.concurrent.Executor;

public class GUIExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        command.run();
    }
}

package fi.qvik.job.util;


import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Tommy on 23/02/16.
 */
public class StaticUtils {

    private static final int THREAD_COUNT = 5;

    private static final Handler uiHandler = new Handler(Looper.getMainLooper());
    private static final ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(THREAD_COUNT);

    public static void runOnUiThread(Runnable r) {
        uiHandler.post(r);
    }

    public static void postWork(Runnable r) {
        threadPool.execute(r);
    }

}

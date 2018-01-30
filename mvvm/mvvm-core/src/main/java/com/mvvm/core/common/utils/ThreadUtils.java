package com.mvvm.core.common.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;


/**
 * Utility Class to work with threads
 */
public class ThreadUtils {

    private static final String TAG = ThreadUtils.class.getSimpleName();

    private static final LazyLoadedValue<Handler> handler = new LazyLoadedValue<Handler>() {
        @Override
        public Handler load() {
            return new Handler(Looper.getMainLooper());
        }
    };

    /**
     * Calls {@link Thread#sleep(long)} and catches the {@link InterruptedException}
     *
     * @param millis is the number of milliseconds to sleep
     */
    public static void sleepSilently(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Log.e(TAG,"Thread sleep interrupted");
        }
    }


    /**
     * Runs code on the Main Thread
     *
     * @param runnable the code to run on the main thread
     */
    public static void runOnUiThread(Runnable runnable) {
        handler.get().post(runnable);
    }


    /**
     * Runs code on the Main Thread in the future
     *
     * @param runnable    the code to run on the main thread
     * @param delayMillis delay in milliseconds
     */
    public static void runOnUiThread(Runnable runnable, long delayMillis) {
        handler.get().postDelayed(runnable, delayMillis);
    }

    public static void cancelRunnables() {
        handler.get().removeCallbacksAndMessages(null);
    }

    
}

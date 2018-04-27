package com.demo.wallet.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadUtil {

    public static ExecutorService newBoundedFixedThreadPool(int coreThreads, int maxThreads) {
        return new ThreadPoolExecutor(coreThreads, maxThreads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(Integer.MAX_VALUE),
                new ThreadPoolExecutor.DiscardPolicy());
    }

}

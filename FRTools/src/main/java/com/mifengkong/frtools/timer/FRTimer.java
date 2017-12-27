package com.mifengkong.frtools.timer;


import com.mifengkong.frtools.util.FRLog;
import com.mifengkong.frtools.handler.FRMainLooperHandler;
import com.mifengkong.frtools.worker.FRRepeatingWork;
import com.mifengkong.frtools.worker.FRWorker;

/**
 * 定时器，只能在主线程使用，timer任务执行也是在主线程
 */
public class FRTimer extends FRWorker {

    private static final class Work extends FRRepeatingWork {

        private final class TimerRunnable implements Runnable {
            @Override
            public void run() {
                FRLog.debug();
                runTask();
                if (mRepeatCount > 0) {
                    if (!FRMainLooperHandler.getInstance().postDelayed(mTimerRunnable, mIntervalMillis)) {
                        FRLog.error("main looper postDelayed error");
                    }
                }
            }
        }

        private TimerRunnable mTimerRunnable;

        public Work(Runnable task, long delayMillis, long intervalMillis, int repeatCount) {
            super(task, delayMillis, intervalMillis, repeatCount);
        }

        @Override
        public boolean startWork() {
            mTimerRunnable = new TimerRunnable();
            return FRMainLooperHandler.getInstance().postDelayed(mTimerRunnable, mDelayMillis);
        }

        @Override
        public void stopWork() {
            mRepeatCount = 0;
            if (mTimerRunnable != null) {
                FRMainLooperHandler.getInstance().removeCallbacks(mTimerRunnable);
                mTimerRunnable = null;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public FRTimer() {
    }

    public void schedule(long delayMillis, long intervalMillis, int repeatCount, Runnable task) {
        if (intervalMillis <= 0 || repeatCount <= 0 || task == null) {
            FRLog.error("param error");
            return;
        }
        start(new Work(task, delayMillis, intervalMillis, repeatCount));
    }

    public void schedule(long delayMillis, long intervalMillis, Runnable task) {
        schedule(delayMillis, intervalMillis, Integer.MAX_VALUE, task);
    }
}

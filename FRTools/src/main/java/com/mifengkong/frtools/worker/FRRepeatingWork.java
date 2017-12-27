package com.mifengkong.frtools.worker;


import com.mifengkong.frtools.util.FRLog;

/**
 *
 */
public abstract class FRRepeatingWork implements FRWorker.Work {

    private final Runnable mTask;
    protected final long mDelayMillis;
    protected final long mIntervalMillis;
    protected int mRepeatCount;

    protected FRRepeatingWork(Runnable task, long delayMillis, long intervalMillis, int repeatCount) {
        mTask = task;
        mDelayMillis = delayMillis;
        mIntervalMillis = intervalMillis;
        mRepeatCount = repeatCount;
    }

    @Override
    public boolean isWorkRunning() {
        return mRepeatCount > 0;
    }

    protected void runTask() {
        if (mRepeatCount <= 0) {
            FRLog.debug(getClass().getSimpleName() + " repeat count <= 0");
            return;
        }

        Runnable runnable = mTask;
        if (runnable == null) {
            FRLog.error(getClass().getSimpleName() + " task is null");
            mRepeatCount = 0;
            return;
        }

        try {
            runnable.run();
        } catch (Exception e) {
            FRLog.error(getClass().getSimpleName() + "run error", e);
        }

        if (mRepeatCount != Integer.MAX_VALUE) {
            mRepeatCount--;
        }
    }
}

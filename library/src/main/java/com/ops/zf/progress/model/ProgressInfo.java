package com.ops.zf.progress.model;

/**
 * Created by zfang on 2016/5/30.
 */
public class ProgressInfo {

    private double max;
    private double min;
    private double currentProgress;
    private int progressOrientation = ProgressOrientation.ORIENTATION_NONE;
    private int progressType = ProgressType.TYPE_NONE;
    private long timeTotal = 0;

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(double currentProgress) {
        this.currentProgress = currentProgress;
    }

    public int getProgressOrientation() {
        return progressOrientation;
    }

    public void setProgressOrientation(int progressOrientation) {
        this.progressOrientation = progressOrientation;
    }

    public int getProgressType() {
        return progressType;
    }

    public void setProgressType(int progressType) {
        this.progressType = progressType;
    }

    public long getTimeTotal() {
        return timeTotal;
    }

    public void setTimeTotal(long timeTotal) {
        this.timeTotal = timeTotal;
    }
}

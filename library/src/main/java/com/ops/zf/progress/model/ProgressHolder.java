package com.ops.zf.progress.model;

/**
 * Created by zfang on 2016/5/30.
 */
public class ProgressHolder {
    private double progress;

    public ProgressHolder(double progress) {
        this.progress = progress;
    }
    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }
}

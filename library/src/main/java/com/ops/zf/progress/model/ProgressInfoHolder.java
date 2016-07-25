package com.ops.zf.progress.model;

/**
 * Created by zfang on 2016/5/30.
 */
public class ProgressInfoHolder {
    private ProgressHolder holder;
    public ProgressInfoHolder(ProgressHolder holder) {
        this.holder = holder;
    }

    public void setProgress(ProgressHolder holder) {
        this.holder.setProgress(holder.getProgress());
    }

    public ProgressHolder getProgress() {
        return new ProgressHolder(holder.getProgress());
    }
}

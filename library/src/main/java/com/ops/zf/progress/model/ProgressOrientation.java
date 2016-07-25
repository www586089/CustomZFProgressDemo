package com.ops.zf.progress.model;

/**
 * Created by zfang on 2016/5/30.
 */
public class ProgressOrientation {

    public static final int ORIENTATION_NONE = -1;
    public static final int ORIENTATION_LEFT2RIGHT = ORIENTATION_NONE + 1;
    public static final int ORIENTATION_RIGHT2LEFT = ORIENTATION_LEFT2RIGHT + 1;
    public static final int ORIENTATION_TOP2BOTTOM = ORIENTATION_RIGHT2LEFT + 1;
    public static final int ORIENTATION_BOTTOM2TOP = ORIENTATION_TOP2BOTTOM + 1;
}

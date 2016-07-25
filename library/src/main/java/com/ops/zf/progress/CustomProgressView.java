package com.ops.zf.progress;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.ops.zf.progress.model.ProgressHolder;
import com.ops.zf.progress.model.ProgressInfo;
import com.ops.zf.progress.model.ProgressInfoHolder;
import com.ops.zf.progress.model.ProgressOrientation;
import com.ops.zf.progress.model.ProgressType;

/**
 * created by zfang 2016-07-25
 */
public class CustomProgressView extends View {

    private String TAG = CustomProgressView.class.getSimpleName();
    private boolean isDebug = false;
    private int width = 0;
    private int height = 0;

    private Paint progressBGPaint = null;
    private Paint progressPaint = null;
    private Paint textBGPaint = null;
    private TextPaint textPaint = null;
    private Resources mResources = null;
    private Context context = null;


    private ProgressInfoHolder progressInfoHolder = null;
    private ProgressInfo progressInfo = null;
    private ProgressHolder progressHolder = null;
    private ValueAnimator animator = null;

    private float m1Dip;
    private float m1Sp;

    private float prevEndX = -1;
    private float textPadding = -1;

    public CustomProgressView(Context context) {
        super(context);
        init(context);
    }

    public CustomProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CustomProgressView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        m1Dip = getResources().getDisplayMetrics().density;
        m1Sp = getResources().getDisplayMetrics().scaledDensity;
        textPadding = dips(5);

        mResources = context.getResources();
        progressPaint = new Paint();
        progressPaint.setStyle(Paint.Style.FILL);
        progressPaint.setColor(mResources.getColor(android.R.color.holo_red_dark));
        progressPaint.setStrokeWidth(dips(2));

        progressBGPaint = new Paint();
        progressBGPaint.setStyle(Paint.Style.FILL);
        progressBGPaint.setColor(mResources.getColor(android.R.color.holo_red_dark));
        progressBGPaint.setStrokeWidth(dips(2));

        textBGPaint = new Paint();
        textBGPaint.setStyle(Paint.Style.FILL);
        textBGPaint.setColor(mResources.getColor(android.R.color.holo_red_dark));
        textBGPaint.setStrokeWidth(dips(2));

        textPaint = new TextPaint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(mResources.getColor(android.R.color.holo_blue_dark));
        textPaint.setTextSize(sp(10));


        //if (isDebug) {
        progressInfo = new ProgressInfo();
        progressInfo.setMin(0);
        progressInfo.setMax(60);
        progressInfo.setProgressType(ProgressType.TYPE_HORIZONTAL);
        progressInfo.setProgressOrientation(ProgressOrientation.ORIENTATION_RIGHT2LEFT);
        //}
    }

    public ProgressInfo getProgressInfo() {
        return progressInfo;
    }

    public void setProgressInfo(ProgressInfo progressInfo) {
        this.progressInfo = progressInfo;
    }

    public void progressStart() {
        //根据进度条类型设置画笔
        int color = context.getResources().getColor(R.color.orange_new);
        if (ProgressOrientation.ORIENTATION_LEFT2RIGHT == progressInfo.getProgressOrientation()) {
            progressPaint.setColor(color);
        } else if (ProgressOrientation.ORIENTATION_RIGHT2LEFT == progressInfo.getProgressOrientation()) {
            progressPaint.setColor(mResources.getColor(R.color.map_overlay_bg_color));
            progressBGPaint.setColor(color);
        }
        textBGPaint.setColor(color);
        textPaint.setColor(Color.WHITE);
        progressHolder = new ProgressHolder(progressInfo.getMin());
        ProgressHolder end = new ProgressHolder(progressInfo.getMax());

        progressInfoHolder = new ProgressInfoHolder(progressHolder);
        animator = ObjectAnimator.ofObject(progressInfoHolder, "progress", new ProgressEvaluator(), end);
        animator.setDuration(progressInfo.getTimeTotal());
        animator.setInterpolator(new LinearInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                reset();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            animator.addPauseListener(new Animator.AnimatorPauseListener() {
                @Override
                public void onAnimationPause(Animator animation) {

                }

                @Override
                public void onAnimationResume(Animator animation) {

                }
            });
        }

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
        animator.start();
    }

    private void reset() {
        prevEndX = 0;
    }

    public void progressStop() {
        animator.end();
    }

    public void progressPause() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            animator.pause();
        } else {
            animator.end();
        }
    }

    public void progressContinue() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            animator.resume();
        } else {
            animator.cancel();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();
        if (isDebug) {
            Log.e(TAG, "width = " + width + ", height = " + height);
            canvas.drawColor(mResources.getColor(android.R.color.darker_gray));
        }

        if (null != progressHolder) {
            if (ProgressOrientation.ORIENTATION_LEFT2RIGHT == progressInfo.getProgressOrientation()) {
                drawProgressLeft2Right(canvas);
            } else if (ProgressOrientation.ORIENTATION_RIGHT2LEFT == progressInfo.getProgressOrientation()) {
                drawProgressRight2Left(canvas);
            } else {
                /*double progress = progressHolder.getProgress();
                canvas.drawLine(0.0f, 0.0f, prevEndX, 0.0f, progressPaint);

                String progressStr = ((int) progress) + "";
                Rect textBounds = new Rect();
                textPaint.getTextBounds(progressStr, 0, progressStr.length(), textBounds);
                canvas.drawText(progressStr, prevEndX, progressPaint.getStrokeWidth() + textBounds.height(), textPaint);*/
            }
        }
    }

    private void drawProgressLeft2Right(Canvas canvas) {
        double progress = progressHolder.getProgress();
        float endX = (float) (progress / progressInfo.getMax() * width);
        if (isDebug) {
            Log.e(TAG, "progress = " + progress + ", endX = " + endX);
        }

        canvas.drawLine(0.0f, 0.0f, endX, 0.0f, progressPaint);

        String progressStr = ((int) progress) + "s";
        Rect textBounds = new Rect();
        textPaint.getTextBounds(progressStr, 0, progressStr.length(), textBounds);
        if (endX >= width) {
            endX = width - textBounds.width();
        } else if (endX >= textBounds.width()) {
            endX = endX - textBounds.width();
        } else {
            endX = textPadding;
        }
        if (endX > prevEndX) {
            prevEndX = endX;
        } else {
            endX = prevEndX;
        }

        if ((width > endX) && (width - (endX + textBounds.width())) < textPadding) {
            endX = width - textPadding - textBounds.width();
        }
        canvas.drawText(progressStr, endX, progressPaint.getStrokeWidth() + textBounds.height(), textPaint);
    }

    private void drawProgressRight2Left(Canvas canvas) {
        double progress = progressHolder.getProgress();

        float endX = (float) (progress / progressInfo.getMax() * width);
        if (isDebug) {
            Log.e(TAG, "progress = " + progress + ", endX = " + endX);
        }
        canvas.drawLine(width, 0.0f, width - endX, 0.0f, progressPaint);
        canvas.drawLine(0, 0, width - endX, 0, progressBGPaint);

        String progressStr = ((int) (progressInfo.getMax() - ((int) progress))) + "s";
        Rect textBounds = new Rect();
        textPaint.getTextBounds(progressStr, 0, progressStr.length(), textBounds);

        int textRectLeft = (int) (width - endX - textBounds.width() - 2 * textPadding);
        /*if (endX < textBounds.width()) {
            endX = textBounds.width();
        } else */if (textRectLeft < 0) {
            endX = width - textBounds.width() - 2 * textPadding;
        }

        if (endX > prevEndX) {
            prevEndX = endX;
        } else {
            endX = prevEndX;
        }


        /*if (endX >= width) {
            endX = width - textBounds.width();
        } else if (endX >= textBounds.width()) {
            endX = endX - textBounds.width();
        } else {
            endX = textPadding;
        }
        if (endX > prevEndX) {
            prevEndX = endX;
        } else {
            endX = prevEndX;
        }*/

        /*if ((width > endX) && (width - (endX + textBounds.width())) < textPadding) {
            endX = width - textPadding - textBounds.width();
        }*/
        //canvas.drawText(progressStr, width - endX - textBounds.width(), progressPaint.getStrokeWidth() + textBounds.height(), textPaint);


        Rect textRect = new Rect();
        textRect.set((int) (width - endX - textBounds.width() - 2 * textPadding),
                0,
                (int) Math.ceil((width - endX - textBounds.width() + textBounds.width())),
                (int) (progressPaint.getStrokeWidth() + textBounds.height() + progressPaint.getStrokeWidth()/* + dips(1)*/));
        canvas.drawRect(textRect, textBGPaint);
        canvas.drawText(progressStr, width - endX - textBounds.width() - textPadding, progressPaint.getStrokeWidth() / 2 + textBounds.height(), textPaint);
    }

    private float dips(final float dips) {
        return dips * m1Dip;
    }

    private float sp(final int sp) {
        return sp * m1Sp;
    }

    public class ProgressEvaluator implements TypeEvaluator {
        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            ProgressHolder startProgressInfo = (ProgressHolder) startValue;
            ProgressHolder endProgressInfo = (ProgressHolder) endValue;
            return new ProgressHolder(startProgressInfo.getProgress() + fraction * (endProgressInfo.getProgress() - startProgressInfo.getProgress()));
        }
    }
}

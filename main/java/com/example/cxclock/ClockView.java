package com.example.cxclock;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Calendar;

/**
 * Created by miji on 16. 1. 12.
 */
public class ClockView extends SurfaceView
    implements SurfaceHolder.Callback, Runnable {

    /**
     *
     */
    private Handler mHandler;

    /**
     *
     */
    private SurfaceHolder mHolder;

    /**
     *
     */
    private boolean mFlagStop;

    /**
     *
     */
    private int mWidth, mHeight;

    /**
     *
     */
    private float mDialRadius;

    /**
     *
     */
    private Paint mPaint, mPaintClear;




    public ClockView(Context context) {
        super(context);

        init(context, null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ClockView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context, attrs);
    }


    /**
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        mHandler = new Handler();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GRAY);

        mPaintClear = new Paint();
        mPaintClear.setColor(Color.BLACK);

        mHolder = getHolder();
        mHolder.addCallback(this);
    }


    /**
     *
     */
    public void start() {
        mFlagStop = false;
        mHandler.post(this);
    }


    /**
     *
     */
    public void stop() {
        mFlagStop = true;
    }


    /**
     *
     * @return
     */
    public boolean isStop() {
        return mFlagStop;
    }



    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        mWidth = width;
        mHeight = height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        stop();
    }

    @Override
    public void run() {

        if(mFlagStop) {
            return;
        }

        Calendar cal = Calendar.getInstance();
        int sec = cal.get(Calendar.SECOND);
        int min = cal.get(Calendar.MINUTE);
        int hour = cal.get(Calendar.HOUR);

        float degSec = (6*sec);
        float degMin = (6*min);
        float degHour = 0.5f * ((60*hour) + min);


        synchronized (mHolder)
        {{{
            Canvas canvas = mHolder.lockCanvas();

            int cx = mWidth / 2;
            int cy = mHeight / 2;

            drawDial(cx, cy, canvas);
            drawHandHour(cx, cy, degHour, canvas);
            drawHandMinute(cx, cy, degMin, canvas);
            drawHandSecond(cx, cy, degSec, canvas);

            mHolder.unlockCanvasAndPost(canvas);
        }}}


        if(mFlagStop != true) {
            mHandler.postDelayed(this, 1000);
        }
    }


    /**
     *
     * @param cx
     * @param cy
     * @param canvas
     */
    private void drawDial(int cx, int cy, Canvas canvas) {

        final float stroke = 7f;

        mDialRadius = Math.min(cx, cy) - stroke;

        canvas.drawRect(0, 0, mWidth, mHeight, mPaintClear);

        mPaint.setStrokeWidth(stroke);
        canvas.drawCircle(cx, cy, mDialRadius, mPaint);
    }

    /**
     *
     * @param cx
     * @param cy
     * @param deg
     * @param canvas
     */
    private void drawHandHour(int cx, int cy, float deg, Canvas canvas) {

        final float stroke = 5f;
        mPaint.setStrokeWidth(stroke);

        float length = mDialRadius * 0.6f;
        float ex = length * 0.12f;

        canvas.save();

        canvas.rotate(deg, cx, cy);
        canvas.drawLine(cx, cy+ex, cx, cy-length, mPaint);

        canvas.restore();
    }

    /**
     *
     * @param cx
     * @param cy
     * @param deg
     * @param canvas
     */
    private void drawHandMinute(int cx, int cy, float deg, Canvas canvas) {

        final float stroke = 4f;
        mPaint.setStrokeWidth(stroke);

        float length = mDialRadius * 0.85f;
        float ex = length * 0.12f;

        canvas.save();

        canvas.rotate(deg, cx, cy);
        canvas.drawLine(cx, cy+ex, cx, cy-length, mPaint);

        canvas.restore();
    }

    /**
     *
     * @param cx
     * @param cy
     * @param deg
     * @param canvas
     */
    private void drawHandSecond(int cx, int cy, float deg, Canvas canvas) {

        final float stroke = 3f;
        mPaint.setStrokeWidth(stroke);

        float length = mDialRadius * 0.92f;
        float ex = length * 0.2f;

        canvas.save();

        canvas.rotate(deg, cx, cy);
        canvas.drawLine(cx, cy+ex, cx, cy-length, mPaint);

        canvas.restore();
    }
}

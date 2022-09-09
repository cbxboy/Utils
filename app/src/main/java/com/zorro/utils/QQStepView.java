package com.zorro.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @Author : cbx
 * @Email : 673591077@qq.com
 * @Date : on 2022-09-06 16:25.
 * @Description :仿QQ运动步数
 */
public class QQStepView extends View {

    private int mOuterColor = Color.RED;
    private int mInnerColor = Color.BLUE;
    private int mBoardWidth = 20;
    private int mStepTextSize = 15;
    private int mStepTextColor = Color.RED;

    private Paint mOuterPaint, mInnerPaint, mStepTextPaint;
    private int mStepMax = 100;
    private int mCurrentStep = 50;

    public QQStepView(Context context) {
        this(context, null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
        mOuterColor = typedArray.getColor(R.styleable.QQStepView_outerColor, mOuterColor);
        mInnerColor = typedArray.getColor(R.styleable.QQStepView_innerColor, mInnerColor);
        mBoardWidth = typedArray.getDimensionPixelSize(R.styleable.QQStepView_boardWidth, mBoardWidth);
        mStepTextSize = typedArray.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize, mStepTextSize);
        mStepTextColor = typedArray.getColor(R.styleable.QQStepView_stepTextColor, mStepTextColor);
        typedArray.recycle();

        mOuterPaint = new Paint();
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setColor(mOuterColor);
        mOuterPaint.setStrokeWidth(mBoardWidth);
        mOuterPaint.setStrokeCap(Paint.Cap.ROUND);
        mOuterPaint.setStyle(Paint.Style.STROKE);

        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(mInnerColor);
        mInnerPaint.setStrokeWidth(mBoardWidth);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        mInnerPaint.setStyle(Paint.Style.STROKE);

        mStepTextPaint = new Paint();
        mStepTextPaint.setAntiAlias(true);
        mStepTextPaint.setTextSize(mStepTextSize);
        mStepTextPaint.setColor(mStepTextColor);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);


        setMeasuredDimension(width > height ? height : width, width > height ? height : width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //canvas.clipRect();
        RectF rectF = new RectF(mBoardWidth / 2, mBoardWidth / 2, getWidth() - mBoardWidth / 2, getHeight() - mBoardWidth / 2);
        //画最外圈圆弧
        canvas.drawArc(rectF, 135, 270, false, mOuterPaint);
        //画内圈圆弧
        float sweepAngle = (float) mCurrentStep / mStepMax;
        canvas.drawArc(rectF, 135, sweepAngle * 270, false, mInnerPaint);
        //画步数
        String stepText = mCurrentStep + "";
        Rect textBounds = new Rect();
        mStepTextPaint.getTextBounds(stepText, 0, stepText.length(), textBounds);
        int dx = getWidth() / 2 - textBounds.width() / 2;

        Paint.FontMetrics fontMetrics = mStepTextPaint.getFontMetrics();
        int dy = (int) ((fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent);
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(stepText, dx, baseLine, mStepTextPaint);

    }


    public synchronized void setStepMax(int stepMax) {
        this.mStepMax = stepMax;
    }

    public synchronized void setCurrentStep(int currentStep) {
        this.mCurrentStep = currentStep;
        invalidate();
    }
}

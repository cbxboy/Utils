package com.zorro.utils.CustomizeView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Dimension;
import androidx.annotation.Nullable;

import com.zorro.utils.R;

/**
 * @Author : cbx
 * @Email : 673591077@qq.com
 * @Date : on 2022-09-05 10:37.
 * @Description :自定义实现TextView
 */
public class MyTextView extends View {

    private String mText;
    private int textSize = 15;
    private int textColor;
    private Paint mPaint;

    public MyTextView(Context context) {
        this(context,null);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTextView);
        mText = typedArray.getString(R.styleable.MyTextView_MyText);
        textSize = typedArray.getDimensionPixelSize(R.styleable.MyTextView_MyTextSize,sp2px(textSize));
        textColor = typedArray.getColor(R.styleable.MyTextView_MyTextColor,textColor);
        typedArray.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(textSize);
        mPaint.setColor(textColor);

    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getResources().getDisplayMetrics());
    }

    /**
     * 自定义View的测量方法
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //指定控件的宽高，需要测量
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST) {//在布局中指定了wrap_content

            Rect bounds = new Rect();
            mPaint.getTextBounds(mText, 0, mText.length(), bounds);
            widthSize = bounds.width() + getPaddingLeft() + getPaddingRight();
        } else if (widthMode == MeasureSpec.EXACTLY) {//在布局中指定了确定的值 比如:100dp / match_parent

        } else if (widthMode == MeasureSpec.UNSPECIFIED) {//尽可能的大，很少能用到
            //ListView、ScrollView在测量子布局的时候会用
        }


        if (heightMode == MeasureSpec.AT_MOST) {//在布局中指定了wrap_content

            Rect bounds = new Rect();
            mPaint.getTextBounds(mText, 0, mText.length(), bounds);
            heightSize = bounds.height() + getPaddingTop() + getPaddingBottom();
        } else if (heightMode == MeasureSpec.EXACTLY) {//在布局中指定了确定的值  比如:100dp / match_parent

        } else if (heightMode == MeasureSpec.UNSPECIFIED) {//尽可能的大，很少能用到
            //ListView、ScrollView在测量子布局的时候会用
        }

        setMeasuredDimension(widthSize, heightSize);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 自定义控件之绘图篇（ 五）：drawText()详解
         * https://blog.csdn.net/harvic880925/article/details/50423762
         */
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        //中心线到基线的距离（当前情况不适用）
        //int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
        //更正
        //onMeasure()方法设置的文字范围对应着“当前绘制顶线(ascent)”和“当前绘制底线(descent)”，并非“可绘制最顶线(top)”和“可绘制最底线(bottom)”
        //getHeight()得到的onMeasure()方法中的高度，此时中心线并不是bottom-top的中心（如果使用会出现文字显示不全的现象），而应该是descent-ascent的中心
        // 中心线到基线的距离
        int dy = (int) ((fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent);
        //得到基线（BaseLine）
        int baseLine = getHeight() / 2 + dy;

        int x = getPaddingLeft();
        canvas.drawText(mText, x, baseLine, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}

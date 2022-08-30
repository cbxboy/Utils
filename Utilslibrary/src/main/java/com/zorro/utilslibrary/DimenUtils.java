package com.zorro.utilslibrary;

import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * @Author : cbx
 * @Email : 673591077@qq.com
 * @Date : on 2022-08-29 15:55.
 * @Description :Dimen工具类
 */
public class DimenUtils {

    /**
     * 根据手机的分辨率将dp的单位转成为px
     * @param context 上下文
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率将px的单位转成为dp
     * @param context 上下文
     * @param pxValue px值
     * @return dp值
     */
    public static float px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxValue / scale + 0.5f);
    }

    /**
     * 获取手机屏幕宽度
     * @param context 上下文
     * @return 手机宽度
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(localDisplayMetrics);
        return localDisplayMetrics.widthPixels;
    }

    /**
     * 获取屏幕高度，高度包括状态栏
     * @param context 上下文
     * @return 手机高度
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(localDisplayMetrics);
        return localDisplayMetrics.heightPixels;
    }

    /**
     * 获取屏幕显示区域，即屏幕的长宽
     * @param context 上下文
     * @return 屏幕的长宽
     */
    public static Rect getScreenRect(Context context) {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(localDisplayMetrics);
        Rect rect = new Rect();
        rect.left = 0;
        rect.right = localDisplayMetrics.widthPixels;
        rect.top = 0;
        rect.bottom = localDisplayMetrics.heightPixels;
        return rect;
    }

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context){
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 将View的位置转化为相对屏幕的位置
     * @param v view
     * @return 相对屏幕的位置
     */
    public static Rect convertToScreenLocation(View v){
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        Rect rect = new Rect();
        rect.left = location[0];
        rect.top = location[1];
        rect.right = rect.left + v.getWidth();
        rect.bottom = rect.top + v.getHeight();
        return rect;
    }
}

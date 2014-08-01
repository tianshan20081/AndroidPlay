package com.aoeng.play.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.aoeng.play.manager.BaseApplication;
import com.aoeng.play.ui.BaseUI;

public class UIUtils {
	public static Context getContext() {
		return BaseApplication.getApplication();
	}

	public static Thread getMainThread() {
		return BaseApplication.getMainThread();
	}

	public static long getMainThreadId() {
		return BaseApplication.getMainThreadId();
	}

	/**
	 * dip 2 px
	 * 
	 * @param dip
	 * @return
	 */
	public static int dip2px(int dip) {
		final float scale = getContext().getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f);
	}

	/**
	 * px 2 dip
	 * 
	 * @param px
	 * @return
	 */
	public static int px2dip(int px) {
		final float scale = getContext().getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

	public static Handler getHandler() {
		// 获得主线程 Looper
		Looper mainLooper = BaseApplication.getMainThreadLooper();
		// 获取主线程 Handler
		Handler handler = new Handler(mainLooper);
		return handler;
	}

	/**
	 * 延时主线程执行 runnable
	 * 
	 * @param runnable
	 * @param delayMillis
	 * @return
	 */
	public static boolean postDelayed(Runnable runnable, long delayMillis) {
		return getHandler().postAtTime(runnable, delayMillis);
	}

	/**
	 * 在主线程执行 runnable
	 * 
	 * @param runnable
	 * @return
	 */
	public static boolean post(Runnable runnable) {
		return getHandler().post(runnable);
	}

	/**
	 * 从主线程 looper 里面移除 runnable
	 * 
	 * @param runnable
	 */
	public static void removeCallBacks(Runnable runnable) {
		getHandler().removeCallbacks(runnable);
	}

	public static View inflate(int resId) {
		return LayoutInflater.from(getContext()).inflate(resId, null);
	}

	/**
	 * 获取资源
	 * 
	 * @return
	 */
	public static Resources getResources() {
		return getContext().getResources();
	}

	/**
	 * 获取文字
	 * 
	 * @param strId
	 * @return
	 */
	public static String getString(int strId) {
		return getContext().getString(strId);
	}

	/**
	 * 获取文字数组
	 * 
	 * @param strsId
	 * @return
	 */
	public static String[] getStringArray(int strsId) {
		return getResources().getStringArray(strsId);
	}

	/**
	 * 获取 dimens
	 * 
	 * @param resId
	 * @return
	 */
	public static int getDimens(int resId) {
		return getResources().getDimensionPixelSize(resId);
	}

	/**
	 * 获取 drawable
	 * 
	 * @param resId
	 * @return
	 */
	public static Drawable getDrawable(int resId) {
		return getResources().getDrawable(resId);
	}

	/**
	 * 获取颜色
	 * 
	 * @param resId
	 * @return
	 */
	public static int getColor(int resId) {
		return getResources().getColor(resId);
	}

	/**
	 * 获取颜色选择器
	 * 
	 * @param resId
	 * @return
	 */
	public static ColorStateList getColorStateList(int resId) {
		return getResources().getColorStateList(resId);
	}

	public static boolean isRunInMainThread() {
		return android.os.Process.myTid() == getMainThreadId();
	}

	public static void runInMianThread(Runnable runnable) {
		if (isRunInMainThread()) {
			runnable.run();
		} else
			post(runnable);
	}

	public static void startActivity(Intent intent) {
		BaseUI ui = BaseUI.getCurrentShowUI();
		if (ui != null) {
			ui.startActivity(intent);
		} else {
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			getContext().startActivity(intent);
		}
	}

	/**
	 * 對 Toast 簡單的封裝 。線程安全，可以在非 UI 線程調用
	 * 
	 * @param resId
	 */
	public static void showToastSafe(final int resId) {
		showToastSafe(getString(resId));
	}

	/**
	 * 對 Toast 簡單的封裝 。線程安全，可以在非 UI 線程調用
	 * 
	 * @param str
	 */
	private static void showToastSafe(final String str) {
		// TODO Auto-generated method stub
		if (isRunInMainThread()) {
			showToast(str);
		} else {
			post(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					showToast(str);
				}
			});
		}
	}

	private static void showToast(String str) {
		// TODO Auto-generated method stub
		BaseUI ui = BaseUI.getCurrentShowUI();
		if (null != ui) {
			Toast.makeText(ui, str, Toast.LENGTH_LONG).show();
		}

	}

}

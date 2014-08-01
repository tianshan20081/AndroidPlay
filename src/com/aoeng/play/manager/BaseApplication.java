package com.aoeng.play.manager;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

public class BaseApplication extends Application {
	/* 获得主线程 LOOPER */
	private static Looper mMainLooper;
	/* 獲得主線程 */
	private static Thread mMainThread;
	/** 主线程Handler */
	private static Handler mMainThreadHandler;
	/** 主线程ID */
	private static int mMainThreadId = -1;
	/** 全局Context，原理是因为Application类是应用最先运行的，所以在我们的代码调用时，该值已经被赋值过了 */
	private static BaseApplication mInstance;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		mMainLooper = getMainLooper();
		mMainThread = Thread.currentThread();
		mMainThreadHandler = new Handler();
		mMainThreadId = android.os.Process.myPid();
		mInstance = this;
	}

	public static BaseApplication getApplication() {
		return mInstance;
	}

	/* 獲得主線程的 ID */
	public static int getMainThreadId() {
		return mMainThreadId;
	}

	// /*獲得主線程的 handler*/
	public static Handler getMainThreadHandler() {
		return mMainThreadHandler;
	}

	/* 获得主线程 */
	public static Thread getMainThread() {
		return mMainThread;
	}

	/* 获得主线程的 LOOPER */
	public static Looper getMainThreadLooper() {
		return mMainLooper;
	}

}

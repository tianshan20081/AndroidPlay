package com.aoeng.play.ui;

import java.util.LinkedList;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class BaseUI extends ActionBarActivity {
	// 记录处于前台的 UI
	private static BaseUI mCurrentShowUI = null;
	// 用于记录 打开过的 UI ACTIVITY
	private static final LinkedList<BaseUI> mUIs = new LinkedList<BaseUI>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
		initView();

		initActionBar();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		mCurrentShowUI = this;
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		mCurrentShowUI = null;
		super.onPause();
	}

	protected void initActionBar() {
	};

	protected void initView() {
	};

	protected void init() {
	};

	public static void finishAll() {
		LinkedList<BaseUI> copy;
		synchronized (mUIs) {
			copy = new LinkedList<BaseUI>(mUIs);
		}
		for (BaseUI ui : copy) {
			ui.finish();
		}
	}

	public static void finishAll(BaseUI expect) {
		LinkedList<BaseUI> copy;
		synchronized (mUIs) {
			copy = new LinkedList<BaseUI>(mUIs);
		}
		for (BaseUI baseUI : copy) {
			if (baseUI != expect)
				baseUI.finish();
		}
	}

	/**
	 * 是否有启动的 UI
	 * 
	 * @return
	 */
	public static boolean isHasUI() {
		return mUIs.size() > 0;
	}

	/**
	 * 获取 现在处于前台显示的 UI
	 * 
	 * @return
	 */
	public static BaseUI getForegroundActivity() {
		return mCurrentShowUI;
	}

	/**
	 * 获得处于栈顶的 UI 不论其是否当前显示
	 * 
	 * @return
	 */
	public static BaseUI getTopUI() {
		LinkedList<BaseUI> copy;
		synchronized (mUIs) {
			copy = new LinkedList<BaseUI>();
		}
		if (copy.size() > 0) {
			return copy.get(copy.size() - 1);
		}
		return null;
	}

	public static void exit() {
		finishAll();
		android.os.Process.killProcess(android.os.Process.myPid());
	}
}

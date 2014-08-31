package com.aoeng.play.ui.holder;

import android.view.View;
import android.widget.ImageView;

import com.aoeng.play.http.image.ImageLoader;

/**
 * 
 * @author aoeng Aug 31, 2014 11:45:40 AM
 * @param <Data>
 */
public abstract class BaseHolder<Data> {
	protected View mRootView;
	protected int mPosition;
	protected Data mData;

	public BaseHolder() {
		// TODO Auto-generated constructor stub
		mRootView = initView();
		mRootView.setTag(this);
	}

	public View getRootView() {
		return mRootView;
	}

	public void setData(Data data) {
		this.mData = data;
		refreshView();
	}

	public Data getData() {
		return this.mData;
	}

	public void setPosition(int position) {
		this.mPosition = position;
	}

	public int getPosition() {
		return this.mPosition;
	}

	public void recyleImageView(ImageView view) {
		Object tag = view.getTag();
		if (null != tag && tag instanceof String) {
			String key = (String) tag;
			ImageLoader.cancel(key);
		}
	}

	protected abstract View initView();

	protected abstract void refreshView();

	public void recycle() {

	}
}

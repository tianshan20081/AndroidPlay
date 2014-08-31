package com.aoeng.play.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.aoeng.play.R;
import com.aoeng.play.utils.UIUtils;

/**
 * 
 * @author aoeng Aug 31, 2014 6:36:28 PM
 */
public class BaseListView extends ListView {
	public BaseListView(Context context) {
		this(context, null);
	}

	public BaseListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BaseListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		setDivider(UIUtils.getResources().getDrawable(R.drawable.nothing));
		setCacheColorHint(UIUtils.getColor(R.color.bg_page));
		setSelector(UIUtils.getResources().getDrawable(R.drawable.nothing));
	}
}

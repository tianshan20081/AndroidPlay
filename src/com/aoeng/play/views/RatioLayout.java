package com.aoeng.play.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class RatioLayout extends FrameLayout {
	private float ratio = 0.0f ;
	

	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

	public RatioLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public RatioLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public RatioLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}


	
}

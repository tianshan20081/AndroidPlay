package com.aoeng.play.ui.fragment;

import java.util.List;

import android.view.View;

import com.aoeng.play.domain.AppInfo;
import com.aoeng.play.http.protocol.GameProtocol;
import com.aoeng.play.ui.adapter.GameAdapter;
import com.aoeng.play.ui.fragment.LoadingPage.LoadResult;
import com.aoeng.play.ui.views.BaseListView;
import com.aoeng.play.utils.UIUtils;

public class GameFragment extends BaseFragment {
	private List<AppInfo> mDatas;
	private GameAdapter mAdapter;
	private BaseListView mListView;

	@Override
	protected LoadResult load() {
		// TODO Auto-generated method stub
		GameProtocol protocol = new GameProtocol();
		mDatas = protocol.load(0);
		return check(mDatas);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (null != mAdapter) {
			mAdapter.startObserver();
			mAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (null != mAdapter) {
			mAdapter.stopObserver();
		}
	}

	@Override
	protected View createLoadedView() {
		// TODO Auto-generated method stub
		mListView = new BaseListView(UIUtils.getContext());
		mAdapter = new GameAdapter(mListView, mDatas);
		mAdapter.startObserver();
		mListView.setAdapter(mAdapter);
		return mListView;
	}

}

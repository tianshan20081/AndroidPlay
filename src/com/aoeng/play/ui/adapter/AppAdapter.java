package com.aoeng.play.ui.adapter;

import java.util.List;

import android.widget.AbsListView;

import com.aoeng.play.domain.AppInfo;
import com.aoeng.play.http.protocol.AppProtocol;

public class AppAdapter extends ListBaseAdapter {

	public AppAdapter(AbsListView listView, List<AppInfo> datas) {
		super(listView, datas);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Load more
	 */
	@Override
	public List<AppInfo> onLoadMore() {
		// TODO Auto-generated method stub
		AppProtocol protocol = new AppProtocol();
		return protocol.load(getData().size());
	}

}

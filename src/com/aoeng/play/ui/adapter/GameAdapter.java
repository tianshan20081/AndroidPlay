package com.aoeng.play.ui.adapter;

import java.util.List;

import android.widget.AbsListView;

import com.aoeng.play.domain.AppInfo;
import com.aoeng.play.http.protocol.GameProtocol;

public class GameAdapter extends ListBaseAdapter {

	public GameAdapter(AbsListView listView, List<AppInfo> datas) {
		super(listView, datas);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<AppInfo> onLoadMore() {
		// TODO Auto-generated method stub
		GameProtocol protocol = new GameProtocol();

		return protocol.load(getData().size());
	}

}

package com.aoeng.play.ui.adapter;

import java.util.List;

import android.widget.AbsListView;

import com.aoeng.play.domain.AppInfo;
import com.aoeng.play.domain.DownloadInfo;
import com.aoeng.play.manager.DownloadManager;
import com.aoeng.play.manager.DownloadManager.DownloadObserver;
import com.aoeng.play.ui.holder.BaseHolder;
import com.aoeng.play.ui.holder.ListBaseHolder;
import com.aoeng.play.utils.UIUtils;

public abstract class ListBaseAdapter extends DefaultAdapter<AppInfo> implements DownloadObserver {
	public ListBaseAdapter(AbsListView listView, List<AppInfo> datas) {
		super(listView, datas);
	}

	@Override
	protected BaseHolder getHolder() {
		return new ListBaseHolder();
	}

	public void startObserver() {
		DownloadManager.getInstance().registerObserver(this); 
	}

	public void stopObserver() {
		DownloadManager.getInstance().unRegisterObserver(this);
	}

	@Override
	public void onDownloadStateChanged(final DownloadInfo info) {
		refreshHolder(info);
	}

	@Override
	public void onDownloadProgressed(DownloadInfo info) {
		refreshHolder(info);
	}

	private void refreshHolder(final DownloadInfo info) {
		List<BaseHolder> displayedHolders = getDisplayedHolders();

		for (int i = 0; i < displayedHolders.size(); i++) {
			BaseHolder baseHolder = displayedHolders.get(i);
			if (baseHolder instanceof ListBaseHolder) {
				final ListBaseHolder holder = (ListBaseHolder) baseHolder;
				AppInfo appInfo = holder.getData();
				if (appInfo.getId() == info.getId()) {
					UIUtils.post(new Runnable() {
						@Override
						public void run() {
							holder.refreshState(info.getDownloadState(), info.getProgress());
						}
					});
				}
			}
		}
	}

	@Override
	public void onItemClickInner(int position) {
		List<AppInfo> data = getData();
		if (position < data.size()) {
			// Intent intent = new Intent(UIUtils.getContext(), DetailUI.class);
			// AppInfo info = data.get(position);
			// intent.putExtra(DetailUI.PACKAGENAME, info.getPackageName());
			// UIUtils.startActivity(intent);
		}
	}
}

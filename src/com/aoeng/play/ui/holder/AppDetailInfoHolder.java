package com.aoeng.play.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.aoeng.play.R;
import com.aoeng.play.domain.AppInfo;
import com.aoeng.play.http.image.ImageLoader;
import com.aoeng.play.utils.StringUtils;
import com.aoeng.play.utils.UIUtils;

public class AppDetailInfoHolder extends BaseHolder<AppInfo> {
	private ImageView mIcon;
	private TextView mTitleTxt, mDownloadTxt, mViesionTxt, mDateTxt, mSizeTxt;
	private RatingBar mRating;

	@Override
	protected View initView() {
		View view = UIUtils.inflate(R.layout.app_detail_info);
		mIcon = (ImageView) view.findViewById(R.id.item_icon);
		mRating = (RatingBar) view.findViewById(R.id.item_rating);
		mTitleTxt = (TextView) view.findViewById(R.id.item_title);
		mDownloadTxt = (TextView) view.findViewById(R.id.item_download);
		mViesionTxt = (TextView) view.findViewById(R.id.item_version);
		mDateTxt = (TextView) view.findViewById(R.id.item_date);
		mSizeTxt = (TextView) view.findViewById(R.id.item_size);
		return view;
	}

	@Override
	public void refreshView() {
		AppInfo info = getData();
		String url = info.getIconUrl();
		mIcon.setTag(url);
		ImageLoader.load(mIcon, url);
		mRating.setRating(info.getStars());
		mTitleTxt.setText(info.getName());
		mDownloadTxt.setText(UIUtils.getString(R.string.app_detail_download) + info.getDownloadNum());
		mViesionTxt.setText(UIUtils.getString(R.string.app_detail_version) + info.getVersion());
		mDateTxt.setText(UIUtils.getString(R.string.app_detail_date) + info.getDate());
		mSizeTxt.setText(UIUtils.getString(R.string.app_detail_size) + StringUtils.formatFileSize(info.getSize()));
	}
}
package com.aoeng.play.domain;

import com.aoeng.play.manager.DownloadManager;
import com.aoeng.play.utils.FileUtils;

public class DownloadInfo {
	private long id;// app 的id 和 appInfo 中的 id 对应
	private String appName; // app 软件的名称
	private long appSize = 0; // app 的 size
	private long currentSize = 0; // 当前 的 size
	private int downloadState = 0; // 下载状态
	private String url; // 下载地址
	private String path; // 保存路径

	public static DownloadInfo clone(AppInfo info) {
		DownloadInfo downloadInfo = new DownloadInfo();
		downloadInfo.id = info.getId();
		downloadInfo.appName = info.getName();
		downloadInfo.appSize = info.getSize();
		downloadInfo.currentSize = 0;
		downloadInfo.downloadState = DownloadManager.STATE_NONE;
		downloadInfo.url = info.getDownloadUrl();
		downloadInfo.path = FileUtils.getDownloadDir() + downloadInfo.appName
				+ ".apk";

		return downloadInfo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public long getAppSize() {
		return appSize;
	}

	public void setAppSize(long appSize) {
		this.appSize = appSize;
	}

	public long getCurrentSize() {
		return currentSize;
	}

	public void setCurrentSize(long currentSize) {
		this.currentSize = currentSize;
	}

	public int getDownloadState() {
		return downloadState;
	}

	public void setDownloadState(int downloadState) {
		this.downloadState = downloadState;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	

}

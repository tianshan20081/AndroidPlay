package com.aoeng.play.manager;

import java.util.HashMap;
import java.util.Map;

import com.aoeng.play.domain.DownloadInfo;

/**
 * 下载工具类
 * 
 * @author paynet
 * 
 */
public class DownloadManager {
	public static final int STATE_NONE = 0;
	/** 等待中 */
	public static final int STATE_WAITING = 1;
	/** 下载中 */
	public static final int STATE_DOWNLOADING = 2;
	/** 暂停 */
	public static final int STATE_PAUSE = 3;
	/** 下载完成 */
	public static final int STATE_DOWNLOADED = 4;
	/** 下载失败 */
	public static final int STATE_ERROR = 5;

	private DownloadManager() {
	}

	private Map<Long, DownloadInfo> mDownloadMap = new HashMap<>();
}

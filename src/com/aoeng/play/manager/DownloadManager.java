package com.aoeng.play.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.aoeng.play.domain.AppInfo;
import com.aoeng.play.domain.DownloadInfo;
import com.aoeng.play.http.HttpHelper;
import com.aoeng.play.http.HttpHelper.HttpResult;
import com.aoeng.play.utils.IOUtils;
import com.aoeng.play.utils.LogUtils;
import com.aoeng.play.utils.UIUtils;

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

	private static DownloadManager instance;

	private DownloadManager() {
	}

	private Map<Long, DownloadInfo> mDownloadMap = new HashMap<Long, DownloadInfo>();
	private List<DownloadObserver> mObserver = new ArrayList<DownloadObserver>();
	private Map<Long, DownloadTask> mTaskMap = new HashMap<Long, DownloadTask>();

	public static synchronized DownloadManager getInstance() {
		if (null == instance) {
			instance = new DownloadManager();
		}
		return instance;
	}

	public void registerObserver(DownloadObserver observer) {
		synchronized (mObserver) {
			if (!mObserver.contains(observer)) {
				mObserver.add(observer);
			}
		}
	}

	public void unRegisterObserver(DownloadObserver observer) {
		synchronized (mObserver) {
			if (mObserver.contains(observer)) {
				mObserver.remove(observer);
			}
		}
	}

	public synchronized void download(AppInfo appInfo) {
		DownloadInfo info = mDownloadMap.get(appInfo.getId());
		if (null == info) {
			info = DownloadInfo.clone(appInfo);
			mDownloadMap.put(appInfo.getId(), info);
		}
		if (info.getDownloadState() == STATE_NONE
				|| info.getDownloadState() == STATE_PAUSE
				|| info.getDownloadState() == STATE_ERROR) {
			info.setDownloadState(STATE_WAITING);
			notifyDownloadStateChanged(info);
			DownloadTask task = new DownloadTask(info);
			mTaskMap.put(info.getId(), task);
			ThreadManager.getDownloadPool().execute(task);
		}
	}

	public synchronized void pause(AppInfo appInfo) {
		stopDownload(appInfo);
		DownloadInfo info = mDownloadMap.get(appInfo.getId());
		if (null != info) {
			info.setDownloadState(STATE_PAUSE);
			notifyDownloadStateChanged(info);
		}
	}

	public synchronized void cancel(AppInfo appInfo) {
		stopDownload(appInfo);
		DownloadInfo info = mDownloadMap.get(appInfo.getId());
		if (null != info) {
			info.setDownloadState(STATE_NONE);
			notifyDownloadStateChanged(info);
			info.setCurrentSize(0);
			File file = new File(info.getPath());
			file.delete();
		}
	}

	public synchronized void install(AppInfo appInfo) {
		stopDownload(appInfo);
		DownloadInfo info = mDownloadMap.get(appInfo.getId());
		if (null != info) {
			Intent installIntent = new Intent(Intent.ACTION_VIEW);
			installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			installIntent.setDataAndType(Uri.parse("file://" + info.getPath()),
					"application/vnd.android.packacge-archive");
			UIUtils.getContext().startActivity(installIntent);
		}
		notifyDownloadStateChanged(info);

	}

	private void stopDownload(AppInfo appInfo) {
		// TODO Auto-generated method stub
		DownloadTask task = mTaskMap.remove(appInfo.getId());
		if (null != task) {
			ThreadManager.getDownloadPool().cancel(task);
		}
	}

	public synchronized void open(AppInfo appInfo) {
		try {
			Context context = UIUtils.getContext();
			Intent intent = context.getPackageManager()
					.getLaunchIntentForPackage(appInfo.getPackageName());
			context.startActivity(intent);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e(e);
		}
	}

	public synchronized DownloadInfo getDownloadInfo(long id) {
		return mDownloadMap.get(id);
	}

	public class DownloadTask implements Runnable {
		private DownloadInfo info;

		public DownloadTask(DownloadInfo info) {
			this.info = info;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			info.setDownloadState(STATE_DOWNLOADING);
			notifyDownloadStateChanged(info);
			File file = new File(info.getPath());
			HttpResult httpResult = null;
			InputStream in = null;
			if (info.getCurrentSize() == 0 || !file.exists()
					|| file.length() != info.getCurrentSize()) {
				info.setCurrentSize(0);
				file.delete();
				httpResult = HttpHelper.download(HttpHelper.URL
						+ "download?name=" + info.getUrl());
			} else
				httpResult = HttpHelper.download(HttpHelper.URL
						+ "download?name=" + info.getUrl() + "&range="
						+ info.getCurrentSize());
			if (httpResult == null
					|| (in = httpResult.getInputStream()) == null) {
				info.setDownloadState(STATE_ERROR);
				notifyDownloadStateChanged(info);
			} else {
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(file, true);
					int count = -1;
					byte[] buffer = new byte[1024];
					while ((count = in.read(buffer)) != -1) {
						fos.write(buffer, 0, count);
						fos.flush();
						info.setCurrentSize(info.getCurrentSize() + count);
						notifyDownloadProcessed(info);
					}
				} catch (Exception e) {
					// TODO: handle exception
					LogUtils.e(e);
					info.setDownloadState(STATE_ERROR);
					notifyDownloadStateChanged(info);
					info.setCurrentSize(0);
					file.delete();
				} finally {
					IOUtils.close(fos);
					if (null != httpResult)
						httpResult.close();
				}
				if (info.getCurrentSize() == info.getAppSize()) {
					info.setDownloadState(STATE_DOWNLOADED);
					notifyDownloadStateChanged(info);
				} else if (info.getDownloadState() == STATE_PAUSE)
					notifyDownloadProcessed(info);
				else {
					info.setDownloadState(STATE_ERROR);
					notifyDownloadStateChanged(info);
					info.setCurrentSize(0);
					file.delete();
				}

			}
			mTaskMap.remove(info.getId());

		}

	}

	public void notifyDownloadStateChanged(DownloadInfo info) {
		// TODO Auto-generated method stub
		synchronized (mObserver) {
			for (DownloadObserver observer : mObserver) {
				observer.onDownloadStateChanged(info);
			}
		}
	}

	public void notifyDownloadProcessed(DownloadInfo info) {
		// TODO Auto-generated method stub
		synchronized (mObserver) {
			for (DownloadObserver observer : mObserver) {
				observer.onDownloadProcessed(info);
			}
		}

	}

	public interface DownloadObserver {
		public void onDownloadStateChanged(DownloadInfo info);

		public void onDownloadProcessed(DownloadInfo info);
	}

}

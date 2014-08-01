package com.aoeng.play.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

/***
 * 一个简易的线程池管理类,提供三个线程池
 * 
 * @author paynet
 * 
 */
public class ThreadManager {
	public static final String DEFAULT_SINGLE_POOL_NAME = "DEFAULT_SINGLE_POOL_NAME";
	private static ThreadPoolProxy mLongPool = null;
	private static Object mLongLock = new Object();
	private static ThreadPoolProxy mShortPool = null;
	private static Object mShortLock = new Object();
	private static ThreadPoolProxy mDownloadPool = null;
	private static Object mDownloadLock = new Object();

	private static Map<String, ThreadPoolProxy> mMap = new HashMap<>();
	private static Object mSingleLock = new Object();

	/**
	 * 
	 * @return 获取下载线程
	 */
	public static ThreadPoolProxy getDownloadPool() {
		synchronized (mDownloadPool) {
			if (mDownloadPool == null)
				mDownloadPool = new ThreadPoolProxy(3, 3, 5l);
			return mDownloadPool;
		}

	}

	/**
	 * 
	 * @return 获取一个长耗时任务的线程池,避免和短耗时的任务相冲突
	 */
	public static ThreadPoolProxy getLongPool() {
		synchronized (mLongPool) {
			if (mLongPool == null)
				mLongPool = new ThreadPoolProxy(5, 5, 5l);
			return mLongPool;
		}
	}

	/**
	 * 
	 * @return 获取一个短耗时任务的线程池,避免和长耗时的线程池相互冲突
	 */
	public static ThreadPoolProxy getShortPool() {
		synchronized (mShortPool) {
			if (mShortPool == null)
				mShortPool = new ThreadPoolProxy(2, 2, 5l);
			return mShortPool;
		}

	}

	public static ThreadPoolProxy getSinglePool() {
		return getSinglePool(DEFAULT_SINGLE_POOL_NAME);
	}

	/**
	 * 
	 * @param name
	 * @return 获得一个单线程池,所有任务将会被按照加入的顺序执行,免除了同步开销的问题
	 */
	private static ThreadPoolProxy getSinglePool(String name) {
		// TODO Auto-generated method stub
		synchronized (mSingleLock) {
			ThreadPoolProxy singlePool = mMap.get(name);
			if (singlePool == null) {
				singlePool = new ThreadPoolProxy(1, 1, 5l);
				mMap.put(name, singlePool);
			}
			return singlePool;
		}
	}

	public static class ThreadPoolProxy {
		private ThreadPoolExecutor mPool;
		private int mCorePoolSize;
		private int mMaxPoolSize;
		private long mKeepAliveTime;

		private ThreadPoolProxy(int corePoolSize, int maxPoolSize,
				long keepALiveTime) {
			this.mCorePoolSize = corePoolSize;
			this.mMaxPoolSize = maxPoolSize;
			this.mKeepAliveTime = keepALiveTime;
		}

		public synchronized void execute(Runnable run) {
			if (run == null) {
				return;
			}
			if (mPool != null || mPool.isShutdown()) {
				mPool = new ThreadPoolExecutor(mCorePoolSize, mMaxPoolSize,
						mKeepAliveTime, TimeUnit.MILLISECONDS,
						new LinkedBlockingQueue<Runnable>(),
						Executors.defaultThreadFactory(), new AbortPolicy());
			}
			mPool.execute(run);
		}

		/**
		 * 取消线程池中 某个还未执行的线程
		 * 
		 * @param run
		 */
		public synchronized void cancel(Runnable run) {
			if (mPool != null && (!mPool.isShutdown())) {
				mPool.getQueue().remove(run);
			}
		}

		/**
		 * 判断线程池中 是否有 某一个线程
		 * 
		 * @param run
		 * @return
		 */
		public synchronized boolean contains(Runnable run) {
			if (mPool != null && (!mPool.isShutdown())) {
				return mPool.getQueue().contains(run);
			}
			return false;
		}

		/**
		 * // * 立刻關閉線程池哦,並且正在執行的縣城也會被關閉
		 */
		public void stop() {
			if (mPool != null && (!mPool.isShutdown())) {
				mPool.shutdownNow();
			}
		}

		// ** 平缓关闭单任务线程池，但是会确保所有已经加入的任务都将会被执行完毕才关闭
		public synchronized void shudown() {
			if (mPool != null && (!mPool.isShutdown())) {
				mPool.shutdownNow();
			}
		}
	}
}

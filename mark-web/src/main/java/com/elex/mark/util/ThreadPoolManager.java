package com.elex.mark.util;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池管理类
 * 
 * @author sun
 *
 */
public class ThreadPoolManager {
	private static ExecutorService _generalES = Executors
			.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2, new GameThreadFactory("COK-GeneralPool"));
	private static ScheduledExecutorService _generalScheduledES = Executors.newScheduledThreadPool(
			Runtime.getRuntime().availableProcessors() * 2, new GameThreadFactory("COK-GerenalSTPool"));
	
	/**
	 * 提交一个任务给线程池
	 * 
	 * @param r
	 *            任务对象
	 * @return future对象
	 */
	public static Future<?> execute(Runnable r) {
		return _generalES.submit(r);
	}

	public static <T> Future<T> execute(Callable<T> r) {
		return _generalES.submit(r);
	}

	/**
	 * 一段时间后执行一个任务
	 * 
	 * @param r
	 *            任务对象
	 * @param delay
	 *            延迟时间
	 * @param tu
	 *            时间单位
	 * @return future对象
	 */
	public static ScheduledFuture<?> schedule(Runnable r, long delay, TimeUnit tu) {
		return _generalScheduledES.schedule(r, delay, tu);
	}

	/**
	 * 定时执行一个任务
	 * 
	 * @param r
	 *            任务对象
	 * @param initial
	 *            初始任务开始时间
	 * @param delay
	 *            任务固定定时时间
	 * @param tu
	 *            时间单位
	 * @return future对象
	 */
	public static ScheduledFuture<?> scheduleAtFixedRate(Runnable r, long initial, long delay, TimeUnit tu) {
		return _generalScheduledES.scheduleAtFixedRate(r, initial, delay, tu);
	}

	private static class GameThreadFactory implements ThreadFactory {
		private String name;
		private AtomicInteger threadNumber = new AtomicInteger(1);
		private ThreadGroup group;

		public GameThreadFactory(String name) {
			this.name = name;
			group = new ThreadGroup(name);
		}

		public Thread newThread(Runnable r) {
			Thread t = new Thread(group, r);
			t.setName(name + "-" + threadNumber.getAndIncrement());
			return t;
		}

		@SuppressWarnings("unused")
		public ThreadGroup getGroup() {
			return group;
		}
	}
}

package com.elex.mark.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;


/**
 * JedisLock 的封装
 * 
 * @author sun
 *
 * @param <T>
 */
public class RedisLock<T> {
	private static final Logger logger = LoggerFactory.getLogger(RedisLock.class);
	
	private RedisSession session;

	public RedisLock(RedisSession session) {
		this.session = session;
//		this.session.autoClose = false;
	}

	public interface RedisLockRunner<T> {
		T run(RedisSession session);
	}

	/**
	 * acquire 超时时长为 5,000 ms<br/>
	 * lockKey 超时时长为 60,000 ms
	 * 
	 * @param lockKey
	 * @return
	 */
	public T exec(String lockKey, RedisLockRunner<T> runner) {
		JedisLock lock = new JedisLock(session, lockKey);
		return exec(lock, runner);
	}

	/**
	 * @param lockKey
	 * @param acquireTimeoutMillis	acquire 超时时长ms
	 * @return
	 */
	public T exec(String lockKey, int acquireTimeoutMillis, RedisLockRunner<T> runner) {
		JedisLock lock = new JedisLock(session, lockKey, acquireTimeoutMillis);
		return exec(lock, runner);
	}

	/**
	 * @param lockKey
	 * @param acquireTimeoutMillis	acquire 超时时长ms
	 * @param expiryTimeMillis		lockKey 超时时长ms
	 * @return
	 */
	public T exec(String lockKey, int acquireTimeoutMillis, int expiryTimeMillis, RedisLockRunner<T> runner) {
		JedisLock lock = new JedisLock(session, lockKey, acquireTimeoutMillis, expiryTimeMillis);
		return exec(lock, runner);
	}

	/**
	 * 
	 * @param lockKey
	 * @param acquireTimeoutMillis	acquire 超时时长ms
	 * @param expiryTimeMillis		lockKey 超时时长ms
	 * @param uuid					锁标识
	 * @return
	 */
	public T exec(String lockKey, int acquireTimeoutMillis, int expiryTimeMillis, UUID uuid, RedisLockRunner<T> runner) {
		JedisLock lock = new JedisLock(session, lockKey, acquireTimeoutMillis, expiryTimeMillis, uuid);
		return exec(lock, runner);
	}

	private T exec(JedisLock lock, RedisLockRunner<T> runner) {
		T result = null;
		long start = System.currentTimeMillis();
		try {
			if (lock.acquire()) {
				result = runner.run(session);
			}
		} catch (Throwable e) {
			logger.error("RedisLock error: lock " + lock.getLockKeyPath(), e);
			throw new RuntimeException("Redis execute exception", e);
		} finally {
			if (lock != null) {
				lock.release();
			}
			if (session != null) {
//				session.close();
				session = null;
			}
		}
		long use = System.currentTimeMillis() - start;
		if(use > 1000){
			logger.info("RedisLock slow exec: lock {} use {}", lock.getLockKeyPath(), use);
		}
		return result;
	}
}
package com.elex.mark.util;

import java.util.UUID;

/**
 * 修改了网上的 JedisLock , 把Jedis 改为 RedisSession。(方便统一监控)
 * 
 * Redis distributed lock implementation (fork by Bruno Bossola
 * <bbossola@gmail.com>)
 * 
 * @author Alois Belaska <alois.belaska@gmail.com>
 */
public class JedisLock {
	private static final Lock NO_LOCK = new Lock(new UUID(0l, 0l), 0l);
	private static final int ONE_SECOND = 1000;
	public static final int DEFAULT_EXPIRY_TIME_MILLIS = 60000;
	public static final int DEFAULT_ACQUIRE_TIMEOUT_MILLIS = 5000;
	public static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = 100;

	private final RedisSession session;
	private final String lockKeyPath;
	private final int lockExpiryInMillis;
	private final int acquiryTimeoutInMillis;
	private final UUID lockUUID;

	private Lock lock = null;

	public JedisLock(RedisSession session, String lockKey) {
		this(session, lockKey, DEFAULT_ACQUIRE_TIMEOUT_MILLIS, DEFAULT_EXPIRY_TIME_MILLIS);
	}

	public JedisLock(RedisSession session, String lockKey, int acquireTimeoutMillis) {
		this(session, lockKey, acquireTimeoutMillis, DEFAULT_EXPIRY_TIME_MILLIS);
	}

	public JedisLock(RedisSession session, String lockKey, int acquireTimeoutMillis, int expiryTimeMillis) {
		this(session, lockKey, acquireTimeoutMillis, expiryTimeMillis, UUID.randomUUID());
	}

	public JedisLock(RedisSession session, String lockKey, int acquireTimeoutMillis, int expiryTimeMillis, UUID uuid) {
		this.session = session;
		this.lockKeyPath = lockKey;
		this.acquiryTimeoutInMillis = acquireTimeoutMillis;
		this.lockExpiryInMillis = expiryTimeMillis + 1;
		this.lockUUID = uuid;
	}

	public UUID getLockUUID() {
		return lockUUID;
	}

	public String getLockKeyPath() {
		return lockKeyPath;
	}

	public synchronized boolean acquire() throws InterruptedException {
		return acquire(session);
	}

	protected synchronized boolean acquire(RedisSession session) throws InterruptedException {
		int timeout = acquiryTimeoutInMillis;
		while (timeout >= 0) {
			final Lock newLock = asLock(System.currentTimeMillis() + lockExpiryInMillis);
			
			// 当前不存在锁
			if (session.setnx(lockKeyPath, newLock.toString()) == 1) {
				session.pexpire(lockKeyPath, lockExpiryInMillis);
				this.lock = newLock;
				return true;
			}

			// 当前redis中的锁
			final String currentValueStr = session.get(lockKeyPath);
			final Lock currentLock = Lock.fromString(currentValueStr);

			// 当前锁是自己, 重新锁定并设定超时时间
			if (currentLock.isSelf(lockUUID)) {
				String oldValueStr = session.getSet(lockKeyPath, newLock.toString());
				if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
					session.pexpire(lockKeyPath, lockExpiryInMillis);
					this.lock = newLock;
					return true;
				}
			}
			
			// 休眠 100ms
			timeout -= DEFAULT_ACQUIRY_RESOLUTION_MILLIS;
			Thread.sleep(DEFAULT_ACQUIRY_RESOLUTION_MILLIS);
		}
		return false;
	}

	/**
	 * Renew lock.
	 * 
	 * @return true if lock is acquired, false otherwise
	 * @throws InterruptedException
	 *             in case of thread interruption
	 */
	public boolean renew() throws InterruptedException {
		final Lock lock = Lock.fromString(session.get(lockKeyPath));
		if (!lock.isSelf(lockUUID)) {
			return false;
		}
		return acquire(session);
	}

	/**
	 * Acquired lock release.
	 */
	public synchronized void release() {
		release(session);
	}

	/**
	 * Acquired lock release.
	 * 
	 * @param
	 */
	protected synchronized void release(RedisSession session) {
		if (isLocked()) {
			session.del(lockKeyPath);
			this.lock = null;
		}
	}

	/**
	 * Check if owns the lock
	 * 
	 * @return true if lock owned
	 */
	public synchronized boolean isLocked() {
		return this.lock != null;
	}

	/**
	 * Returns the expiry time of this lock
	 * 
	 * @return the expiry time in millis (or null if not locked)
	 */
	public synchronized long getLockExpiryTimeInMillis() {
		return this.lock.getExpiryTime();
	}

	private Lock asLock(long expires) {
		return new Lock(lockUUID, expires);
	}

	protected static class Lock {
		private UUID uuid;
		private long expiryTime;

		protected Lock(UUID uuid, long expiryTimeInMillis) {
			this.uuid = uuid;
			this.expiryTime = expiryTimeInMillis;
		}

		protected static Lock fromString(String text) {
			try {
				String[] parts = text.split(":");
				UUID theUUID = UUID.fromString(parts[0]);
				long theTime = Long.parseLong(parts[1]);
				return new Lock(theUUID, theTime);
			} catch (Exception any) {
				return NO_LOCK;
			}
		}

		public UUID getUUID() {
			return uuid;
		}

		public long getExpiryTime() {
			return expiryTime;
		}

		@Override
		public String toString() {
			return uuid.toString() + ":" + expiryTime;
		}

		boolean isExpired() {
			return getExpiryTime() < System.currentTimeMillis();
		}

		boolean isSelf(UUID otherUUID) {
			return this.getUUID().equals(otherUUID);
		}
	}
}
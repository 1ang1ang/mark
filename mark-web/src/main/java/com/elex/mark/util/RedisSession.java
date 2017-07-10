/**
 * @author: lifangkai@elex-tech.com
 * @date: 2013年9月2日 下午8:14:30
 */
package com.elex.mark.util;

import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Tuple;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Redis缓存实现
 */
@Component
public class RedisSession {
    private static Logger logger = LoggerFactory.getLogger(RedisSession.class);

    @Autowired
    private JedisPool jedisPool;

    public RedisSession() {
    }

//    public void jedis.close() {
//        try {
//						Jedis jedis = jedisPool.getResource();
//            if (jedis == null) {
//                return;
//            }
//            jedis.jedis.close();
//        } catch (Throwable t) {
//            logger.error("close redis",   t);
//        }
//    }

    public boolean exists(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.exists(key);
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return false;
        } finally {
            jedis.close();
        }
    }

    public String set(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            String ret = jedis.set(key, field);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("set codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key + " field:" + field);
            }
            return ret;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return null;
        } finally {
            jedis.close();
        }
    }

    public String setex(String key, int ttl, String field){
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            String ret = jedis.setex(key, ttl, field);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("setex codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key + " field:" + field);
            }
            return ret;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return null;
        } finally {
            jedis.close();
        }
    }
    
    public Long setnx(String key, String value){
        Jedis jedis = jedisPool.getResource();
    	try {
    		Long ret = jedis.setnx(key, value);
    		return ret;
    	} catch (Throwable e) {
    		logger.error(getErrorOutPutInfo(jedis),   e);
    		return null;
    	} finally {
            jedis.close();
    	}
    }

    public String set(byte[] key, byte[] field) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            String ret = jedis.set(key, field);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("set [] codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key + " field:" + field);
            }
            return ret;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return null;
        } finally {
            jedis.close();
        }
    }

    public int hsetnx(String key, String field, String value) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            long result = jedis.hsetnx(key, field, value);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("hsetnx codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key + " field:" + field + " value:" + value);
            }
            return (int) result;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return 1;
        } finally {
            jedis.close();
        }
    }

    public Set<String> sMember(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            Set<String> set = jedis.smembers(key);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("sMember codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
            return set;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return new HashSet<>();
        } finally {
            jedis.close();
        }
    }

    public long sRem(String key, String... value) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            long ret = jedis.srem(key, value);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("sRem codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
            return ret;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return 0;
        } finally {
            jedis.close();
        }
    }

    public boolean sIsMember(String key, String member) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            boolean res = jedis.sismember(key, member);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("sIsMember codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
            return  res;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return false;
        } finally {
            jedis.close();
        }
    }

    public long sAdd(String key, String... value) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            long res = jedis.sadd(key, value);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("sAdd codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
            return res;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return 0;
        } finally {
            jedis.close();
        }
    }

    public String spop(String key) {
        String ret = null;
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            ret = jedis.spop(key);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("spop codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
        } finally {
            jedis.close();
        }
        return ret;
    }

    public void del(byte[] key) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            jedis.del(key);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("del [] codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
        } finally {
            jedis.close();
        }
    }

    public void del(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            jedis.del(key);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("del codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
        } finally {
            jedis.close();
        }
    }

    public long del(List<String> keys) {
        Jedis jedis = jedisPool.getResource();
        try {
            if(keys == null || keys.isEmpty()){
                return 0;
            }
            long beginTime = System.currentTimeMillis();
            String[] keyArr = keys.toArray(new String[keys.size()]);
            long res = jedis.del(keyArr);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("dels codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + keys);
            }
            return res;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
        } finally {
            jedis.close();
        }
        return 0;
    }

    public long lPush(String key, String value) {
        long ret = 0;
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            ret = jedis.lpush(key, value);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("lPush codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
        } finally {
            jedis.close();
        }
        return ret;
    }

    public List<String> lRange(String key, long start, long end) {
        List<String> ret = null;
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            ret = jedis.lrange(key, start, end);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("lRange codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
        } finally {
            jedis.close();
        }
        return ret;
    }

    public String rPop(String key) {
        String ret = null;
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            ret = jedis.rpop(key);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("rPop codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
        } finally {
            jedis.close();
        }
        return ret;
    }

    public String lpop(String key) {
        String ret = null;
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            ret = jedis.lpop(key);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("lpop codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
        } finally {
            jedis.close();
        }
        return ret;
    }

    public Set<String> keys(String pattern) {
        Set<String> ret;
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            ret = jedis.keys(pattern);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("keys codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + pattern);
            }
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return new HashSet<>();
        } finally {
                jedis.close();
        }
        return ret;
    }

    public String lIndex(String key, long count) {
        String ret;
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            ret = jedis.lindex(key, count);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("lIndex codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            ret = null;
        } finally {
                jedis.close();
        }
        return ret;
    }

    public long rPush(String key, String value) {
        long ret = 0;
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            ret = jedis.rpush(key, value);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("rPush codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
        } finally {
            jedis.close();
        }
        return ret;
    }

    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            String ret = jedis.get(key);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("get codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
            return ret;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return null;
        } finally {
            jedis.close();
        }
    }
    
    public String getSet(String key, String value) {
        Jedis jedis = jedisPool.getResource();
    	try {
            String ret = jedis.getSet(key, value);
            return ret;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return null;
        } finally {
            jedis.close();
        }
	}


    public byte[] get(byte[] key) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            byte[] ret = jedis.get(key);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("get[] codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
            return ret;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return null;
        } finally {
            jedis.close();
        }
    }

    public long incrBy(String key, long incValue) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            long ret = jedis.incrBy(key, incValue);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("incrBy codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
            return ret;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return 0;
        } finally {
            jedis.close();
        }
    }

    public long incr(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            long ret = jedis.incr(key);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("incr codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
            return ret;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return 0;
        } finally {
            jedis.close();
        }
    }

    public long decr(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            long ret = jedis.decr(key);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("decr codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
            return ret;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return 0;
        } finally {
            jedis.close();
        }
    }

    public byte[] hGet(byte[] key, byte[] field) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            byte[] ret = jedis.hget(key, field);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("hGet[] codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key + " field:" + field);
            }
            return ret;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return null;
        } finally {
            jedis.close();
        }
    }

    public String hGet(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            String ret = jedis.hget(key, field);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("hGet codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key + " field:" + field);
            }
            return ret;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return null;
        } finally {
            jedis.close();
        }
    }

    public List<String> hMGet(String key, String... field) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            List<String> ret = jedis.hmget(key, field);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("hMGet codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key + " field:" + field);
            }
            return ret;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return new ArrayList<>();
        } finally {
            jedis.close();
        }
    }

    public List<String> hMGet(String key, List<String> fields) {
        Jedis jedis = jedisPool.getResource();
        try {
            String[] arr = new String[fields.size()];
            int i = 0;
            for (String item : fields) {
                arr[i++] = item;
            }
            long beginTime = System.currentTimeMillis();
            List<String> ret = jedis.hmget(key, arr);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("hMGet codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key + " field:" + fields);
            }
            return ret;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return new ArrayList<>();
        } finally {
            jedis.close();
        }
    }

    public Map<String, String> hMGetAll(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            Map<String, String> ret = jedis.hgetAll(key);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("hMGetAll codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
            return ret;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return new HashMap<>();
        } finally {
            jedis.close();
        }
    }

    public void setExpireTime(String key, int seconds) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            jedis.expire(key, seconds);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("setExpireTime codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
        } finally {
            jedis.close();
        }
    }
    
    public void pexpire(String key, long milliseconds) {
        Jedis jedis = jedisPool.getResource();
    	try {
    		long beginTime = System.currentTimeMillis();
    		jedis.pexpire(key, milliseconds);
    		long diff = System.currentTimeMillis() - beginTime;
    		if (diff > 1000){
    			logger.info("setExpireTime codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
    		}
    	} catch (Throwable e) {
    		logger.error(getErrorOutPutInfo(jedis),   e);
    	} finally {
            jedis.close();
    	}
    }

    public void setPExpireTime(String key, long time) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            jedis.pexpireAt(key, time);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("setPExpireTime codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
        } finally {
            jedis.close();

        }
    }

    public String hMSet(String key, String nestedKey, String value) {
        Map<String, String> map = new HashMap<>();
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            map.put(nestedKey, value);
            String ret = jedis.hmset(key, map);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("hMSet codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
            return ret;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return null;
        } finally {
            jedis.close();
        }
    }

    public String hMSet(String key, Map<String, String> map) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            String ret = jedis.hmset(key, map);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("hMSet codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
            return ret;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return null;
        } finally {
            jedis.close();
        }
    }

    public Collection<String> hVal(String key) {
        return hMGetAll(key).values();
    }

    public long hDel(String key, List<String> fields) {
        return hDel(key, fields.toArray(new String[fields.size()]));
    }

    public long hDel(String key, String... fields) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            long ret = jedis.hdel(key, fields);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("hDel codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
            return ret;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return 0;
        } finally {
            jedis.close();

        }
    }

    public long hSet(byte[] key, byte[] field, byte[] value) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            long ret = jedis.hset(key, field, value);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("hSet[] codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
            return ret;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return 0;
        } finally {
            jedis.close();
        }
    }


    public long hSet(String key, String field, String value) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            long ret = jedis.hset(key, field, value);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("hSet codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
            return ret;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return 0;
        } finally {
            jedis.close();
        }
    }

    public Set<String> hKeys(String key) {
        Set<String> fields = new HashSet<>();
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            fields = jedis.hkeys(key);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("hKeys codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return fields;
        } finally {
            jedis.close();
        }
        return fields;
    }

    public long hLen(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            long length = jedis.hlen(key);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("hLen codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
            return length;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return 0;
        } finally {
            jedis.close();
        }
    }

    public boolean hExists(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            boolean ret = jedis.hexists(key, field);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("hExists codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
            return ret;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return false;
        } finally {
            jedis.close();
        }
    }

    public long pushListFromLeft(String key, String... members) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            long ret = jedis.lpush(key, members);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("pushListFromLeft codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
            return ret;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return 0;
        } finally {
            jedis.close();
        }
    }

    public String lSet(String key, long index, String members) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            String ret = jedis.lset(key, index, members);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("lSet codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
            return ret;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return null;
        } finally {
            jedis.close();
        }
    }

    public List<String> getRangeList(String key, long start, long end) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            List<String> retList = jedis.lrange(key, start, end);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("getRangeList codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key + " start:"+ start + " end:" + end);
            }
            return retList;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return new ArrayList<>();
        } finally {
            jedis.close();
        }
    }

    public long getLength(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            long length = jedis.llen(key);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("getLength codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
            return length;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return 0;
        } finally {
            jedis.close();
        }
    }

    public List<byte[]> getRangeList(byte[] key, int start, int end) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            List<byte[]> retList = jedis.lrange(key, start, end);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("getRangeList[] codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
            return retList;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return new ArrayList<>();
        } finally {
            jedis.close();
        }
    }

    public void lRem(String key, int count, String value) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            jedis.lrem(key, count, value);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("lRem codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
        } finally {
            jedis.close();
        }
    }

    public void lTrim(String key, int start, int end) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            jedis.ltrim(key, start, end);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("lTrim codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
        } finally {
            jedis.close();
        }
    }

    public String deleteListFromRight(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            String retList = jedis.rpop(key);
            return retList;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return null;
        } finally {
            jedis.close();
        }
    }

    public long hincrBy(String key, String field, long num) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.hincrBy(key, field, num);
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return 0;
        } finally {
            jedis.close();
        }
    }

    public double zincrby(String key, String member, double score){
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            double res = jedis.zincrby(key, score, member);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("zincrby codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
            return res;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return 0;
        } finally {
            jedis.close();
        }
    }

    public long zAdd(String key, String member, double score) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            long res = jedis.zadd(key, score, member);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("zAdd codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
            return res;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return 0;
        } finally {
            jedis.close();
        }
    }

    public long zAdd(String key, Map<String, Double> scoreMap){
        Jedis jedis = jedisPool.getResource();
        try {
            if(scoreMap == null || scoreMap.isEmpty()){
                return 0;
            }
            return jedis.zadd(key, scoreMap);
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return 0;
        } finally {
            jedis.close();
        }
    }

    public Optional<Double> zScore(String key, String member) {
        Jedis jedis = jedisPool.getResource();
        try {
            return Optional.fromNullable(jedis.zscore(key, member));
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return Optional.absent();
        } finally {
            jedis.close();
        }
    }

    public Optional<Long> zCount(String key) {
    	return zCount(key, Double.MIN_NORMAL, Double.MAX_VALUE);
    }

    public Optional<Long> zCount(String key, double min, double max) {
        Jedis jedis = jedisPool.getResource();
    	try {
    		return Optional.fromNullable(jedis.zcount(key, min, max));
    	} catch (Throwable e) {
    		logger.error(getErrorOutPutInfo(jedis),   e);
    		return Optional.absent();
    	} finally {
            jedis.close();
    	}
    }
    
    public Optional<Long> lLength(String key) {
        Jedis jedis = jedisPool.getResource();
    	try {
    		return Optional.fromNullable(jedis.llen(key));
    	} catch (Throwable e) {
    		logger.error(getErrorOutPutInfo(jedis),   e);
    		return Optional.absent();
    	} finally {
            jedis.close();
    	}
	}

    public Optional<Long> zGetRankByAsc(String key, String member) {
        Jedis jedis = jedisPool.getResource();
        try {
            return Optional.fromNullable(jedis.zrank(key, member));
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return Optional.absent();
        } finally {
            jedis.close();
        }
    }

    public Optional<Long> zGetRankByDesc(String key, String member) {
        Jedis jedis = jedisPool.getResource();
        try {
            return Optional.fromNullable(jedis.zrevrank(key, member));
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return Optional.absent();
        } finally {
            jedis.close();
        }
    }
    
    public Optional<Integer> getInt(String key) {
        Jedis jedis = jedisPool.getResource();
    	try {
            String ret = jedis.get(key);
            if(ret != null){
            	return Optional.fromNullable(Integer.valueOf(ret));
            }
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
        } finally {
            jedis.close();
        }
    	return Optional.absent();
    }

    public Optional<Long> getLong(String key) {
        Jedis jedis = jedisPool.getResource();
    	try {
    		String ret = jedis.get(key);
    		if(ret != null){
    			return Optional.fromNullable(Long.valueOf(ret));
    		}
    	} catch (Throwable e) {
    		logger.error(getErrorOutPutInfo(jedis),   e);
    	} finally {
            jedis.close();
    	}
    	return Optional.absent();
    }

    public Optional<String> getString(String key) {
        Jedis jedis = jedisPool.getResource();
    	try {
    		String ret = jedis.get(key);
    		if(ret != null){
    			return Optional.fromNullable(ret);
    		}
    	} catch (Throwable e) {
    		logger.error(getErrorOutPutInfo(jedis),   e);
    	} finally {
            jedis.close();
    	}
    	return Optional.absent();
    }

    public long zRemByRank(String key, long start, long end){
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zremrangeByRank(key, start, end);
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return 0;
        } finally {
            jedis.close();
        }
    }

    public long zRem(String key, String... members){
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrem(key, members);
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return 0;
        } finally {
            jedis.close();
        }
    }


    public Set<String> zRangeByRankAsc(String key, long start, long end){
        Set<String> ret = new LinkedHashSet<>();
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            ret = jedis.zrange(key, start, end);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("zRangeByRankAsc codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
            return ret;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return ret;
        } finally {
            jedis.close();
        }
    }
    
    public Set<String> zRangeByRankDesc(String key, long start, long end){
        Set<String> ret = new LinkedHashSet<>();
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            ret = jedis.zrevrange(key, start, end);
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("zRangeByRankDesc codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
            return ret;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return ret;
        } finally {
            jedis.close();
        }
    }

    public HashMap<String, Double> zRangByAsc(String key, long start, long end) {
        HashMap<String, Double> ret = new HashMap<>();
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            Set<Tuple> result = jedis.zrangeWithScores(key, start, end);
            if (result != null && result.size() > 0) {
                for (Tuple member : result) {
                    ret.put(member.getElement(), member.getScore());
                }
            }
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("zRangByAsc codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
            return ret;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return ret;
        } finally {
            jedis.close();
        }
    }

    /**
     * @param key
     * @param start
     * @param end
     * @return ISFSObject: key(String), score(Double)
     */
    public List<PairUtil> zRangByDesc(String key, long start, long end) {
        List<PairUtil> retList = new LinkedList<>();
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            Set<Tuple> result = jedis.zrevrangeWithScores(key, start, end);
            if (result != null && result.size() > 0) {
                for (Tuple member : result) {
                    PairUtil obj = new PairUtil(member.getElement(), String.valueOf(member.getScore()));
                    retList.add(obj);
                }
            }
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("zRangByDesc codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + key);
            }
            return retList;
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return retList;
        } finally {
            jedis.close();
        }
    }

    public void subscribe(JedisPubSub listener, List<String> channels) {
        Jedis jedis = jedisPool.getResource();
        try {
            long beginTime = System.currentTimeMillis();
            jedis.subscribe(listener, channels.toArray(new String[]{}));
            long diff = System.currentTimeMillis() - beginTime;
            if (diff > 1000){
                logger.info("getBatch codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff);
            }
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
        } finally {
            jedis.close();
        }
    }

    public long publish(String channel, String msg) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.publish(channel, msg);
        } catch (Throwable e) {
            logger.error(getErrorOutPutInfo(jedis),   e);
            return 0;
        } finally {
            jedis.close();
        }
    }

//    public void setAutoClose(boolean autoClose) {
//        this.autoClose = autoClose;
//    }
//
//    public void setMode(RedisConnectionMode mode) {
//        this.mode = mode;
//    }

//    public void setJedis(Jedis jedis) {
//        this.jedis = jedis;
//    }

//    public Map<String, String> getBatch(RedisKey redisKey, Collection<String> list) {
//        Map<String, String> result = new HashMap<>();
//        try {
//            Jedis jedis = jedisPool.getResource();
//            long beginTime = System.currentTimeMillis();
//            for (String uid : list) {
//                String v = jedis.get(redisKey.suffix(uid));
//                if (StringUtils.isNotBlank(v)) {
//                    result.put(uid, v);
//                }
//            }
//            long diff = System.currentTimeMillis() - beginTime;
//            if (diff > 1000){
//                logger.info("getBatch codis : "+ getErrorOutPutInfo(jedis) + " slow cost : " + diff + " key:" + redisKey);
//            }
//
//        } catch (Throwable e) {
//            logger.error(getErrorOutPutInfo(jedis),   e);
//            return result;
//        } finally {
//            jedis.close();
//        }
//        return result;
//    }
//
//    public static boolean isCodisOpen() {
//        return PropertyFileReader.getRealBooleanItem("codis_open", "false");
////    }
//
//    public RedisConnectionMode getMode() {
//        return mode;
//    }


    
    /**
     * Redis 锁的用法 
     * 
     *  String result = RedisSession.getGlobal(true).execByLock("myLock", new RedisLockRunner<String>() {
	 *		@Override
	 *		public String run(RedisSession session) {
	 *			String key = "someKey";
	 *			Optional<String> strVal = session.getString(key);
	 *			if(strVal.isPresent() && strVal.get().equals("new")){
	 *				return session.set(key, strVal+"-modify");
	 *			}else{
	 *				return session.set(key, "new");
	 *			}
	 *		}
	 *	});
	 *
     * @param lock
     * @param runner
     * @return
     */
    public <T> T execByLock(String lock, RedisLock.RedisLockRunner<T> runner) {
    	RedisLock<T> redisLock = new RedisLock<T>(this);
    	return redisLock.exec(lock, runner);
    }
    
    /**
     * Redis 锁的用法 
     * 
     *  String result = RedisSession.getGlobal(true).execByLock("myLock", new RedisLockRunner<String>() {
	 *      @Override
	 *	    public String run(RedisSession session) {
	 *			String key = "someKey";
	 *			Optional<String> strVal = session.getString(key);
	 *			if(strVal.isPresent() && strVal.get().equals("new")){
	 *				return session.set(key, strVal+"-modify");
	 *			}else{
	 *				return session.set(key, "new");
	 *			}
	 *		}
	 *	});
	 *	
     * @param lock
     * @param acquireTimeoutMillis  获取锁的最大等待时间  默认5秒
     * @param expiryTimeMillis		锁标记的超时时间		默认60秒
     * @param runner
     * @return
     */
    public <T> T execByLock(String lock, int acquireTimeoutMillis, int expiryTimeMillis, RedisLock.RedisLockRunner<T> runner) {
    	RedisLock<T> redisLock = new RedisLock<T>(this);
    	return redisLock.exec(lock, acquireTimeoutMillis, expiryTimeMillis, runner);
    }
    
    /**
     * 
     * execByLockAsynchronized:异步执行，避免阻塞当前线程. <br/>
     *
     * @author Administrator
     * @param lock
     * @param acquireTimeoutMillis	获取锁的最大等待时间  默认5秒
     * @param expiryTimeMillis		锁标记的超时时间		默认60秒
     * @param runner
     */
	public <T> Future<T> execByLockAsynchronized(final String lock, final int acquireTimeoutMillis,
			final int expiryTimeMillis, final RedisLock.RedisLockRunner<T> runner) {
		final RedisLock<T> redisLock = new RedisLock<T>(this);
		Future<T> future = ThreadPoolManager.execute(new Callable<T>(){
			@Override
			public T call() throws Exception {
				return redisLock.exec(lock, acquireTimeoutMillis, expiryTimeMillis, runner);
			}
		});
		return future;
	}

//	public boolean isAutoClose() {
//		return autoClose;
//	}
	
	private String getErrorOutPutInfo(Jedis jedis) {
        return "redis : " + jedis == null ? "null" : jedis.getClient().getHost();
    }
}

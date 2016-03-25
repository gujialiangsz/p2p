package com.qiushui.cache;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.RedissonClient;
import org.redisson.core.RKeys;
import org.redisson.core.RList;
import org.redisson.core.RMapCache;
import org.redisson.core.RSetCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisCache {
	private static final Logger LOG = LoggerFactory.getLogger(RedisCache.class);
	private static volatile RedisCache instance;
	/**
	 * redis客户端
	 */
	private RedissonClient redisson;

	public static RedisCache getInstance(RedissonClient client) {
		if (null == instance) {
			synchronized (RedisCache.class) {
				if (null == instance) {
					try {
						instance = new RedisCache(client);
					} catch (Exception e) {
						LOG.error("redis初始化失败：" + e.fillInStackTrace());
						try {
							if (instance != null) {
								instance.redisson.shutdown();
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						instance = new RedisCache();
					}
				}
			}
		}
		return instance;
	}

	public static RedisCache getInstance(String host, String port, Integer db,
			String password) {
		if (null == instance) {
			synchronized (RedisCache.class) {
				if (null == instance) {
					try {
						instance = new RedisCache(host, port, db, password);
					} catch (Exception e) {
						LOG.error("redis初始化失败：" + e.fillInStackTrace());
						try {
							if (instance != null) {
								instance.redisson.shutdown();
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						instance = new RedisCache(host, port, db, password);
					}
				}
			}
		}
		return instance;
	}

	public static RedisCache getInstance() {
		if (null == instance) {
			synchronized (RedisCache.class) {
				if (null == instance) {
					try {
						instance = new RedisCache();
					} catch (Exception e) {
						LOG.error("redis初始化失败：" + e.fillInStackTrace());
						try {
							if (instance != null) {
								instance.redisson.shutdown();
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						instance = new RedisCache();
					}
				}
			}
		}
		return instance;
	}

	private RedisCache(RedissonClient client) {
		this.redisson = client;
	}

	private RedisCache(String host, String port, Integer db, String password) {
		// 创建配置信息.
		try {
			LOG.info("Start MessageListener...");
			Config config = new Config();
			config.useSingleServer().setAddress(host + ":" + port)
					.setDatabase(db).setPassword(password)
					.setConnectionPoolSize(200);
			redisson = Redisson.create(config);
		} catch (Exception e) {
			LOG.error("redis初始化失败：" + e.fillInStackTrace());
			throw new RuntimeException();
		}
		LOG.info("Connect Redis Success.");
	}

	private RedisCache() {
		// 创建配置信息
		try {
			Properties p = new Properties();
			p.load(RedisCache.class.getClassLoader().getResourceAsStream(
					"ca.properties"));
			LOG.info("Start MessageListener...");
			String host = p.getProperty("redis.host");
			int port = Integer.parseInt(p.getProperty("redis.port"));
			int db = Integer.parseInt(p.getProperty("redis.db"));
			String password = p.getProperty("redis.password");
			Config config = new Config();
			// String host = PropertiesHelper.getPro("redis.host");
			// String port = PropertiesHelper.getPro("redis.port");
			// int db = Integer.valueOf(PropertiesHelper.getPro("redis.db"));
			// String password = PropertiesHelper.getPro("redis.password");
			config.useSingleServer().setAddress(host + ":" + port)
					.setDatabase(db).setPassword(password)
					.setConnectionPoolSize(200);
			redisson = Redisson.create(config);
		} catch (Exception e) {
			LOG.error("redis初始化失败：" + e.fillInStackTrace());
			throw new RuntimeException();
		}
		LOG.info("Connect Redis Success.");
	}

	/*************************************** MAP ****************************************/
	/**
	 * 缓存对象到Map
	 * 
	 * @param key
	 * @param value
	 * @description:
	 */
	public void putToMapCache(String map, String key, Object value) {
		redisson.getMapCache(map).put(key, value);
	}

	/**
	 * 缓存对象到Map
	 * 
	 * @param key
	 * @param value
	 * @param date
	 *            指定日期过期
	 * @description:
	 */
	public void putToMapCache(String map, String key, Object value, Date date) {
		redisson.getMapCache(map).put(key, value,
				System.currentTimeMillis() - date.getTime(),
				TimeUnit.MILLISECONDS);
	}

	/**
	 * 缓存对象到Map
	 * 
	 * @param key
	 * @param value
	 * @param time
	 *            指定毫秒后过期
	 * @description:
	 */
	public void putToMapCache(String map, String key, Object value, long time) {
		redisson.getMapCache(map).put(key, value, time, TimeUnit.MILLISECONDS);
	}

	/**
	 * 缓存对象到Map
	 * 
	 * @param key
	 * @param value
	 * @param time
	 *            指定毫秒后过期
	 * @param unit
	 *            指定时间单位
	 * @description:
	 */
	public void putToMapCache(String map, String key, Object value, long time,
			TimeUnit unit) {
		redisson.getMapCache(map).put(key, value, time, unit);
	}

	public boolean removeMapCache(String mapname) {
		return redisson.getMapCache(mapname).delete();
	}

	public void removeFromMapCache(String map, String key) {
		redisson.getMapCache(map).remove(key);
	}

	public RMapCache<String, Object> getMapCache(String mapname) {
		return redisson.getMapCache(mapname);
	}

	public Object getFromMapCache(String map, String key) {
		return redisson.getMapCache(map).get(key);
	}

	public boolean existInMapCache(String map, String key) {
		return redisson.getMapCache(map).containsKey(key);
	}

	/*************************************** Set ****************************************/
	/**
	 * 缓存对象到Set
	 * 
	 * @param key
	 * @param value
	 * @description:
	 */
	public void addToSetCache(String set, String key) {
		redisson.getSetCache(set).add(key);
	}

	/**
	 * 缓存对象到Set
	 * 
	 * @param key
	 * @param value
	 * @param date
	 *            指定日期过期
	 * @description:
	 */
	public void addToSetCache(String set, String key, Date date) {
		redisson.getSetCache(set).add(key,
				System.currentTimeMillis() - date.getTime(),
				TimeUnit.MILLISECONDS);
	}

	/**
	 * 缓存对象到Set
	 * 
	 * @param key
	 * @param value
	 * @param time
	 *            指定毫秒后过期
	 * @description:
	 */
	public void addToSetCache(String set, String key, long time) {
		redisson.getSetCache(set).add(key, time, TimeUnit.MILLISECONDS);
	}

	/**
	 * 缓存对象到Set
	 * 
	 * @param key
	 * @param value
	 * @param time
	 *            指定毫秒后过期
	 * @param unit
	 *            指定时间单位
	 * @description:
	 */
	public void addToSetCache(String set, String key, long time, TimeUnit unit) {
		redisson.getSetCache(set).add(key, time, unit);
	}

	public boolean removeSetCache(String set) {
		return redisson.getSetCache(set).delete();
	}

	public void removeFromSetCache(String set, String key) {
		redisson.getSetCache(set).remove(key);
	}

	public RSetCache<String> getSetCache(String set) {
		return redisson.getSetCache(set);
	}

	public boolean existInSetCache(String set, String key) {
		return redisson.getSetCache(set).contains(key);
	}

	/********************************************* LIST **************************************/
	public boolean expireList(String listname, long time) {
		return redisson.getList(listname).expireAt(
				System.currentTimeMillis() + time);
	}

	public boolean expireList(String listname, Date date) {
		return redisson.getList(listname).expireAt(date);
	}

	public boolean expireMapCache(String mapname, long time) {
		return redisson.getMapCache(mapname).expireAt(
				System.currentTimeMillis() + time);
	}

	public boolean expireMapCache(String mapname, Date date) {
		return redisson.getMapCache(mapname).expireAt(date);
	}

	/**
	 * 缓存对象到List
	 * 
	 * @param key
	 * @param value
	 * @description:
	 */
	public void addToList(String listname, Object value) {
		if (listname != null) {
			redisson.getList(listname).add(value);
		}
	}

	/**
	 * 缓存对象到List
	 * 
	 * @param key
	 * @param value
	 * @param index
	 *            指定下标号
	 * @description:
	 */
	public void addToList(String listname, Object value, int index) {
		if (listname != null) {
			redisson.getList(listname).add(index, value);
		}
	}

	/**
	 * 获取list缓存值
	 * 
	 * @param listname
	 * @param index
	 * @return
	 * @description:
	 */
	public Object getFromList(String listname, int index) {
		if (listname != null) {
			return redisson.getList(listname).get(index);
		}
		return null;
	}

	public boolean removeList(String listname) {
		return redisson.getList(listname).delete();
	}

	public boolean removeFromList(String listname, Object value) {
		return redisson.getList(listname).remove(value);
	}

	public void removeFromList(String listname, int index) {
		redisson.getList(listname).remove(index);
	}

	public RList<Object> getList(String listname) {
		return redisson.getList(listname);
	}

	public boolean getList(String listname, Object value) {
		return redisson.getList(listname).contains(value);
	}

	public RKeys getAllKeys() {
		return redisson.getKeys();
	}

	public Iterable<String> getKeys(String paramString) {
		return redisson.getKeys().getKeysByPattern(paramString);
	}

	public static void main(String[] args) {
		Properties p = new Properties();
		try {
			p.load(RedisCache.class.getClassLoader().getResourceAsStream(
					"ca.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

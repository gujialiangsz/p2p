package com.qiushui.base.db.search;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.hibernate.search.annotations.Indexed;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import com.qiushui.base.db.EntityClassBeanFactoryPostProcessor;
import com.qiushui.base.db.dao.DaoUtils;

/**
 * 全文索引组件。
 */
@Component
public class FullTextIndexer extends EntityClassBeanFactoryPostProcessor {
	private final ReentrantLock lock = new ReentrantLock();
	private List<Class<?>> indexedEntityClasses = new ArrayList<Class<?>>();

	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) {
		super.postProcessBeanFactory(beanFactory);
		for (Class<?> entityClass : entityClasses) {
			if (entityClass.isAnnotationPresent(Indexed.class)) {
				indexedEntityClasses.add(entityClass);
			}
		}
	}

	/**
	 * 同步创建指定实体类的全文索引，如果未指定实体类则创建全部实体类的全文索引。
	 * 
	 * @param entityClasses
	 *            实体类列表
	 */
	public void startAndWait(Class<?>... entityClasses) {
		lock.lock();
		try {
			for (Class<?> indexedEntityClass : entityClasses) {
				DaoUtils.getDao(indexedEntityClass).rebuildIndex(true);
			}
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 异步创建指定实体类的全文索引。
	 * 
	 * @param entityClasses
	 *            实体类列表
	 */
	public void start(Class<?>... entityClasses) {
		lock.lock();
		try {
			for (Class<?> indexedEntityClass : entityClasses) {
				DaoUtils.getDao(indexedEntityClass).rebuildIndex(false);
			}
		} finally {
			lock.unlock();
		}
	}

	public List<Class<?>> getIndexedEntityClasses() {
		return indexedEntityClasses;
	}
}
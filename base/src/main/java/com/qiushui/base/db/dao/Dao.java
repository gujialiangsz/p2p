package com.qiushui.base.db.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Cache;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.util.Assert;

import com.qiushui.base.annotations.Restriction;
import com.qiushui.base.constants.Chars;
import com.qiushui.base.db.DbType;
import com.qiushui.base.db.SqlExpression;
import com.qiushui.base.db.search.FullTextCriteria;
import com.qiushui.base.exception.BusinessException;
import com.qiushui.base.exception.UncheckedException;
import com.qiushui.base.model.Page;
import com.qiushui.base.model.SearchModel;
import com.qiushui.base.util.BeanUtils;
import com.qiushui.base.util.StringUtils;

/**
 * 泛型DAO。
 * 
 * @param <T>
 *            业务实体类型
 */
@SuppressWarnings("unchecked")
public class Dao<T> {
	private final Logger log = LoggerFactory.getLogger(getClass());
	@Resource
	private SessionFactory sessionFactory;
	private Class<T> clazz;
	private static String ALIAS = "T";
	@Resource
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * 构造方法。
	 * 
	 * @param clazz
	 *            业务实体类
	 */
	public Dao(Class<T> clazz) {
		this.clazz = clazz;
	}

	/**
	 * 获取Hibernate的Session对象。
	 * 
	 * @return 返回Hibernate的Session对象。
	 */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 获取Hibernate的全文搜索Session对象。
	 * 
	 * @return 返回Hibernate的全文搜索Session对象。
	 */
	public FullTextSession getFullTextSession() {
		return Search.getFullTextSession(getSession());
	}

	/**
	 * 根据指定的ID加载实体对象。
	 * 
	 * @param id
	 *            实体ID
	 * @return 返回指定的ID加载实体对象，如果对象不存在抛出异常。
	 */
	public T load(Serializable id) {
		return (T) getSession().load(clazz, id);
	}

	/**
	 * 根据指定的ID获取实体对象。
	 * 
	 * @param id
	 *            实体ID
	 * @return 返回指定ID的实体对象，如果没有找到则返回null。
	 */
	public T get(Serializable id) {
		return (T) getSession().get(clazz, id);
	}

	/**
	 * 持久化实体对象。
	 * 
	 * @param entity
	 *            待持久化实体对象
	 */
	public void persist(T entity) {
		getSession().persist(entity);
	}

	/**
	 * 保存或更新实体对象。
	 * 
	 * @param entity
	 *            待保存实体对象
	 */
	public void save(T entity) {
		getSession().saveOrUpdate(entity);
	}

	/**
	 * 插入实体
	 * 
	 * @param entity
	 * @description:
	 */
	public void insert(T entity) {
		getSession().save(entity);
	}

	/**
	 * 更新实体对象。
	 * 
	 * @param entity
	 *            待更新实体对象
	 */
	public void update(T entity) {
		getSession().update(entity);
	}

	/**
	 * 合并实体对象。
	 * 
	 * @param entity
	 *            待更新实体对象
	 * @return 返回更新后的实体对象（持久状态的）
	 */
	public T merge(T entity) {
		return (T) getSession().merge(entity);
	}

	/**
	 * 保存实体对象。（复用已有的ID键值时使用）
	 * 
	 * @param entity
	 *            待保存的实体对象
	 */
	public void replicate(T entity) {
		getSession().replicate(entity, ReplicationMode.EXCEPTION);
	}

	/**
	 * 删除实体对象。
	 * 
	 * @param entity
	 *            待删除实体对象
	 */
	public void remove(T entity) {
		getSession().delete(entity);
	}

	/**
	 * 根据ID删除实体对象。
	 * 
	 * @param id
	 *            待删除实体对象ID
	 */
	public void remove(Serializable id) {
		remove(get(id));
	}

	/**
	 * 根据ID批量删除实体对象。
	 * 
	 * @param ids
	 *            待删除实体对象ID数组
	 */
	public void remove(Serializable[] ids) {
		for (Serializable id : ids) {
			remove(id);
		}
	}

	/**
	 * 删除多个实体对象。
	 * 
	 * @param entitys
	 *            待删除的实体对象列表
	 */
	public void remove(List<T> entitys) {
		for (T entity : entitys) {
			remove(entity);
		}
	}

	/**
	 * 根据属性批量删除实体对象
	 * 
	 * @param name
	 *            属性名
	 * @param value
	 *            属性值
	 */
	public void removeBy(String name, Object value) {
		Query query = createQuery("delete from " + clazz.getName() + " where "
				+ name + "=?", value);
		query.executeUpdate();
	}

	/**
	 * 清理当前Session。
	 */
	public void clear() {
		getSession().clear();
	}

	public T findFirst(Criterion... res) {
		List<T> list = find(res);
		return (list == null || list.size() == 0) ? null : list.get(0);
	}

	public T findFirst(String name, Object value) {
		List<T> list = findBy(name, value);
		return (list == null || list.size() == 0) ? null : list.get(0);
	}

	public T findFirst(String name, Object value, String orderby, boolean isasc) {
		List<T> list = findBy(name, value, orderby, isasc);
		return (list == null || list.size() == 0) ? null : list.get(0);
	}

	/**
	 * 创建一个绑定实体类型的条件查询对象。
	 * 
	 * @param criterions
	 *            查询条件
	 * @return 返回一个条件查询对象。
	 */
	public Criteria createCriteria(Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(clazz, ALIAS);
		Cache cache = clazz.getAnnotation(Cache.class);
		if (cache != null)
			criteria.setCacheable(true);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 创建一个SQL查询对象。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param values
	 *            参数值
	 * @return 返回一个SQL查询对象。
	 */
	public Query createSQLQuery(String sql, Object... values) {
		Query query = getSession().createSQLQuery(sql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query;
	}

	/**
	 * 创建一个绑定实体并设定了排序的条件查询对象。
	 * 
	 * @param orderBy
	 *            排序属性
	 * @param isAsc
	 *            是否升序
	 * @param criterions
	 *            查询条件
	 * @return 返回一个已设定排序的条件查询对象。
	 */
	public Criteria createCriteria(String orderBy, Boolean isAsc,
			Criterion... criterions) {
		Criteria criteria = createCriteria(criterions);
		if (isAsc) {
			criteria.addOrder(Order.asc(orderBy));
		} else {
			criteria.addOrder(Order.desc(orderBy));
		}
		return criteria;
	}

	/**
	 * 获取指定类型的所有实体对象。
	 * 
	 * @return 返回指定类型的所有实体对象。
	 */
	public List<T> getAll() {
		Criteria criteria = createCriteria();
		return criteria.list();
	}

	/**
	 * 获取指定类型的所有实体对象并进行排序。
	 * 
	 * @param orderBy
	 *            排序的属性名
	 * @param isAsc
	 *            是否升序
	 * @return 返回排序后的指定类型的所有实体对象。
	 */
	public List<T> getAll(String orderBy, Boolean isAsc) {
		Criteria criteria = createCriteria(orderBy, isAsc);
		return criteria.list();
	}

	/**
	 * 获取某个字段最大值
	 * 
	 * @param property
	 * @param conditions
	 * @return
	 * @description:
	 */
	public <X> X getMax(String property, Criterion... conditions) {
		return (X) createCriteria(conditions).setProjection(
				Projections.max(property)).uniqueResult();
	}

	/**
	 * 获取某个字段最小值
	 * 
	 * @param property
	 * @param conditions
	 * @return
	 * @description:
	 */
	public <X> X getMin(String property, Criterion... conditions) {
		return (X) createCriteria(conditions).setProjection(
				Projections.min(property)).uniqueResult();
	}

	/**
	 * 根据属性的值查找实体对象。
	 * 
	 * @param name
	 *            属性名
	 * @param value
	 *            属性值
	 * @return 返回属性值相符的实体对象集合，如果没有找到返回一个空的集合。
	 */
	public List<T> findBy(String name, Object value) {
		Criteria criteria = createCriteria();
		if (value == null) {
			criteria.add(Restrictions.isNull(name));
		} else {
			criteria.add(Restrictions.eq(name, value));
		}
		return criteria.list();
	}

	/**
	 * 根据属性的值查找实体对象并进行排序。
	 * 
	 * @param name
	 *            属性名
	 * @param value
	 *            属性值
	 * @param orderBy
	 *            排序属性
	 * @param isAsc
	 *            是否升序
	 * @return 返回排序后的属性值相符的实体对象集合，如果没有找到返回一个空的集合。
	 */
	public List<T> findBy(String name, Object value, String orderBy,
			boolean isAsc) {
		Criteria criteria = createCriteria(orderBy, isAsc);
		if (value == null) {
			criteria.add(Restrictions.isNull(name));
		} else {
			criteria.add(Restrictions.eq(name, value));
		}
		return criteria.list();
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	public <X> List<X> find(final String hql, final Object... values) {
		return createQuery(hql, values).list();
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	public <X> List<X> find(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).list();
	}

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	public <X> X findUnique(final String hql, final Object... values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	public <X> X findUnique(final String hql, final Map<String, ?> values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 * @return 更新记录数.
	 */
	public int batchExecute(final String hql, final Object... values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 * @return 更新记录数.
	 */
	public int batchExecute(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	public Query createQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		Cache cache = clazz.getAnnotation(Cache.class);
		if (cache != null)
			query.setCacheable(true);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	public Query createQuery(final String queryString,
			final Map<String, ?> values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		Cache cache = clazz.getAnnotation(Cache.class);
		if (cache != null)
			query.setCacheable(true);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	/**
	 * 按Criteria查询对象列表.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 */
	public List<T> find(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}

	/**
	 * 按Criteria查询唯一对象.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 */
	public T findUnique(final Criterion... criterions) {
		return (T) createCriteria(criterions).uniqueResult();
	}

	/**
	 * 判断是否存在属性重复的实体对象。
	 * 
	 * @param entity
	 *            待判断的实体对象
	 * @param propNames
	 *            属性名，可以多个属性名用","分割
	 * @return 如果存在重复的实体对象返回false，否则返回true。
	 */
	public Boolean isUnique(T entity, String propNames) {
		Criteria criteria = createCriteria().setProjection(
				Projections.rowCount());
		String[] nameList = propNames.split(Chars.COMMA);
		try {
			for (String name : nameList) {
				Object var = null;
				if (name.contains(Chars.DOT)) {
					String[] fnames = name.split("\\.");
					int i = 0;
					var = entity;
					while (i < fnames.length) {
						var = BeanUtils.getField(var, fnames[i]);
						i++;
					}
				} else {
					var = BeanUtils.getField(entity, name);
				}
				criteria.add(Restrictions.eq(name, var));
			}
			// 更新实体类时应该排除自身
			String idName = getIdName();
			Serializable id = getId(entity);
			if (id != null) {
				criteria.add(Restrictions.not(Restrictions.eq(idName, id)));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return Integer.parseInt(criteria.uniqueResult().toString()) == 0;
	}

	/**
	 * 根据属性的值查找唯一的实体对象。
	 * 
	 * @param name
	 *            属性名
	 * @param value
	 *            属性值
	 * @return 返回指定唯一的实体对象，如果没有找到则返回null。
	 */
	public T findUnique(String name, Object value) {
		Criteria criteria = createCriteria(Restrictions.eq(name, value));
		return (T) criteria.uniqueResult();
	}

	/**
	 * 根据HQL查询语句进行分页查询。
	 * 
	 * @param hql
	 *            HQL查询语句
	 * @param pageNo
	 *            待获取的页数
	 * @param pageSize
	 *            每页的记录数
	 * @param totalCount
	 *            总记录数
	 * @param values
	 *            参数值
	 * @return 返回查询得到的分页对象。
	 */
	public Page<T> findPage(String hql, Integer pageNo, Integer pageSize,
			Integer totalCount, Object... values) {
		if (totalCount < 1) {
			return new Page<T>(pageSize);
		}

		Page<T> page = new Page<T>(totalCount, pageNo, pageSize);
		Query query = createQuery(hql, values);

		List<T> list = query.setFirstResult((page.getNumber() - 1) * pageSize)
				.setMaxResults(pageSize).list();
		page.setContents(list);
		return page;
	}

	/**
	 * 根据HQL查询语句进行分页查询。
	 * 
	 * @param hql
	 *            HQL查询语句
	 * @param pageNo
	 *            待获取的页数
	 * @param pageSize
	 *            每页的记录数
	 * @param values
	 *            参数值
	 * @return 返回查询得到的分页对象。
	 */
	public Page<T> findPage(String hql, Integer pageNo, Integer pageSize,
			Object... values) {
		return findPage(hql, pageNo, pageSize, countHqlResult(hql, values),
				values);
	}

	/**
	 * 根据HQL查询语句进行分页查询，会有n+1问题。
	 * 
	 * @param model
	 *            搜索属性封装model
	 * @return 返回查询得到的分页对象。
	 */
	public Page<T> findPage(SearchModel model) {
		StringBuilder hql = new StringBuilder("from " + clazz.getName()
				+ " where 1=1");
		Class<?> modelClass = model.getClass();
		Restriction annotation = modelClass.getAnnotation(Restriction.class);
		Field[] fiels = modelClass.getDeclaredFields();
		List<Object> olist = new ArrayList<Object>();
		try {
			for (Field f : fiels) {
				f.setAccessible(true);
				Restriction fa = f.getAnnotation(Restriction.class);
				if (fa == null) {
					if (annotation == null)
						continue;
					else {
						Object o = f.get(model);
						if (o != null && StringUtils.hasText(o.toString())) {
							hql.append(" and " + f.getName()
									+ SqlExpression.EQ.getValue() + "?");
							olist.add(o);
						}
					}
				} else {
					SqlExpression exp = fa.type();
					String pname = fa.propertyName();
					String preffix = fa.propertyPreffix();
					String suffix = fa.propertySuffix();
					String realPname = StringUtils.hasText(pname) ? pname : f
							.getName();
					Object o = f.get(model);
					if (o != null && StringUtils.hasText(o.toString())) {
						hql.append(" and " + realPname + exp.getValue() + "?");
						o = StringUtils.hasText(preffix) ? preffix + o : o;
						o = StringUtils.hasText(suffix) ? o + suffix : o;
						olist.add(o);
					}
				}
			}
			Field orderfield = SearchModel.class.getDeclaredField("orderBy");
			Field sortfield = SearchModel.class.getDeclaredField("sort");
			orderfield.setAccessible(true);
			sortfield.setAccessible(true);
			String orderBy = (String) orderfield.get(model);
			String sort = (String) sortfield.get(model);
			if (StringUtils.hasText(orderBy)) {
				String[] orderbys = orderBy.split(",");
				String[] sorts = sort.split(",");
				int j = 0;
				hql.append(" order by ");
				for (int i = 0; i < orderbys.length; i++) {
					if (i < sorts.length) {
						j = i;
					}
					if (i == orderbys.length - 1)
						hql.append(orderbys[i] + " " + sorts[j]);
					else
						hql.append(orderbys[i] + " " + sorts[j] + ",");
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("SearchModel read error:"
					+ e.getMessage());
		}
		return findPage(hql.toString(), model.getPageNo(), model.getPageSize(),
				olist.toArray());
	}

	/**
	 * 根据条件查询对象进行分页查询。
	 * 
	 * @param criteria
	 *            条件查询对象
	 * @param pageNo
	 *            待获取的页数
	 * @param pageSize
	 *            每页的记录数
	 * @param totalCount
	 *            总记录数
	 * @return 返回查询得到的分页对象。
	 */
	public Page<T> findPage(Criteria criteria, Integer pageNo,
			Integer pageSize, Integer totalCount) {
		if (totalCount < 1) {
			return new Page<T>(pageSize);
		}

		Page<T> page = new Page<T>(totalCount, pageNo, pageSize);
		List<T> list = criteria
				.setFirstResult((page.getNumber() - 1) * pageSize)
				.setMaxResults(pageSize).list();
		page.setContents(list);
		return page;
	}

	/**
	 * 根据条件查询当前类型对象进行分页查询。
	 * 
	 * @param criteria
	 *            条件查询对象
	 * @param pageNo
	 *            待获取的页数
	 * @param pageSize
	 *            每页的记录数
	 * @param totalCount
	 *            总记录数
	 * @return 返回查询得到的分页对象。
	 */
	public Page<T> findPage(Integer pageNo, Integer pageSize,
			Integer totalCount, Criterion... criterions) {
		if (totalCount < 1) {
			return new Page<T>(pageSize);
		}
		Criteria cr = getSession().createCriteria(clazz);
		if (criterions != null) {
			for (Criterion c : criterions) {
				cr.add(c);
			}
		}
		Page<T> page = new Page<T>(totalCount, pageNo, pageSize);
		List<T> list = cr.setFirstResult((page.getNumber() - 1) * pageSize)
				.setMaxResults(pageSize).list();
		page.setContents(list);
		return page;
	}

	/**
	 * 根据条件查询对象进行分页查询。
	 * 
	 * @param criteria
	 *            条件查询对象
	 * @param pageNo
	 *            待获取的页数
	 * @param pageSize
	 *            每页的记录数
	 * @return 返回查询得到的分页对象。
	 */
	public Page<T> findPage(Criteria criteria, Integer pageNo, Integer pageSize) {
		return findPage(criteria, pageNo, pageSize,
				countCriteriaResult(criteria));
	}

	/**
	 * 创建全文搜索查询对象。
	 * 
	 * @return 返回全文搜索查询对象。
	 */
	public FullTextCriteria createFullTextCriteria() {
		return new FullTextCriteria(getFullTextSession(), clazz);
	}

	/**
	 * 根据全文搜索查询对象进行全文搜索。
	 * 
	 * @param fullTextCriteria
	 *            全文搜索查询对象
	 * @return 返回符合查询条件的实体对象列表。
	 */
	public List<T> searchBy(FullTextCriteria fullTextCriteria) {
		return fullTextCriteria.generateQuery().list();
	}

	/**
	 * 根据全文搜索查询对象进行分页全文搜索。
	 * 
	 * @param fullTextCriteria
	 *            全文搜索查询对象
	 * @param pageNo
	 *            待获取的页数
	 * @param pageSize
	 *            每页的记录数
	 * @return 返回搜索得到的分页对象。
	 */
	public Page<T> searchPage(FullTextCriteria fullTextCriteria,
			Integer pageNo, Integer pageSize) {
		FullTextQuery fullTextQuery = fullTextCriteria.generateQuery();
		int total = 0;
		try {
			// 当实体对应数据库中没有记录，其索引文件未生成时该方法会抛出异常
			// 这里捕捉后忽略该异常
			total = fullTextQuery.getResultSize();
		} catch (Exception e) {
			log.warn("实体[" + clazz + "]全文索引文件尚未生成。", e);
		}
		if (total < 1) {
			return new Page<T>(pageSize);
		}

		Page<T> page = new Page<T>(total, pageNo, pageSize);
		fullTextQuery.setFirstResult((page.getNumber() - 1) * pageSize)
				.setMaxResults(pageSize);
		List<T> result = fullTextQuery.list();
		page.setContents(result);
		return page;
	}

	/**
	 * 重建全文索引。
	 * 
	 * @param sync
	 *            是否同步创建
	 */
	public void rebuildIndex(Boolean sync) {
		try {
			log.info("开始重建[{}]全文索引...", clazz.getSimpleName());
			Long startTime = System.currentTimeMillis();
			if (sync) {
				getFullTextSession().createIndexer(clazz).startAndWait();
			} else {
				getFullTextSession().createIndexer(clazz).start();
			}
			Long endTime = System.currentTimeMillis();
			log.info("完成重建[{}]全文索引...耗时[{}]毫秒。", clazz.getSimpleName(), endTime
					- startTime);
		} catch (Exception e) {
			throw new UncheckedException("重建全文索引时发生异常。", e);
		}
	}

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数。<br/>
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询。
	 * 
	 * @param hql
	 *            查询语句
	 * @param values
	 *            查询参数
	 * @return 返回查询结果总数
	 */
	public Integer countHqlResult(String hql, Object... values) {
		String fromHql = hql;
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");
		String countHql = "select count(*) " + fromHql;
		int count = Integer.parseInt(createQuery(countHql, values)
				.uniqueResult().toString());
		return count;
	}

	/**
	 * 执行count查询获得本次Criteria查询所能获得的对象总数。
	 * 
	 * @param criteria
	 *            查询对象
	 * @return 返回查询结果总数
	 */
	public Integer countCriteriaResult(Criteria criteria) {
		CriteriaImpl impl = (CriteriaImpl) criteria;
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		Field orderEntriesField = BeanUtils.findField(CriteriaImpl.class,
				"orderEntries");
		List<CriteriaImpl.OrderEntry> orderEntries = (List<CriteriaImpl.OrderEntry>) BeanUtils
				.getField(impl, orderEntriesField);
		BeanUtils.setField(impl, orderEntriesField,
				new ArrayList<CriteriaImpl.OrderEntry>());

		int totalCount = Integer.parseInt(criteria
				.setProjection(Projections.rowCount()).uniqueResult()
				.toString());

		criteria.setProjection(projection);
		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			criteria.setResultTransformer(transformer);
		}
		BeanUtils.setField(impl, orderEntriesField, orderEntries);

		return totalCount;
	}

	/**
	 * 执行count查询获得记录总数。
	 * 
	 * @return 返回记录总数。
	 */
	public Integer count() {
		Criteria criteria = createCriteria();
		return Integer.parseInt(criteria.setProjection(Projections.rowCount())
				.uniqueResult().toString());
	}

	/**
	 * 获取实体类的主键值。
	 * 
	 * @param entity
	 *            实体对象
	 * @return 返回实体类的主键值。
	 */
	private Serializable getId(T entity) {
		return (Serializable) BeanUtils.getField(entity, getIdName());
	}

	/**
	 * 获取实体类的主键名。
	 * 
	 * @return 返回实体类的主键名。
	 */
	private String getIdName() {
		ClassMetadata meta = sessionFactory.getClassMetadata(clazz);
		return meta.getIdentifierPropertyName();
	}

	/**
	 * 分页查询sql转换
	 * 
	 * @param page
	 * @param type
	 * @param sql
	 * @return
	 */
	public String toPageSql(Page<T> page, DbType type, String sql) {
		if (DbType.ORACLE.equals(type)) {
			sql = "select * from (select t.*,rownum rownum_ from ( " + sql
					+ " ) t ) tt where tt.rownum_ > " + (page.getNumber() - 1)
					* page.getSize() + " and tt.rownum_ <= " + page.getNumber()
					* page.getSize();
		} else if (DbType.MYSQL.equals(type)) {
			sql = "select * from (" + sql + ") t limit "
					+ (page.getNumber() - 1) * page.getSize() + ","
					+ page.getSize();
		} else if (DbType.DB2.equals(type)) {
			sql = "select * from (select t.*,rownumber() over(order by 0 asc) as rowid from("
					+ sql
					+ ") t) tt where tt.rowid>"
					+ (page.getNumber() - 1)
					* page.getSize()
					+ " and tt.rowid<= "
					+ page.getNumber()
					* page.getSize();
		} else if (DbType.MSSQL.equals(type)) {
			sql = "select * from (select t.*,row_number() over(order by 0 asc) as rowid from("
					+ sql
					+ ") t) tt where tt.rowid>"
					+ (page.getNumber() - 1)
					* page.getSize()
					+ " and tt.rowid<= "
					+ page.getNumber()
					* page.getSize();
		}
		return sql;
	}

	/**
	 * 带悲观锁查询集合
	 * 
	 * @param lock
	 * @param cris
	 *            注意，必须用到索引，否则锁全表
	 * @return
	 */
	public List<T> getListByLock(LockMode lock, Criterion... cris) {
		Criteria cri = getSession().createCriteria(clazz);
		cri.setLockMode(lock);
		for (Criterion c : cris) {
			cri.add(c);
		}
		return cri.list();
	}

	/**
	 * 带悲观锁查询唯一值
	 * 
	 * @param lock
	 * @param c
	 *            注意，必须用到索引，否则锁全表
	 * @return
	 */
	public T getUniqueByLock(LockMode lock, Criterion c) {
		Criteria cri = getSession().createCriteria(clazz);
		cri.setLockMode(lock);
		cri.add(c);
		return (T) cri.uniqueResult();
	}

	/**
	 * 用id带悲观锁加载对象
	 * 
	 * @param lock
	 * @param id
	 *            ID
	 * @return
	 */
	public T getUniqueByLock(LockOptions lock, Serializable id) {
		return (T) getSession().load(clazz, id, lock);
	}

	/**
	 * jdbc方式更新，非预编译方式
	 * 
	 * @param sql
	 */
	public void executeSql(String sql) {
		jdbcTemplate.execute(sql);
	}

	/**
	 * jdbc方式更新，预编译方式
	 * 
	 * @param sql
	 */
	public int executeUpdate(String sql, Object... objects) {
		return jdbcTemplate.update(sql, objects);
	}

	/**
	 * jdbc方式更新，预编译方式
	 * 
	 * @param paramVal
	 *            键值对
	 */
	public int executeUpdate(String[] set, String[] where,
			List<Object[]> paramVal) {
		if (paramVal != null && paramVal.size() > 0 && set != null
				&& set.length > 0) {
			StringBuilder sql = new StringBuilder("UPDATE " + getTableName()
					+ " ");
			StringBuilder setsql = new StringBuilder(" SET ");
			StringBuilder wheresql = new StringBuilder();
			for (String s : set) {
				setsql.append(s + "=?,");
			}
			setsql.deleteCharAt(setsql.length() - 1);
			if (where != null && where.length > 0) {
				wheresql.append(" WHERE ");
				for (String s : where) {
					wheresql.append(s + "=?,");
				}
				wheresql.deleteCharAt(wheresql.length() - 1);
			}
			sql.append(setsql).append(wheresql);
			return jdbcTemplate.update(sql.toString(), paramVal);
		}
		return 0;
	}

	/**
	 * jdbc方式批量操作，非预编译方式
	 * 
	 * @param sql
	 */
	public int[] executeBatch(String[] sql) {
		return jdbcTemplate.batchUpdate(sql);
	}

	/**
	 * 插入一个实体
	 * 
	 * @param entity
	 * @param mode
	 * @return
	 * @description:
	 */
	public int insert(T entity, InsertMode mode) {
		return jdbcTemplate.update(getInsertSql(mode), getParamArray(entity));
	}

	/**
	 * 批量插入实体数组
	 * 
	 * @param list
	 * @param mode
	 * @return
	 * @description:
	 */
	public int[] insert(List<T> list, InsertMode mode) {
		List<Object[]> params = new ArrayList<Object[]>();
		for (T t : list) {
			params.add(getParamArray(t));
		}
		return jdbcTemplate.batchUpdate(getInsertSql(mode), params);
	}

	/**
	 * 获取一个实体的属性值作为参数列表
	 * 
	 * @param entity
	 * @return
	 * @description:
	 */
	public Object[] getParamArray(T entity) {
		Field[] fields = clazz.getDeclaredFields();
		List<Object> params = new ArrayList<Object>();
		try {
			if (fields == null || fields.length == 0)
				throw new BusinessException("不存在可插入的列");
			Arrays.sort(fields, new Comparator<Field>() {
				@Override
				public int compare(Field o1, Field o2) {
					if (o1 == null)
						return -1;
					else if (o2 == null)
						return 1;
					return o1.getName().compareTo(o2.getName());
				}
			});
			for (Field f : fields) {
				f.setAccessible(true);
				if (Modifier.isStatic(f.getModifiers())
						|| f.getAnnotation(Transient.class) != null
						|| f.getAnnotation(JoinColumn.class) != null) {
					continue;
				}
				params.add(f.get(entity));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("获取字段值失败");
		}
		return params.toArray();
	}

	/**
	 * 从实体获取表名称
	 * 
	 * @return
	 * @description:
	 */
	public String getTableName() {
		Table table = clazz.getAnnotation(Table.class);
		String tablename;
		if (table != null && !table.name().isEmpty())
			tablename = table.name();
		else
			tablename = clazz.getSimpleName();
		return tablename;
	}

	/**
	 * 获取当前泛型所指表对应模式的插入SQL预编译语句
	 * 
	 * @param mode
	 * @return
	 * @description:
	 */
	public String getInsertSql(InsertMode mode) {
		String tablename = getTableName();
		StringBuffer sql = new StringBuffer();
		StringBuffer tail = new StringBuffer(" VALUES" + Chars.OPEN_SBRACKET);
		if (InsertMode.IGNORE.equals(mode)) {
			sql.append("INSERT IGNORE INTO " + tablename + Chars.OPEN_SBRACKET);
		} else if (InsertMode.REPLACE.equals(mode)) {
			sql.append("REPLACE INTO " + tablename + Chars.OPEN_SBRACKET);
		} else {
			sql.append("INSERT INTO " + tablename + Chars.OPEN_SBRACKET);
		}
		Field[] fields = clazz.getDeclaredFields();
		Arrays.sort(fields, new Comparator<Field>() {
			@Override
			public int compare(Field o1, Field o2) {
				if (o1 == null)
					return -1;
				else if (o2 == null)
					return 1;
				return o1.getName().compareTo(o2.getName());
			}
		});
		if (fields == null || fields.length == 0)
			throw new BusinessException("不存在可插入的列");
		for (Field f : fields) {
			f.setAccessible(true);
			if (Modifier.isStatic(f.getModifiers())
					|| f.getAnnotation(Transient.class) != null
					|| f.getAnnotation(JoinColumn.class) != null)
				continue;
			sql.append(Chars.SMALL_STOP + f.getName() + Chars.SMALL_STOP);
			sql.append(Chars.COMMA);
			tail.append(Chars.QUESTION + Chars.COMMA);
		}
		sql.deleteCharAt(sql.length() - 1);
		tail.deleteCharAt(tail.length() - 1);
		tail.append(Chars.CLOSE_SBRACKET);
		sql.append(Chars.CLOSE_SBRACKET);
		sql.append(tail.toString());
		return sql.toString();
	}

	/**
	 * jdbc方式批量操作，预编译方式
	 * 
	 * @param sql
	 */
	public int[] executeBatch(String sql, List<Object[]> param) {
		return jdbcTemplate.batchUpdate(sql, param);
	}

	/**
	 * jdbc方式计算总行数
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public Integer queryCount(String sql, Object... args) {
		return jdbcTemplate.queryForObject(
				"select count(*) from (" + sql + ")", args, Integer.class);
	}

	/**
	 * jdbc方式查询获得int值
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public Integer queryForInt(String sql, Object... args) {
		return jdbcTemplate.queryForObject(sql, args, Integer.class);
	}

	/**
	 * jdbc方式查询获得long值
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public Long queryForLong(String sql, Object... args) {
		return jdbcTemplate.queryForObject(sql, args, Long.class);
	}

	/**
	 * jdbc方式查询获得唯一值
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public T queryUnique(String sql, Object... args) {
		T t = jdbcTemplate.queryForObject(sql, args,
				new BeanPropertyRowMapper<T>(clazz));
		return t;
	}

	/**
	 * jdbc方式查询集合
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public List<T> queryList(String sql, Object... args) {
		List<T> list = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<T>(clazz));
		return list;
	}

	/**
	 * jdbc方式查询分页
	 * 
	 * @param sql
	 * @param page
	 * @param args
	 * @return
	 */
	public Page<T> queryPage(String sql, Page<T> page, Object... args) {
		if (page.getCount() <= 1 && page.isAutoCount()) {
			page.setCount(queryForInt("select count(*) from (" + sql + ")",
					args, Integer.class));
		}
		List<T> list = jdbcTemplate.query(toPageSql(page, DbType.ORACLE, sql),
				args, new BeanPropertyRowMapper<T>(clazz));
		page.setContents(list);
		return page;
	}

	/**
	 * 执行无返回结果的存储过程
	 * 
	 * @param sql
	 * @param args
	 */
	public void executeCall(String sql, final Object... args) {
		jdbcTemplate.execute(sql, new PreparedStatementCallback<T>() {
			@Override
			public T doInPreparedStatement(PreparedStatement ps)
					throws SQLException, DataAccessException {
				if (args != null) {
					for (int i = 0; i < args.length; i++) {
						ps.setObject(i + 1, args[i]);
					}
				}
				ps.execute();
				return null;
			}
		});
	}

	/**
	 * 执行有返回值存储过程
	 * 
	 * @param sql
	 * @param param
	 * @param type
	 * @return
	 */
	public List<Map<String, Object>> executeCallBackList(final String sql,
			final Object[] param, final int[] type) {
		List<Map<String, Object>> resultList = jdbcTemplate.execute(
				new CallableStatementCreator() {
					public CallableStatement createCallableStatement(
							Connection con) throws SQLException {
						CallableStatement cs = con.prepareCall(sql);
						int i = 0;
						for (; i < param.length; i++) {
							cs.setObject(i + 1, param[i]);
						}
						for (; i < param.length + type.length; i++) {
							cs.registerOutParameter(i + 1, type[i
									- param.length]);
						}
						return cs;
					}
				}, new CallableStatementCallback<List<Map<String, Object>>>() {
					public List<Map<String, Object>> doInCallableStatement(
							CallableStatement cs) throws SQLException,
							DataAccessException {
						List<Map<String, Object>> resultsMap = new ArrayList<Map<String, Object>>();
						cs.execute();
						ResultSet rs = cs.getResultSet();
						ResultSetMetaData meta = rs.getMetaData();
						while (rs.next()) {
							Map<String, Object> map = new HashMap<String, Object>();
							for (int i = 0; i < meta.getColumnCount(); i++) {
								map.put(meta.getColumnName(i + 1),
										rs.getObject(i + 1));
							}
							resultsMap.add(map);
						}
						rs.close();
						return resultsMap;
					}
				});
		return resultList;
	}

	public enum InsertMode {
		IGNORE, REPLACE, ONUPDATE;
	}
}

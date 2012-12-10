package edu.vanderbilt.cqs.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public abstract class GenericDAOImpl<T extends Serializable, Id extends Serializable>
		implements GenericDAO<T, Id> {
	@Autowired
	private SessionFactory sessionFactory;

	private Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public GenericDAOImpl() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	public Session getSession() {
		return getSessionFactory().getCurrentSession();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public T findById(Id id) {
		return findById(id, false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findById(Id id, boolean lock) {
		T entity;

		if (lock) {
			entity = (T) getSession().get(getPersistentClass(), id,
					LockOptions.UPGRADE);
		} else {
			entity = (T) getSession().get(getPersistentClass(), id);
		}

		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findListByName(String tablecolumnname, String name) {
		Criteria criteria = getSession().createCriteria(persistentClass);
		criteria.add(Restrictions.eq(tablecolumnname, name));

		List<T> objectList = criteria.list();
		return objectList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findInstanceByName(String tablecolumnname, String name) {
		Criteria criteria = getSession().createCriteria(persistentClass);
		criteria.add(Restrictions.eq(tablecolumnname, name));
		T object = (T) criteria.uniqueResult();
		return object;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findListByNamedInteger(String tablecolumnname, Integer name) {
		Criteria criteria = getSession().createCriteria(persistentClass);
		criteria.add(Restrictions.eq(tablecolumnname, new Integer(name)));
		List<T> objectList = criteria.list();
		return objectList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findListByNamedDouble(String tablecolumnname, Double name) {
		Criteria criteria = getSession().createCriteria(persistentClass);
		criteria.add(Restrictions.eq(tablecolumnname, new Double(name)));
		List<T> objectList = criteria.list();
		return objectList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findInstanceByNamedInteger(String tablecolumnname, Integer name) {
		Criteria criteria = getSession().createCriteria(persistentClass);
		criteria.add(Restrictions.eq(tablecolumnname, new Integer(name)));
		T objectList = (T) criteria.uniqueResult();
		return objectList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByQuery(String query) {
		List<T> listObjects = getSession().createQuery(query).list();
		return listObjects;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		Criteria criteria = getSession().createCriteria(persistentClass);
		return criteria.list();
	}

	@Override
	public T makePersistent(T entity) {
		getSession().saveOrUpdate(entity);
		return entity;
	}

	@Override
	public void makeTransient(T entity) {
		getSession().delete(entity);
	}

	@Override
	public void refresh(T entity) {
		getSession().refresh(entity);
	}

	@Override
	public T delete(T entity) {
		getSession().delete(entity);
		return entity;
	}

	@Override
	public void deleteById(Id id) {
		T entity = findById(id, false);
		if (null != entity) {
			delete(entity);
		}
	}

	@Override
	public T saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
		return entity;
	}

	@Override
	public T save(T entity) {
		getSession().save(entity);
		return entity;
	}

	@Override
	public T update(T entity) {
		getSession().update(entity);
		return entity;
	}

	@Override
	public T merge(T entity) {
		getSession().merge(entity);
		return entity;
	}

	@Override
	public void persist(Object obj) {
		getSession().persist(obj);
	}

	@Override
	public boolean isEmpty() {
		return count() == 0;
	}

	@Override
	public long count() {
		Criteria criteria = getSession().createCriteria(persistentClass);
		criteria.setProjection(Projections.rowCount());
		return ((Long) criteria.list().get(0)).longValue();
	}
}

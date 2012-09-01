package edu.vanderbilt.cqs.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T extends Serializable, Id extends Serializable> {
	T findById(Id id, boolean lock);

	List<T> findListByName(String tablecolumn, String name);

	T findInstanceByName(String tablecolumn, String name);

	List<T> findListByNamedInteger(String tablecolumnname, Integer name);

	List<T> findListByNamedDouble(String tablecolumnname, Double name);

	T findInstanceByNamedInteger(String tablecolumnname, Integer name);

	List<T> findByQuery(String query);

	List<T> findAll();

	T makePersistent(T entity);

	void makeTransient(T entity);

	void refresh(T entity);

	T delete(T entity);

	void deleteById(Id id);
	
	T saveOrUpdate(T entity);

	T save(T entity);

	T update(T entity);

	T merge(T entity);

	void persist(Object obj);
	
	long count();
	
	boolean isEmpty();
}

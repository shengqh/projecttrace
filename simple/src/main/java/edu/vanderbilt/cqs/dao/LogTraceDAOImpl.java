package edu.vanderbilt.cqs.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import edu.vanderbilt.cqs.bean.LogTrace;

@Repository
public class LogTraceDAOImpl extends GenericDAOImpl<LogTrace, Long> implements
		LogTraceDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<LogTrace> listAll() {
		Criteria criteria = getSession().createCriteria(getPersistentClass())
				.addOrder(Order.desc("logDate"));
		return criteria.list();
	}
}

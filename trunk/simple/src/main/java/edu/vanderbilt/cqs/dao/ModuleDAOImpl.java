package edu.vanderbilt.cqs.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import edu.vanderbilt.cqs.bean.Module;

@Repository
public class ModuleDAOImpl extends GenericDAOImpl<Module, Long> implements
		ModuleDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Module> findByTechnology(Long technologyId) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("technology.id", technologyId));

		List<Module> objectList = criteria.list();
		return objectList;
	}
}

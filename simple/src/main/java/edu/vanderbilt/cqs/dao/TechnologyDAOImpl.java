package edu.vanderbilt.cqs.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import edu.vanderbilt.cqs.bean.Technology;

@Repository
public class TechnologyDAOImpl extends GenericDAOImpl<Technology, Long>
		implements TechnologyDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Technology> listValidTechnology() {
		String hql = "from Technology where (enabled=:enabled) order by name";
		Query query = getSession().createQuery(hql);
		query.setParameter("enabled", true);
		return (List<Technology>) query.list();
	}

	@Override
	public Technology findByName(String name) {
		return (Technology) (getSession().createCriteria(getPersistentClass())
				.add(Restrictions.eq("name", name)).uniqueResult());
	}
}

package edu.vanderbilt.cqs.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import edu.vanderbilt.cqs.Role;
import edu.vanderbilt.cqs.bean.Project;

@Repository
public class ProjectDAOImpl extends GenericDAOImpl<Project, Long> implements
		ProjectDAO {
	@SuppressWarnings("unchecked")
	@Override
	public List<Project> getProjectByUser(Long userid) {
		String sql = "select distinct(PROJECT_ID) from PROJECT_USER where USER_ID=:id";
		Query qry = getSession().createSQLQuery(sql).addScalar("PROJECT_ID",
				StandardBasicTypes.LONG);
		qry.setLong("id", userid);
		List<Long> ids = qry.list();
		if (ids.size() == 0) {
			return new ArrayList<Project>();
		} else {
			Criteria criteria = getSession().createCriteria(
					getPersistentClass()).add(Restrictions.in("id", ids));
			return criteria.list();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getPermission(Long userid, Long projectid) {
		String sql = "select max(PERMISSION) from PROJECT_USER where USER_ID=:userid and PROJECT_ID=:projectid";
		Query qry = getSession().createSQLQuery(sql).addScalar("PERMISSION",
				StandardBasicTypes.INTEGER);
		qry.setLong("userid", userid);
		qry.setLong("projectid", projectid);
		List<Integer> lst = qry.list();
		if (lst.size() > 0) {
			return lst.get(0);
		}
		return Role.NONE;
	}
}

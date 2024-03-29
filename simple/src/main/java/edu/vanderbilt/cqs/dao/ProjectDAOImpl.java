package edu.vanderbilt.cqs.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import edu.vanderbilt.cqs.UserType;
import edu.vanderbilt.cqs.bean.Project;

@Repository
public class ProjectDAOImpl extends GenericDAOImpl<Project, Long> implements
		ProjectDAO {
	@SuppressWarnings("unchecked")
	@Override
	public List<Project> listProjectByUser(Long userid, String orderBy,
			Boolean ascending) {
		Order order = ascending ? Order.asc(orderBy) : Order.desc(orderBy);
		if (userid == null || userid.longValue() <= 0) {
			Criteria criteria = getSession().createCriteria(
					getPersistentClass()).addOrder(order);
			return criteria.list();
		} else {
			String sql = "select distinct(PROJECT_ID) from PROJECT_USER where USER_ID=:id";
			Query qry = getSession().createSQLQuery(sql).addScalar(
					"PROJECT_ID", StandardBasicTypes.LONG);
			qry.setLong("id", userid);
			List<Long> ids = qry.list();

			if (ids.size() == 0) {
				return new ArrayList<Project>();
			} else {
				Criteria criteria = getSession()
						.createCriteria(getPersistentClass())
						.add(Restrictions.in("id", ids)).addOrder(order);
				return criteria.list();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getUserType(Long userid, Long projectid) {
		String sql = "select max(USERTYPE) as MAXPERMISSION from PROJECT_USER where USER_ID=:userid and PROJECT_ID=:projectid";
		Query qry = getSession().createSQLQuery(sql).addScalar("MAXPERMISSION",
				StandardBasicTypes.INTEGER);
		qry.setLong("userid", userid);
		qry.setLong("projectid", projectid);
		List<Integer> lst = qry.list();
		if (lst.size() > 0) {
			return lst.get(0);
		}
		return UserType.NONE;
	}
}

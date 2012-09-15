package edu.vanderbilt.cqs.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import edu.vanderbilt.cqs.Role;
import edu.vanderbilt.cqs.Status;
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
		String sql = "select max(PERMISSION) as MAXPERMISSION from PROJECT_USER where USER_ID=:userid and PROJECT_ID=:projectid";
		Query qry = getSession().createSQLQuery(sql).addScalar("MAXPERMISSION",
				StandardBasicTypes.INTEGER);
		qry.setLong("userid", userid);
		qry.setLong("projectid", projectid);
		List<Integer> lst = qry.list();
		if (lst.size() > 0) {
			return lst.get(0);
		}
		return Role.NONE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateStatus(Long projectid) {
		String sql = "select min(STATUS) as MINSTATUS from PROJECTTASK where PROJECT_ID=:projectid";
		Query qry = getSession().createSQLQuery(sql).addScalar("MINSTATUS",
				StandardBasicTypes.INTEGER);
		qry.setLong("projectid", projectid);
		List<Integer> lst = qry.list();

		Integer status;
		if (lst.size() == 0) {
			status = Status.PENDING;
		} else {
			status = lst.get(0);
		}

		String updateSql = "update PROJECT set STATUS=:status where ID=:projectid";
		Query updateQry = getSession().createSQLQuery(updateSql);
		updateQry.setInteger("status", status);
		updateQry.setLong("projectid", projectid);
		updateQry.executeUpdate();
	}
}

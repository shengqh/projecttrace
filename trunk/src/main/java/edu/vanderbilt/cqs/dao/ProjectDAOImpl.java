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
	@Override
	public void clearTask(Project project) {
		getSession().persist(project.getTasks());
		project.getTasks().clear();
		getSession().save(project);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Project> getProjectByUser(Long userid) {
		String sql = "select distinct(p.id) from PROJECT p, PROJECT_MANAGER as m, PROJECT_USER as u, PROJECT_OBSERVER as o where (m.USER_ID=:id and p.id=m.PROJECT_ID) or (u.USER_ID=:id and p.id=u.PROJECT_ID) or (o.USER_ID=:id and p.id=o.PROJECT_ID)";
		Query qry = getSession().createSQLQuery(sql).addScalar("ID",
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

	@Override
	public Integer getPermission(Long userid, Long projectid) {
		if (hasEntry("PROJECT_MANAGER", projectid, userid)) {
			return Role.MANAGER;
		}

		if (hasEntry("PROJECT_USER", projectid, userid)) {
			return Role.USER;
		}

		if (hasEntry("PROJECT_OBSERVER", projectid, userid)) {
			return Role.OBSERVER;
		}

		return Role.NONE;
	}

	private boolean hasEntry(String table, Long projectid, Long userid) {
		String sql = String
				.format("select count(*) as c from %s as m where m.PROJECT_ID=:pid and m.USER_ID=:uid",
						table);
		Query qry = getSession().createSQLQuery(sql).addScalar("c",
				StandardBasicTypes.LONG);
		qry.setLong("pid", projectid);
		qry.setLong("uid", userid);
		Long count = (Long) qry.uniqueResult();
		return count > 0;
	}

	private void removeUserEntry(String table, Long userid) {
		String sql = String
				.format("delete from %s where USER_ID=:uid",
						table);
		Query qry = getSession().createSQLQuery(sql);
		qry.setLong("uid", userid);
		qry.executeUpdate();
	}

	@Override
	public void removeUserEntry(Long userid) {
		removeUserEntry("PROJECT_MANAGER", userid);
		removeUserEntry("PROJECT_USER", userid);
		removeUserEntry("PROJECT_OBSERVER", userid);
	}
}

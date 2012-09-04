package edu.vanderbilt.cqs.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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
		Criteria criteria = getSession()
				.createCriteria(getPersistentClass())
				.createAlias("managers", "m")
				.createAlias("users", "u")
				.createAlias("observers", "o")
				.add(Restrictions.or(Restrictions.or(
						Restrictions.eq("m.id", userid),
						Restrictions.eq("u.id", userid)), Restrictions.eq(
						"o.id", userid)));

		return criteria.list();
	}

	@Override
	public Integer getPermission(Long userid, Long projectid) {
		Criteria criteria;

		criteria = getSession().createCriteria(getPersistentClass())
				.createAlias("managers", "m")
				.add(Restrictions.eq("m.id", userid))
				.add(Restrictions.eq("id", projectid))
				.setProjection(Projections.rowCount());
		if (((Long) criteria.list().get(0)).longValue() > 0) {
			return Role.MANAGER;
		}

		criteria = getSession().createCriteria(getPersistentClass())
				.createAlias("users", "m").add(Restrictions.eq("m.id", userid))
				.add(Restrictions.eq("id", projectid))
				.setProjection(Projections.rowCount());
		if (((Long) criteria.list().get(0)).longValue() > 0) {
			return Role.USER;
		}

		criteria = getSession().createCriteria(getPersistentClass())
				.createAlias("observers", "m")
				.add(Restrictions.eq("m.id", userid))
				.add(Restrictions.eq("id", projectid))
				.setProjection(Projections.rowCount());

		if (((Long) criteria.list().get(0)).longValue() > 0) {
			return Role.OBSERVER;
		}

		return Role.NONE;
	}
}

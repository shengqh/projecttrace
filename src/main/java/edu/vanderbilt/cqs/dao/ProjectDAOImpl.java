package edu.vanderbilt.cqs.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

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
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.createAlias("managers", "m").createAlias("users", "u")
				.createAlias("observers", "o");
		criteria.add(Restrictions.or(
				Restrictions.or(Restrictions.eq("m.id", userid),
						Restrictions.eq("u.id", userid)),
				Restrictions.eq("o.id", userid)));

		List<Project> objectList = criteria.list();
		return objectList;
	}
}

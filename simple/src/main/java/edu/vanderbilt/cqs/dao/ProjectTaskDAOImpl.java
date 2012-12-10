package edu.vanderbilt.cqs.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import edu.vanderbilt.cqs.bean.ProjectTask;

@Repository
public class ProjectTaskDAOImpl extends GenericDAOImpl<ProjectTask, Long>
		implements ProjectTaskDAO {

	@Override
	public Long findProjecIdByTaskId(Long taskid) {
		String sql = "Select m.project.id From ProjectTask as m where m.id=:taskid";
		Query query = getSession().createQuery(sql);
		query.setLong("taskid", taskid);
		return (Long) query.uniqueResult();
	}
}

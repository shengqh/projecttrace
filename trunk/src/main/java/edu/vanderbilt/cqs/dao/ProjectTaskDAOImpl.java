package edu.vanderbilt.cqs.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import edu.vanderbilt.cqs.bean.ProjectTask;

@Repository
public class ProjectTaskDAOImpl extends GenericDAOImpl<ProjectTask, Integer>
		implements ProjectTaskDAO {

	@Override
	public Integer findProjecIdByTaskId(Integer taskid) {
		String sql = "Select m.project.id From ProjectTask as m where m.id=:taskid";
		Query query = getSession().createQuery(sql);
		query.setInteger("taskid", taskid);
		return (Integer) query.uniqueResult();
	}
}

package edu.vanderbilt.cqs.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import edu.vanderbilt.cqs.bean.PipelineTask;

@Repository
public class PipelineTaskDAOImpl extends GenericDAOImpl<PipelineTask, Integer>
		implements PipelineTaskDAO {
	@Override
	public Integer findPipelineIdByTaskId(Integer taskid) {
		String sql = "Select m.pipeline.id From PipelineTask as m where m.id=:taskid";
		Query query = getSession().createQuery(sql);
		query.setInteger("taskid", taskid);
		return (Integer) query.uniqueResult();
	}
}

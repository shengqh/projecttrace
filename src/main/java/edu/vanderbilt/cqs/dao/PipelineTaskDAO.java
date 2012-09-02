package edu.vanderbilt.cqs.dao;

import edu.vanderbilt.cqs.bean.PipelineTask;


public interface PipelineTaskDAO extends GenericDAO<PipelineTask, Long> {
	Long findPipelineIdByTaskId(Long taskid);
}

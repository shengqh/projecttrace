package edu.vanderbilt.cqs.dao;

import edu.vanderbilt.cqs.bean.PipelineTask;


public interface PipelineTaskDAO extends GenericDAO<PipelineTask, Integer> {
	Integer findPipelineIdByTaskId(Integer taskid);
}

package edu.vanderbilt.cqs.dao;

import edu.vanderbilt.cqs.bean.ProjectTask;

public interface ProjectTaskDAO extends GenericDAO<ProjectTask, Integer> {
	Integer findProjecIdByTaskId(Integer taskid);
}

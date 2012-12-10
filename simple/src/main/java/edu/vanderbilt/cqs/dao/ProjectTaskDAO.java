package edu.vanderbilt.cqs.dao;

import edu.vanderbilt.cqs.bean.ProjectTask;

public interface ProjectTaskDAO extends GenericDAO<ProjectTask, Long> {
	Long findProjecIdByTaskId(Long taskid);
}

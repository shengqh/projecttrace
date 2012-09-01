package edu.vanderbilt.cqs.dao;

import java.util.List;

import edu.vanderbilt.cqs.bean.Project;

public interface ProjectDAO extends GenericDAO<Project, Integer> {
	public void clearTask(Project project);
	public List<Project> getProjectByUser(Integer userid);
}

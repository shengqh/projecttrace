package edu.vanderbilt.cqs.dao;

import java.util.List;

import edu.vanderbilt.cqs.bean.Project;

public interface ProjectDAO extends GenericDAO<Project, Long> {
	public void clearTask(Project project);
	
	public List<Project> getProjectByUser(Long userid);
	
	Integer getPermission(Long userid, Long projectid);
}

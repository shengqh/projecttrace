package edu.vanderbilt.cqs.dao;

import java.util.List;

import edu.vanderbilt.cqs.bean.Project;

public interface ProjectDAO extends GenericDAO<Project, Long> {
	List<Project> getProjectByUser(Long userid);
	
	Integer getUserType(Long userid, Long projectid);
}

package edu.vanderbilt.cqs.dao;

import java.util.List;

import edu.vanderbilt.cqs.bean.ProjectTechnologyModule;

public interface ProjectTechnologyModuleDAO extends
		GenericDAO<ProjectTechnologyModule, Long> {
	void assignModulePrice(Long projectId);

	List<ProjectTechnologyModule> getModuleInProject(Long projectId);
}

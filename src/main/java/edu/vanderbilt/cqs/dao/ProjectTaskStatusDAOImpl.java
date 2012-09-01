package edu.vanderbilt.cqs.dao;

import org.springframework.stereotype.Repository;

import edu.vanderbilt.cqs.bean.ProjectTaskStatus;

@Repository
public class ProjectTaskStatusDAOImpl extends GenericDAOImpl<ProjectTaskStatus, Integer>
		implements ProjectTaskStatusDAO {
}

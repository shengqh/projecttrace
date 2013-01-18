package edu.vanderbilt.cqs.dao;

import org.springframework.stereotype.Repository;

import edu.vanderbilt.cqs.bean.ProjectFile;

@Repository
public class ProjectFileDAOImpl extends GenericDAOImpl<ProjectFile, Long>
		implements ProjectFileDAO {
}

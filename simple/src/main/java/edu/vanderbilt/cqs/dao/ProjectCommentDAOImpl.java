package edu.vanderbilt.cqs.dao;

import org.springframework.stereotype.Repository;

import edu.vanderbilt.cqs.bean.ProjectComment;

@Repository
public class ProjectCommentDAOImpl extends GenericDAOImpl<ProjectComment, Long>
		implements ProjectCommentDAO {
}

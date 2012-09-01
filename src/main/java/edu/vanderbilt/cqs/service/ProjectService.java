package edu.vanderbilt.cqs.service;

import java.util.List;

import edu.vanderbilt.cqs.bean.Pipeline;
import edu.vanderbilt.cqs.bean.PipelineTask;
import edu.vanderbilt.cqs.bean.Project;
import edu.vanderbilt.cqs.bean.ProjectTask;
import edu.vanderbilt.cqs.bean.User;

public interface ProjectService {
	// User
	void addUser(User user);

	void updateUser(User user);

	User validateUser(User user);

	List<User> listUser();

	void removeUser(Integer id);

	// Project
	Project findProject(Integer id);

	void addProject(Project project);

	void updateProject(Project project);

	List<Project> listProject(User currentUser);

	void removeProject(Integer id);

	// Pipeline
	Pipeline findPipeline(Integer id);

	void addPipeline(Pipeline pipeline);

	void updatePipeline(Pipeline pipeline);

	List<Pipeline> listPipeline(User currentUser);

	void removePipeline(Integer id);

	// PipelineTask
	PipelineTask findPipelineTask(Integer id);

	void addPipelineTask(PipelineTask task);

	void updatePipelineTask(PipelineTask task);

	void removePipelineTask(Integer id);

	Integer findPipelineByTask(Integer taskid);

	// ProjectTask
	ProjectTask findProjectTask(Integer id);

	void addProjectTask(ProjectTask task);

	void updateProjectTask(ProjectTask task);

	void removeProjectTask(Integer id);

	Integer findProjectByTask(Integer taskid);

	// Other
	void persist(Object obj);

	void applyPipeline(Project project, Pipeline pipeline, int count);
	
	boolean hasUser();
}

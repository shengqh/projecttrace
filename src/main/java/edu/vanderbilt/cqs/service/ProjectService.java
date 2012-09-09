package edu.vanderbilt.cqs.service;

import java.util.List;

import edu.vanderbilt.cqs.bean.Pipeline;
import edu.vanderbilt.cqs.bean.PipelineTask;
import edu.vanderbilt.cqs.bean.Project;
import edu.vanderbilt.cqs.bean.ProjectTask;
import edu.vanderbilt.cqs.bean.ProjectTaskStatus;
import edu.vanderbilt.cqs.bean.User;

public interface ProjectService {
	// User
	void addUser(User user);

	void updateUser(User user);

	void removeUser(Long id);

	User findUser(Long id);

	User findUserByEmail(String email);

	// Project
	Project findProject(Long id);

	void addProject(Project project);

	void updateProject(Project project);

	List<Project> listProject(Long userid, Integer userRole);

	void removeProject(Long id);

	// Pipeline
	Pipeline findPipeline(Long id);

	void addPipeline(Pipeline pipeline);

	void updatePipeline(Pipeline pipeline);

	List<Pipeline> listPipeline(User currentUser);

	void removePipeline(Long id);

	// PipelineTask
	PipelineTask findPipelineTask(Long id);

	void addPipelineTask(PipelineTask task);

	void updatePipelineTask(PipelineTask task);

	void removePipelineTask(Long id);

	Long findPipelineByTask(Long taskid);

	// ProjectTask
	ProjectTask findProjectTask(Long id);

	void addProjectTask(ProjectTask task, ProjectTaskStatus status);

	void updateProjectTask(ProjectTask task, ProjectTaskStatus status);

	void removeProjectTask(Long id);

	Long findProjectByTask(Long taskid);

	// Other
	void persist(Object obj);

	boolean hasUser();

	boolean hasUser(Long id);

	void updatePassword(Long id, String newPassword);

	List<User> listValidUser();

	List<User> listValidUser(Integer role);

	List<User> listInvalidUser();
	
	Integer getPermission(Long userid, Integer userRole, Long projectid);
}

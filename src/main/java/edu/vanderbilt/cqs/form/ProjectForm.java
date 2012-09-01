package edu.vanderbilt.cqs.form;

import java.io.Serializable;
import java.util.List;

import edu.vanderbilt.cqs.bean.Pipeline;
import edu.vanderbilt.cqs.bean.Project;
import edu.vanderbilt.cqs.bean.User;

public class ProjectForm implements Serializable {

	private static final long serialVersionUID = -4361402280964698358L;

	private Boolean canEdit;

	private Project project;
	
	private List<User> allUsers;

	private List<Integer> managers;

	private List<Integer> users;
	
	private List<Integer> observers;

	private List<Pipeline> allPipelines;
	
	private Integer pipeline;
	
	private List<Project> projectList;

	public Boolean getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(Boolean canEdit) {
		this.canEdit = canEdit;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Project> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
	}

	public List<User> getAllUsers() {
		return allUsers;
	}

	public void setAllUsers(List<User> allUsers) {
		this.allUsers = allUsers;
	}

	public List<Integer> getManagers() {
		return managers;
	}

	public void setManagers(List<Integer> managers) {
		this.managers = managers;
	}

	public List<Integer> getUsers() {
		return users;
	}

	public void setUsers(List<Integer> users) {
		this.users = users;
	}

	public List<Integer> getObservers() {
		return observers;
	}

	public void setObservers(List<Integer> observers) {
		this.observers = observers;
	}

	public List<Pipeline> getAllPipelines() {
		return allPipelines;
	}

	public void setAllPipelines(List<Pipeline> allPipelines) {
		this.allPipelines = allPipelines;
	}

	public Integer getPipeline() {
		return pipeline;
	}

	public void setPipeline(Integer pipeline) {
		this.pipeline = pipeline;
	}

}

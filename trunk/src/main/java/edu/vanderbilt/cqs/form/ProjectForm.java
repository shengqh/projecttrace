package edu.vanderbilt.cqs.form;

import java.io.Serializable;
import java.util.List;

import edu.vanderbilt.cqs.bean.Project;
import edu.vanderbilt.cqs.bean.User;

public class ProjectForm implements Serializable {

	private static final long serialVersionUID = -4361402280964698358L;

	private Project project;
	
	private List<User> allUsers;

	private List<Integer> managers;

	private List<Integer> users;
	
	private List<Integer> observers;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
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
}

package edu.vanderbilt.cqs.form;

import java.io.Serializable;
import java.util.List;

import edu.vanderbilt.cqs.bean.Project;
import edu.vanderbilt.cqs.bean.User;

public class ProjectForm implements Serializable {

	private static final long serialVersionUID = -4361402280964698358L;

	private Project project;

	private List<User> managers;

	private List<User> users;

	private List<User> observers;

	private List<Long> managerIds;

	private List<Long> userIds;

	private List<Long> observerIds;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<User> getObservers() {
		return observers;
	}

	public void setObservers(List<User> observers) {
		this.observers = observers;
	}

	public List<Long> getManagerIds() {
		return managerIds;
	}

	public void setManagerIds(List<Long> managerIds) {
		this.managerIds = managerIds;
	}

	public List<Long> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}

	public List<Long> getObserverIds() {
		return observerIds;
	}

	public void setObserverIds(List<Long> observerIds) {
		this.observerIds = observerIds;
	}

	public List<User> getManagers() {
		return managers;
	}

	public void setManagers(List<User> managers) {
		this.managers = managers;
	}
}

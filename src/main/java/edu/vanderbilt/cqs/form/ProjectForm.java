package edu.vanderbilt.cqs.form;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import edu.vanderbilt.cqs.bean.User;

public class ProjectForm implements Serializable {

	public List<User> getValidManagers() {
		return validManagers;
	}

	public void setValidManagers(List<User> validManagers) {
		this.validManagers = validManagers;
	}

	public List<User> getValidUsers() {
		return validUsers;
	}

	public void setValidUsers(List<User> validUsers) {
		this.validUsers = validUsers;
	}

	public List<User> getValidObservers() {
		return validObservers;
	}

	public void setValidObservers(List<User> validObservers) {
		this.validObservers = validObservers;
	}

	private static final long serialVersionUID = -4361402280964698358L;

	private Long id;

	@NotEmpty
	private String name;

	private String description;

	private List<User> validManagers;

	private List<User> validUsers;

	private List<User> validObservers;

	private List<Long> managerIds;

	private List<Long> userIds;

	private List<Long> observerIds;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

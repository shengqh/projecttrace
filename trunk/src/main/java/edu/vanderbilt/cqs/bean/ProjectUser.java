package edu.vanderbilt.cqs.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PROJECT_USER")
public class ProjectUser {

	@Id
	@GeneratedValue
	@Column(name="ID")
	private Long id;

	@Column(name = "PERMISSION", updatable = false)
	private Integer permission;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	private Project project;

	public ProjectUser() {
	}

	public ProjectUser(Project project, User user, Integer permission) {
		this.project = project;
		this.user = user;
		this.setPermission(permission);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public boolean equals(Object instance) {
		if (instance == null)
			return false;

		if (!(instance instanceof ProjectUser))
			return false;

		ProjectUser other = (ProjectUser) instance;
		if (!(user.getId().equals(other.getUser().getId())))
			return false;

		if (!(project.getId().equals(other.getProject().getId())))
			return false;

		if (!(permission.equals(other.getPermission())))
			return false;

		return true;
	}

	public int hashcode() {
		int hash = 7;
		hash = 47 * hash
				+ (this.user != null ? this.user.getId().intValue() : 0);
		hash = 47 * hash
				+ (this.project != null ? this.project.getId().intValue() : 0);
		hash = 47 * hash + this.permission;
		return hash;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPermission() {
		return permission;
	}

	public void setPermission(Integer permission) {
		this.permission = permission;
	}

}
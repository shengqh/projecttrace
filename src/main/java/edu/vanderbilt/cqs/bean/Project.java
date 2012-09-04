package edu.vanderbilt.cqs.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import edu.vanderbilt.cqs.Utils;

@Entity
@Table(name = "PROJECT")
public class Project implements Serializable {

	private static final long serialVersionUID = -5677749261455648256L;

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String name;

	@Column(length = 1000)
	private String description;

	@Column
	private String creator;

	@Column
	private Date createDate;
	
	@Column
	private Boolean deleted = false;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinTable(name = "PROJECT_MANAGER", joinColumns = @JoinColumn(name = "PROJECT_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"))
	private List<User> managers;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinTable(name = "PROJECT_USER", joinColumns = @JoinColumn(name = "PROJECT_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"))
	private List<User> users;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinTable(name = "PROJECT_OBSERVER", joinColumns = @JoinColumn(name = "PROJECT_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"))
	private List<User> observers;

	@OneToMany(mappedBy = "project")
	@OrderBy("taskIndex")
	private List<ProjectTask> tasks;

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

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<User> getManagers() {
		return managers;
	}

	public void setManagers(List<User> managers) {
		this.managers = managers;
	}

	public void setManagers(Collection<User> managers) {
		if (managers == null) {
			this.managers = new ArrayList<User>();
		} else {
			this.managers = new ArrayList<User>(managers);
		}
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<ProjectTask> getTasks() {
		return tasks;
	}

	public void setTasks(List<ProjectTask> tasks) {
		this.tasks = tasks;
	}

	public double getPeopleTime() {
		return Utils.getTotalPeopleTime(getTasks());
	}

	public double getMachineTime() {
		return Utils.getTotalMachineTime(getTasks());
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<User> getObservers() {
		return observers;
	}

	public void setObservers(List<User> observers) {
		this.observers = observers;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
}

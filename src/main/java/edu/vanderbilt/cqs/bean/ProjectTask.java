package edu.vanderbilt.cqs.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import edu.vanderbilt.cqs.Status;

@Entity
@Table(name = "PROJECTTASK")
public class ProjectTask implements ITask, Serializable {

	private static final long serialVersionUID = -7161836652862415065L;

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String name;

	@Column
	private Integer taskIndex;

	@Column
	private Double peopleTime;

	@Column
	private Double machineTime;

	@Column
	private Integer status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	private Project project;

	public String getStatusString() {
		return Status.getStatusMap().get(this.status);
	}

	@Column
	private String updateUser;

	@Column
	private Date updateDate;

	@OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private List<ProjectTaskStatus> statuses = new ArrayList<ProjectTaskStatus>();

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

	public Double getPeopleTime() {
		return peopleTime;
	}

	public void setPeopleTime(Double peopleTime) {
		this.peopleTime = peopleTime;
	}

	public Double getMachineTime() {
		return machineTime;
	}

	public void setMachineTime(Double machineTime) {
		this.machineTime = machineTime;
	}

	public Integer getTaskIndex() {
		return taskIndex;
	}

	public void setTaskIndex(Integer taskIndex) {
		this.taskIndex = taskIndex;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<ProjectTaskStatus> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<ProjectTaskStatus> statuses) {
		this.statuses = statuses;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}

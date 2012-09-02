package edu.vanderbilt.cqs.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PROJECTTASK")
public class ProjectTask implements ITask, Serializable {

	private static final long serialVersionUID = -7161836652862415065L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "TASK_INDEX")
	private Integer taskIndex;

	@Column(name = "PEOPLE_TIME")
	private Double peopleTime;

	@Column(name = "MACHINE_TIME")
	private Double machineTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	private Project project;

	// /0:open, 1:finished
	@Column(name = "LAST_STATUS")
	private Integer lastStatus = 0;

	@Column(name = "LAST_USER")
	private User lastUser;

	@Column(name = "LAST_DATE")
	private Date lastDate;

	@OneToMany(mappedBy = "task")
	private List<ProjectTaskStatus> statuses;

	public ProjectTask() {
	}

	public ProjectTask(PipelineTask source, int count) {
		this.name = source.getName();
		this.taskIndex = source.getTaskIndex();
		this.machineTime = source.getMachineTime() * count;
		this.peopleTime = source.getPeopleTime() * count;
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

	public Integer getLastStatus() {
		return lastStatus;
	}

	public void setLastStatus(Integer lastStatus) {
		this.lastStatus = lastStatus;
	}

	public User getLastUser() {
		return lastUser;
	}

	public void setLastUser(User lastUser) {
		this.lastUser = lastUser;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public List<ProjectTaskStatus> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<ProjectTaskStatus> statuses) {
		this.statuses = statuses;
	}

}

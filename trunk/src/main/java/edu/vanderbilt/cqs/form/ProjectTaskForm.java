package edu.vanderbilt.cqs.form;

import java.io.Serializable;
import java.util.Map;

public class ProjectTaskForm implements Serializable {
	private static final long serialVersionUID = -570206787990960165L;

	private Long projectId;

	private String name;

	private Long id;

	private Integer taskIndex;

	private Double peopleTime;

	private Double machineTime;

	private Integer status;

	private Map<Integer, String> statusMap;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Map<Integer, String> getStatusMap() {
		return statusMap;
	}

	public void setStatusMap(Map<Integer, String> statusMap) {
		this.statusMap = statusMap;
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

	public Integer getTaskIndex() {
		return taskIndex;
	}

	public void setTaskIndex(Integer taskIndex) {
		this.taskIndex = taskIndex;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}

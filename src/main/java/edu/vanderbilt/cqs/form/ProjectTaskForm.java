package edu.vanderbilt.cqs.form;

import java.io.Serializable;
import java.util.List;

import edu.vanderbilt.cqs.bean.ProjectTask;

public class ProjectTaskForm implements Serializable {

	private static final long serialVersionUID = -570206787990960165L;

	private ProjectTask task;

	private Long projectId;

	private List<String> statusList;

	public ProjectTask getTask() {
		return task;
	}

	public void setTask(ProjectTask task) {
		this.task = task;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public List<String> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}

}

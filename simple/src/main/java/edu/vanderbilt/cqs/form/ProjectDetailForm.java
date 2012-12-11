package edu.vanderbilt.cqs.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.vanderbilt.cqs.Status;
import edu.vanderbilt.cqs.bean.Project;
import edu.vanderbilt.cqs.bean.ProjectTaskStatus;

public class ProjectDetailForm implements Serializable {

	private static final long serialVersionUID = 4318158107014019347L;

	private Project project;

	private Integer userType;

	private String[] statuses;

	private Long taskId;

	private List<ProjectTaskStatus> comments = new ArrayList<ProjectTaskStatus>();

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<ProjectTaskStatus> getComments() {
		return comments;
	}

	public void setComments(List<ProjectTaskStatus> statuses) {
		this.comments = statuses;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String[] getStatuses() {
		return statuses;
	}

	public void setStatuses(String[] statuses) {
		this.statuses = statuses;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Map<String, String> getStatusMap() {
		return Status.getStatusMap();
	}
}

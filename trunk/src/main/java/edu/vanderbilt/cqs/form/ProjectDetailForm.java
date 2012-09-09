package edu.vanderbilt.cqs.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.vanderbilt.cqs.bean.Project;
import edu.vanderbilt.cqs.bean.ProjectTaskStatus;

public class ProjectDetailForm implements Serializable {

	private static final long serialVersionUID = 4318158107014019347L;

	private Project project;

	private Boolean canManage;

	private Boolean canEdit;
	
	private Map<Integer, String> statusMap;
	
	private Long taskId;
	
	private List<ProjectTaskStatus> comments = new ArrayList<ProjectTaskStatus>();
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Boolean getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(Boolean canEdit) {
		this.canEdit = canEdit;
	}

	public Boolean getCanManage() {
		return canManage;
	}

	public void setCanManage(Boolean canManage) {
		this.canManage = canManage;
	}

	public Map<Integer, String> getStatusMap() {
		return statusMap;
	}

	public void setStatusMap(Map<Integer, String> statusMap) {
		this.statusMap = statusMap;
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

}

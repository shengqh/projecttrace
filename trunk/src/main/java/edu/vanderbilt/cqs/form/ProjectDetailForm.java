package edu.vanderbilt.cqs.form;

import java.io.Serializable;

import edu.vanderbilt.cqs.bean.Project;

public class ProjectDetailForm implements Serializable {

	private static final long serialVersionUID = 4318158107014019347L;

	private Project project;

	private Boolean canManage;

	private Boolean canEdit;

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

}

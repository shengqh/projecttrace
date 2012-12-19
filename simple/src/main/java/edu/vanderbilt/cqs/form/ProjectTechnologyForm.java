package edu.vanderbilt.cqs.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.vanderbilt.cqs.bean.ProjectTechnology;
import edu.vanderbilt.cqs.bean.Technology;

public class ProjectTechnologyForm implements Serializable {
	public ProjectTechnology getTechnology() {
		return technology;
	}

	public void setTechnology(ProjectTechnology technology) {
		this.technology = technology;
	}

	private static final long serialVersionUID = -570206787990960165L;

	private Long projectId;
	
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	private ProjectTechnology technology;

	private Technology reference;

	private List<Long> modules = new ArrayList<Long>();

	public List<Long> getModules() {
		return modules;
	}

	public void setModules(List<Long> modules) {
		this.modules = modules;
	}

	public Technology getReference() {
		return reference;
	}

	public void setReference(Technology reference) {
		this.reference = reference;
	}
}

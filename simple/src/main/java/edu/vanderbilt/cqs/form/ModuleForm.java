package edu.vanderbilt.cqs.form;

import java.util.List;

import edu.vanderbilt.cqs.bean.ProjectTechnologyModule;

public class ModuleForm {
	private List<ProjectTechnologyModule> modules;

	public List<ProjectTechnologyModule> getModules() {
		return modules;
	}

	public void setModules(List<ProjectTechnologyModule> modules) {
		this.modules = modules;
	}
	
	private Long projectId;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	private String estimatedPrice = "0";

	public String getEstimatedPrice() {
		return estimatedPrice;
	}

	public void setEstimatedPrice(String estimatedPrice) {
		this.estimatedPrice = estimatedPrice;
	}
}

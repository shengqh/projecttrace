package edu.vanderbilt.cqs.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;

import edu.vanderbilt.cqs.bean.ProjectCostCenter;

public class ProjectCostCenterForm {
	private Long projectId;
	
	@SuppressWarnings("unchecked")
	private List<ProjectCostCenter> costCenters = LazyList.decorate(new ArrayList<ProjectCostCenter>(), FactoryUtils.instantiateFactory(ProjectCostCenter.class));

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public List<ProjectCostCenter> getCostCenters() {
		return costCenters;
	}
}

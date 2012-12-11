package edu.vanderbilt.cqs.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PROJECTTECHNOLOGYMODULE")
public class ProjectTechnologyModule implements Serializable {
	private static final long serialVersionUID = -5185865990624687118L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="MODULE_ID")
	private Long moduleId;
	
	@Column(name="MODULE_INDEX")
	private Integer moduleIndex;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TECHNOLOGY_ID")
	private ProjectTechnology technology;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProjectTechnology getTechnology() {
		return technology;
	}

	public void setTechnology(ProjectTechnology technology) {
		this.technology = technology;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Integer getModuleIndex() {
		return moduleIndex;
	}

	public void setModuleIndex(Integer moduleIndex) {
		this.moduleIndex = moduleIndex;
	}
}

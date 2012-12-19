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
@Table(name = "MODULE")
public class Module implements Serializable {
	private static final long serialVersionUID = -5487021755468142531L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "PRICEPERPROJECT")
	private double pricePerProject;

	@Column(name = "PRICEPERUNIT")
	private double pricePerUnit;

	public Module() {
	}

	public Module(String name, double pricePerProject, double pricePerUnit) {
		this.name = name;
		this.pricePerProject = pricePerProject;
		this.pricePerUnit = pricePerUnit;
		this.description = "";
	}

	public Module(String name, double pricePerProject, double pricePerUnit,
			String notes) {
		this.name = name;
		this.pricePerProject = pricePerProject;
		this.pricePerUnit = pricePerUnit;
		this.description = notes;
	}

	public Module(Integer index, String name, double pricePerProject,
			double pricePerUnit, String notes) {
		this.moduleIndex = index;
		this.name = name;
		this.pricePerProject = pricePerProject;
		this.pricePerUnit = pricePerUnit;
		this.description = notes;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "MODULEINDEX")
	private Integer moduleIndex;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TECHNOLOGY_ID")
	private Technology technology;

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

	public Integer getModuleIndex() {
		return moduleIndex;
	}

	public void setModuleIndex(Integer moduleIndex) {
		this.moduleIndex = moduleIndex;
	}

	public Technology getTechnology() {
		return technology;
	}

	public void setTechnology(Technology technology) {
		this.technology = technology;
	}

	public double getPricePerProject() {
		return pricePerProject;
	}

	public void setPricePerProject(double pricePerProject) {
		this.pricePerProject = pricePerProject;
	}

	public double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
}

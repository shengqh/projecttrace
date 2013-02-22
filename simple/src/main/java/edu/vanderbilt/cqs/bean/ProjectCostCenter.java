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
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "PROJECTCOSTCENTER")
public class ProjectCostCenter implements Serializable {

	private static final long serialVersionUID = -5186486819215195257L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	@NotEmpty
	private String name;

	@Column(name = "PERCENTAGE")
	private Double percentage = 0.0;
	
	@Column(name = "ISREMAININGCOST")
	private Boolean isRemainingCost = false;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	private Project project;
	
	@Transient
	private Boolean removed;

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

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Boolean getIsRemainingCost() {
		return isRemainingCost;
	}

	public void setIsRemainingCost(Boolean isRemainingCost) {
		this.isRemainingCost = isRemainingCost;
	}

	public Boolean getRemoved() {
		return removed;
	}

	public void setRemoved(Boolean removed) {
		this.removed = removed;
	}
}

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

import edu.vanderbilt.cqs.ModuleType;

@Entity
@Table(name = "PROJECTTECHNOLOGYMODULE")
public class ProjectTechnologyModule implements Serializable {
	private static final long serialVersionUID = -5185865990624687118L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "MODULE_ID")
	private Long moduleId;

	@Column(name = "MODULE_INDEX")
	private Integer moduleIndex;

	@Column(name = "SAMPLE_NUMBER")
	private Double sampleNumber;

	@Column(name = "OTHER_UNIT")
	private Double otherUnit;

	@Column(name = "PRICEPERPROJECT")
	private Double pricePerProject;

	@Column(name = "PRICEPERUNIT")
	private Double pricePerUnit;

	@Column(name = "MODULETYPE")
	private Integer moduleType;

	@Column(name = "DESCRIPTION")
	private String description;

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

	public Double getSampleNumber() {
		return sampleNumber;
	}

	public void setSampleNumber(Double sampleNumber) {
		this.sampleNumber = sampleNumber;
	}

	public Double getOtherUnit() {
		return otherUnit;
	}

	public void setOtherUnit(Double otherUnit) {
		this.otherUnit = otherUnit;
	}

	public Double getPricePerProject() {
		return pricePerProject;
	}

	public void setPricePerProject(Double pricePerProject) {
		this.pricePerProject = pricePerProject;
	}

	public Double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(Double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	private boolean hasSampleNumber() {
		return this.sampleNumber != null && this.sampleNumber > 0;
	}

	private boolean hasOtherUnit() {
		return this.otherUnit != null && this.otherUnit > 0;
	}

	public double getProjectSetupFee() {
		if (this.pricePerProject == null) {
			return 0.0;
		}
		
		int mType = getModuleTypeValue();

		double result = 0.0;
		if (mType == ModuleType.PerSample) {
			if (hasSampleNumber()) {
				result = this.pricePerProject;
			}
		} else if (mType == ModuleType.PerSamplePerUnit) {
			if (hasSampleNumber()) {
				result = this.sampleNumber * this.pricePerProject;
			}
		} else if (mType == ModuleType.PerUnit) {
			if (hasOtherUnit()) {
				result = this.pricePerProject;
			}
		} else {
			throw new RuntimeException(
					"I don't know how to calcuate project setup fee for module type "
							+ String.valueOf(mType));
		}
		
		return Math.round(result);
	}

	public int getModuleTypeValue() {
		int mType;
		if(this.moduleType == null){
			mType = ModuleType.PerSample;
		}
		else{
			mType = this.moduleType;
		}
		return mType;
	}

	public double getUnitFee() {
		if (this.pricePerUnit == null) {
			return 0.0;
		}

		int mType = getModuleTypeValue();

		double result = 0.0;
		if (mType == ModuleType.PerSample) {
			if (hasSampleNumber()) {
				result = this.sampleNumber * this.pricePerUnit;
			}
		} else if (mType == ModuleType.PerSamplePerUnit) {
			if (hasSampleNumber() && hasOtherUnit()) {
				result = this.sampleNumber * this.otherUnit * this.pricePerUnit;
			}
		} else if (mType == ModuleType.PerUnit) {
			if (hasOtherUnit()) {
				result = this.pricePerUnit * this.otherUnit;
			}
		} else {
			throw new RuntimeException(
					"I don't know how to calcuate project unit fee for module type "
							+ String.valueOf(mType));
		}
		
		return Math.round(result);
	}

	public Integer getModuleType() {
		if(moduleType == null){
			return Module.DEFAULT_TYPE;
		}
		return moduleType;
	}

	public void setModuleType(Integer moduleType) {
		this.moduleType = moduleType;
	}

	public String getModuleTypeString() {
		return ModuleType.getString(this.moduleType);
	}

	public double getTotalFee() {
		return this.getProjectSetupFee() + this.getUnitFee();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

package edu.vanderbilt.cqs.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "PROJECTTECHNOLOGY")
public class ProjectTechnology implements Serializable {
	private static final long serialVersionUID = -2371536244151648066L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "TECHNOLOGYID")
	private Long technologyId;

	@Column(name = "TECHNOLOGY")
	private String technology;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	private Project project;

	@Column(name = "PLATFORMID")
	private Long platformId;

	@Column(name = "PLATFORM")
	private String platform;

	@Column(name = "SAMPLENUMBER")
	private Integer sampleNumber;
	
	@OneToMany(mappedBy = "technology", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval=true)
	@OrderBy("moduleIndex")
	private List<ProjectTechnologyModule> modules = new ArrayList<ProjectTechnologyModule>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Long getTechnologyId() {
		return technologyId;
	}

	public void setTechnologyId(Long technologyId) {
		this.technologyId = technologyId;
	}

	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public Integer getSampleNumber() {
		return sampleNumber;
	}

	public void setSampleNumber(Integer sampleNumber) {
		this.sampleNumber = sampleNumber;
	}

	public List<ProjectTechnologyModule> getModules() {
		return modules;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((technologyId == null) ? 0 : technologyId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectTechnology other = (ProjectTechnology) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (technologyId == null) {
			if (other.technologyId != null)
				return false;
		} else if (!technologyId.equals(other.technologyId))
			return false;
		return true;
	}
}

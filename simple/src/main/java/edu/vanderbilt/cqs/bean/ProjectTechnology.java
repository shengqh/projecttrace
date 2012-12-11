package edu.vanderbilt.cqs.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
	private Set<ProjectTechnologyModule> modules = new HashSet<ProjectTechnologyModule>();

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

	public Set<ProjectTechnologyModule> getModules() {
		return modules;
	}

	public void setModules(Set<ProjectTechnologyModule> modules) {
		this.modules = modules;
	}
}

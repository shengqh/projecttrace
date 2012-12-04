package edu.vanderbilt.cqs.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import edu.vanderbilt.cqs.Status;

@Entity
@Table(name = "PROJECT")
public class Project implements Serializable {

	private static final long serialVersionUID = -5677749261455648256L;

	@Id
	@GeneratedValue
	@Column(name="ID")
	private Long id;

	@Column(name="NAME")
	private String name;

	@Column(name="DESCRIPTION")
	@Lob
	private String description;
	
	@Column(name="CONTACTDATE")
	private Date contactDate;

	@Column(name="CREATOR")
	private String creator;

	@Column(name="CREATEDATE")
	private Date createDate;

	@Column(name="STATUS")
	private Integer status = Status.PENDING;

	@Column(name="ENABLED")
	private Boolean enabled = true;

	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private Set<ProjectUser> users = new HashSet<ProjectUser>();

	@Column(name="TECHNOLOGIES")
	private String technologies;
	
	@Column(name="MODULES")
	private String modules;
	
	@Column(name="CONTACT")
	private String contact;
	
	public Date getContactDate() {
		return contactDate;
	}

	public void setContactDate(Date contactDate) {
		this.contactDate = contactDate;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getStudyPI() {
		return studyPI;
	}

	public void setStudyPI(String studyPI) {
		this.studyPI = studyPI;
	}

	@Column(name="STUDYPI")
	private String studyPI;

	public String getModules() {
		return modules;
	}

	public void setModules(String modules) {
		this.modules = modules;
	}

	public String getTechnologies() {
		return technologies;
	}

	public void setTechnologies(String technologies) {
		this.technologies = technologies;
	}

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

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Set<ProjectUser> getUsers() {
		return users;
	}

	public void setUsers(Set<ProjectUser> users) {
		this.users = users;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getStatusString(){
		return Status.getStatusMap().get(this.status);
	}
}

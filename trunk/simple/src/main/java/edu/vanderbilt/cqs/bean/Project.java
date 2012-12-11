package edu.vanderbilt.cqs.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import edu.vanderbilt.cqs.Status;
import edu.vanderbilt.cqs.UserType;

@Entity
@Table(name = "PROJECT")
public class Project implements Serializable {

	private static final long serialVersionUID = -5677749261455648256L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "CONTACTDATE")
	private Date contactDate;

	@Column(name = "NAME")
	private String name;

	@Column(name = "QUOTEAMOUNT")
	private Double quoteAmount;

	@Column(name = "WORKSTARTED")
	private Date workStarted;

	@Column(name = "WORKCOMPLETED")
	private Date workCompleted;

	@Column(name = "COSTCENTERTOBILL")
	private Double costCenterToBill;

	@Column(name = "REQUESTCOSTCENTERSETUPINCORES")
	private Date requestCostCenterSetupInCORES;

	@Column(name = "REQUESTEDBY")
	private String requestedBy;

	@Column(name = "BILLEDBYCORES")
	private Date billedInCORES;

	@Column(name = "BILLEDBY")
	private String billedBy;

	@Column(name = "COMMENTS")
	@Lob
	private String comments;

	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval=true)
	private Set<ProjectUser> users = new HashSet<ProjectUser>();

	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval=true)
	@OrderBy("technology")
	private Set<ProjectTechnology> technologies = new HashSet<ProjectTechnology>();
	
	@Column(name = "CREATOR")
	private String creator;

	@Column(name = "CREATEDATE")
	private Date createDate;

	@Column(name = "STATUS")
	private Integer status = Status.PENDING;

	@Column(name = "ENABLED")
	private Boolean enabled = true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getContactDate() {
		return contactDate;
	}

	public void setContactDate(Date contactDate) {
		this.contactDate = contactDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<ProjectTechnology> getTechnologies() {
		return technologies;
	}

	public void setTechnologies(Set<ProjectTechnology> technologies) {
		this.technologies = technologies;
	}

	public Double getQuoteAmount() {
		return quoteAmount;
	}

	public void setQuoteAmount(Double quoteAmount) {
		this.quoteAmount = quoteAmount;
	}

	public Date getWorkStarted() {
		return workStarted;
	}

	public void setWorkStarted(Date workStarted) {
		this.workStarted = workStarted;
	}

	public Date getWorkCompleted() {
		return workCompleted;
	}

	public void setWorkCompleted(Date workCompleted) {
		this.workCompleted = workCompleted;
	}

	public Double getCostCenterToBill() {
		return costCenterToBill;
	}

	public void setCostCenterToBill(Double costCenterToBill) {
		this.costCenterToBill = costCenterToBill;
	}

	public Date getRequestCostCenterSetupInCORES() {
		return requestCostCenterSetupInCORES;
	}

	public void setRequestCostCenterSetupInCORES(
			Date requestCostCenterSetupInCORES) {
		this.requestCostCenterSetupInCORES = requestCostCenterSetupInCORES;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public Date getBilledInCORES() {
		return billedInCORES;
	}

	public void setBilledInCORES(Date billedInCORES) {
		this.billedInCORES = billedInCORES;
	}

	public String getBilledBy() {
		return billedBy;
	}

	public void setBilledBy(String billedBy) {
		this.billedBy = billedBy;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Set<ProjectUser> getUsers() {
		return users;
	}

	public void setUsers(Set<ProjectUser> users) {
		this.users = users;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	private List<String> getUserName(Integer uType) {
		List<String> result = new ArrayList<String>();
		for (ProjectUser user : getUsers()) {
			if(user.getUserType().equals(uType)){
				result.add(user.getUser().getName());
			}
		}
		return result;
	}

	public List<String> getContactName() {
		return getUserName(UserType.CONTACT);
	}

	public List<String> getStudyPIName() {
		return getUserName(UserType.STUDYPI);
	}

	public List<String> getFacultyName() {
		return getUserName(UserType.VANGARD_FACULTY);
	}

	public List<String> getStaffName() {
		return getUserName(UserType.VANGARD_STAFF);
	}
}

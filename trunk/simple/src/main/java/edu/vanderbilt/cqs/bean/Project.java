package edu.vanderbilt.cqs.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

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
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date contactDate;

	@Column(name = "CONTRACTDATE")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date contractDate;

	@Column(name = "NAME")
	private String name = "";

	@Column(name = "IS_BIOVU_SAMPLE_REQUEST")
	private Boolean isBioVUSampleRequest = false;

	@Column(name = "IS_BIOVU_DATA_REQUEST")
	private Boolean isBioVUDataRequest = false;

	@Column(name = "BIOVU_DATA_DELIVERY_DATE")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date bioVUDataDeliveryDate;

	@Column(name = "BIOVU_REDEPOSIT_DATE")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date bioVURedepositDate;

	@Column(name = "ISVANTAGE")
	private Boolean isVantage = false;

	@Column(name = "ISOUTSIDE")
	private Boolean isOutside = false;

	@Column(name = "ISGRANTED")
	private Boolean isGranted = false;

	@Column(name = "QUOTEAMOUNT")
	private Double quoteAmount;

	@Column(name = "WORKSTARTED")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date workStarted;

	@Column(name = "WORKCOMPLETED")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date workCompleted;

	@Column(name = "COSTCENTERTOBILL_STRING")
	private String costCenterToBill;

	@Column(name = "REQUESTCOSTCENTERSETUPINCORES")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date requestCostCenterSetupInCORES;

	@Column(name = "REQUESTEDBY")
	private String requestedBy;

	@Column(name = "BILLEDBYCORES")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date billedInCORES;

	@Column(name = "BILLEDBY")
	private String billedBy;

	@Column(name = "BILLEDAMOUNT")
	private Double billedAmount;

	public Double getBilledAmount() {
		return billedAmount;
	}

	public void setBilledAmount(Double billedAmount) {
		this.billedAmount = billedAmount;
	}

	@Column(name = "CONTACT")
	private String contact;

	@Column(name = "STUDYPI")
	private String studyPI;

	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
	@OrderBy("commentDate")
	private List<ProjectComment> comments = new ArrayList<ProjectComment>();

	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
	private List<ProjectUser> users = new ArrayList<ProjectUser>();

	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
	@OrderBy("technology")
	private List<ProjectTechnology> technologies = new ArrayList<ProjectTechnology>();

	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
	@OrderBy("id")
	private List<ProjectFile> files = new ArrayList<ProjectFile>();

	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
	@OrderBy("id")
	private List<ProjectCostCenter> costCenters = new ArrayList<ProjectCostCenter>();

	@Column(name = "CREATOR")
	private String creator;

	@Column(name = "CREATEDATE")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createDate;

	@Column(name = "STATUS")
	private String status = Status.PENDING;

	@Column(name = "ENABLED")
	private Boolean enabled = true;

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

	public List<ProjectTechnology> getTechnologies() {
		return technologies;
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

	public String getCostCenterToBill() {
		return costCenterToBill;
	}

	public void setCostCenterToBill(String costCenterToBill) {
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

	public List<ProjectUser> getUsers() {
		return users;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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
			if (user.getUserType().equals(uType)) {
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

	public List<ProjectComment> getComments() {
		return comments;
	}
	
	public List<String> getCostCenterToBillList(){
		List<String> result = new ArrayList<String>();
		for(ProjectCostCenter cc:getCostCenters()){
			result.add(cc.getName() + " : " + cc.getPercentage().toString() + "%");
		}
		
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Project other = (Project) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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

	public static String getProjectName(Long id) {
		if (id == null) {
			return "";
		} else {
			return String.format("VANGARD%05d", id);
		}
	}

	public String getProjectName() {
		return getProjectName(this.id);
	}

	public Boolean getIsVantage() {
		return isVantage;
	}

	public void setIsVantage(Boolean isVantage) {
		this.isVantage = isVantage;
	}

	public Boolean getIsOutside() {
		return isOutside;
	}

	public void setIsOutside(Boolean isOutside) {
		this.isOutside = isOutside;
	}

	public Boolean getIsBioVUSampleRequest() {
		return isBioVUSampleRequest;
	}

	public void setIsBioVUSampleRequest(Boolean isBioVUSampleRequest) {
		this.isBioVUSampleRequest = isBioVUSampleRequest;
	}

	public Boolean getIsBioVUDataRequest() {
		return isBioVUDataRequest;
	}

	public void setIsBioVUDataRequest(Boolean isBioVUDataRequest) {
		this.isBioVUDataRequest = isBioVUDataRequest;
	}

	public Date getBioVUDataDeliveryDate() {
		return bioVUDataDeliveryDate;
	}

	public void setBioVUDataDeliveryDate(Date bioVUDataDeliveryDate) {
		this.bioVUDataDeliveryDate = bioVUDataDeliveryDate;
	}

	public Date getBioVURedepositDate() {
		return bioVURedepositDate;
	}

	public void setBioVURedepositDate(Date bioVURedepositDate) {
		this.bioVURedepositDate = bioVURedepositDate;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	public Boolean getIsBioVU() {
		return (isBioVUDataRequest != null && isBioVUDataRequest)
				|| (isBioVUSampleRequest != null && isBioVUSampleRequest);
	}

	public Date getContactDate() {
		return contactDate;
	}

	public void setContactDate(Date contactDate) {
		this.contactDate = contactDate;
	}

	public String getBioVUType() {
		if (isBioVUSampleRequest != null && isBioVUSampleRequest) {
			return "Sample request";
		}

		if (isBioVUDataRequest != null && isBioVUDataRequest) {
			return "Data request";
		}

		return "";
	}

	public List<ProjectFile> getFiles() {
		return files;
	}

	public Boolean getIsGranted() {
		return isGranted;
	}

	public void setIsGranted(Boolean isGranted) {
		this.isGranted = isGranted;
	}

	public List<ProjectCostCenter> getCostCenters() {
		return costCenters;
	}
}

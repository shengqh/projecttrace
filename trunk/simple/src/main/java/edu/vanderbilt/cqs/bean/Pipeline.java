package edu.vanderbilt.cqs.bean;

import java.io.Serializable;
import java.util.Date;
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

import edu.vanderbilt.cqs.Utils;

@Entity
@Table(name = "PIPELINE")
public class Pipeline implements Serializable {

	private static final long serialVersionUID = -7429170184798325852L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "NAME")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "CREATOR_ID")
	private User creator;

	@Column(name = "CREATEDATE")
	private Date createDate;

	@Column(name = "ENABLED")
	private Boolean Enabled = true;

	@OneToMany(mappedBy = "pipeline")
	@OrderBy("taskIndex")
	private List<PipelineTask> tasks;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<PipelineTask> getTasks() {
		return tasks;
	}

	public void setTasks(List<PipelineTask> tasks) {
		this.tasks = tasks;
	}

	public Boolean isEnabled() {
		return Enabled;
	}

	public void setEnabled(Boolean enabled) {
		Enabled = enabled;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPeopleTime() {
		return Utils.getTotalPeopleTime(getTasks());
	}

	public double getMachineTime() {
		return Utils.getTotalMachineTime(getTasks());
	}
}

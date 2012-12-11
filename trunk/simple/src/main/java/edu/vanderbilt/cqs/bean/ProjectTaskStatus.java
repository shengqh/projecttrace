package edu.vanderbilt.cqs.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PROJECTTASKSTATUS")
public class ProjectTaskStatus implements Serializable {

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	private static final long serialVersionUID = -1921355876114740827L;

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_TASK_ID")
	private ProjectTask task;

	@Column(name = "STATUS")
	private String status;

	@Lob
	@Column(name = "COMMENT")
	private String comment;

	@Column(name = "INPUTEUSER")
	private String updateUser;

	@Column(name = "INPUTEDATE")
	private Date updateDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProjectTask getTask() {
		return task;
	}

	public void setTask(ProjectTask task) {
		this.task = task;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}

package edu.vanderbilt.cqs.form;

import java.io.Serializable;
import java.util.Date;

public class ProjectTaskStatusForm implements Serializable {
	private static final long serialVersionUID = -8313684048270091328L;

	private Long id;

	private Integer status;

	private String comment;

	private String updateUser;

	private Date updateDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
}

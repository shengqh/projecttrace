package edu.vanderbilt.cqs.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import edu.vanderbilt.cqs.Utils;

@Entity
@Table(name = "USER")
public class User implements Serializable {
	private static final long serialVersionUID = 7401126221031716368L;

	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Integer id;

	@Column(name = "FIRSTNAME")
	private String firstname;

	@Column(name = "LASTNAME")
	private String lastname;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "TELEPHONE")
	private String telephone;

	@Column(name = "CREATEDATE")
	private Date createDate;

	@Column(name = "ENABLED")
	private Boolean Enabled = true;

	@Column(name = "LOCKED")
	private Boolean Locked = false;

	@Column(name = "MD5PASSWORD")
	private String md5password;

	@Transient
	private String password;

	public String getEmail() {
		return email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getMd5password() {
		return md5password;
	}

	public void setMd5password(String md5password) {
		this.md5password = md5password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		this.md5password = Utils.md5(password);
	}

	public Boolean isEnabled() {
		return Enabled;
	}

	public void setEnabled(Boolean enabled) {
		Enabled = enabled;
	}

	public Boolean isLocked() {
		return Locked;
	}

	public void setLocked(Boolean locked) {
		Locked = locked;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}

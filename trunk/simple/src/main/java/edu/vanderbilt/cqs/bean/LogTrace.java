package edu.vanderbilt.cqs.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.jboss.logging.Logger;

@Entity
@Table(name = "LOGTRACE")
public class LogTrace implements Serializable {
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getLogDateString() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.getLogDate());
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	private static final long serialVersionUID = -3789317031830536399L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "LEVEL")
	private int level;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getLevelString() {
		for (Logger.Level l : Logger.Level.values()) {
			if (l.ordinal() == level) {
				return l.name();
			}
		}
		return Logger.Level.INFO.name();
	}

	@Column(name = "LOGDATE")
	private Date logDate;

	@Column(name = "USER")
	private String user;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Column(name = "ACTION")
	private String action;

	@Column(name = "IPADDRESS")
	private String ipaddress;
}

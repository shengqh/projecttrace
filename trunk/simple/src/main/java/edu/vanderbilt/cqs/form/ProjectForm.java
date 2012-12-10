package edu.vanderbilt.cqs.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.vanderbilt.cqs.bean.Project;
import edu.vanderbilt.cqs.bean.Technology;
import edu.vanderbilt.cqs.bean.User;

public class ProjectForm implements Serializable {
	private static final long serialVersionUID = -4361402280964698358L;

	private Project project;
	
	private List<Long> technology = new ArrayList<Long>();
	
	private List<Long> faculty = new ArrayList<Long>();

	private List<Long> staff = new ArrayList<Long>();

	private List<Long> contact = new ArrayList<Long>();

	private List<Long> studyPI = new ArrayList<Long>();
	
	private List<Technology> technologies;
	
	private List<User> users;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Long> getTechnology() {
		return technology;
	}

	public void setTechnology(List<Long> technology) {
		this.technology = technology;
	}

	public List<Long> getFaculty() {
		return faculty;
	}

	public void setFaculty(List<Long> faculty) {
		this.faculty = faculty;
	}

	public List<Long> getStaff() {
		return staff;
	}

	public void setStaff(List<Long> staff) {
		this.staff = staff;
	}

	public List<Long> getContact() {
		return contact;
	}

	public void setContact(List<Long> contact) {
		this.contact = contact;
	}

	public List<Long> getStudyPI() {
		return studyPI;
	}

	public void setStudyPI(List<Long> studyPI) {
		this.studyPI = studyPI;
	}

	public List<Technology> getTechnologies() {
		return technologies;
	}

	public void setTechnologies(List<Technology> technologies) {
		this.technologies = technologies;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}

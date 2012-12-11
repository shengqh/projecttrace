package edu.vanderbilt.cqs.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.vanderbilt.cqs.Status;
import edu.vanderbilt.cqs.bean.Project;
import edu.vanderbilt.cqs.bean.Technology;
import edu.vanderbilt.cqs.bean.User;

public class ProjectForm implements Serializable {
	private static final long serialVersionUID = -4361402280964698358L;

	private Project project;

	private Integer userType;

	private List<Long> technology = new ArrayList<Long>();

	private List<Long> faculty = new ArrayList<Long>();

	private List<Long> staff = new ArrayList<Long>();

	private List<Long> contact = new ArrayList<Long>();

	private List<Long> studyPI = new ArrayList<Long>();

	private List<Technology> technologyList;

	private List<User> contactList;

	private List<User> studyPIList;

	private List<User> facultyList;

	private List<User> staffList;

	public Map<String, String> getStatusMap() {
		return Status.getStatusMap();
	}

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

	public List<User> getContactList() {
		return contactList;
	}

	public void setContactList(List<User> contactList) {
		this.contactList = contactList;
	}

	public List<User> getStudyPIList() {
		return studyPIList;
	}

	public void setStudyPIList(List<User> studyPIList) {
		this.studyPIList = studyPIList;
	}

	public List<User> getFacultyList() {
		return facultyList;
	}

	public void setFacultyList(List<User> facultyList) {
		this.facultyList = facultyList;
	}

	public List<User> getStaffList() {
		return staffList;
	}

	public void setStaffList(List<User> staffList) {
		this.staffList = staffList;
	}

	public List<Technology> getTechnologyList() {
		return technologyList;
	}

	public void setTechnologyList(List<Technology> technologyList) {
		this.technologyList = technologyList;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}
}

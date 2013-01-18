package edu.vanderbilt.cqs.form;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadForm {
	private Long projectId;
	
	private String message;
	
	private List<MultipartFile> files;

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
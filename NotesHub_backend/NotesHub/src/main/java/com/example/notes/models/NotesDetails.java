package com.example.notes.models;

import java.time.LocalDate;

public class NotesDetails 
{
	private Long id;
	private String fileName;
	
	private String teacherName;
	
	private LocalDate uploadedAt;

	private int downloadCount;

	public NotesDetails(Long id, String fileName, String teacherName, LocalDate uploadedAt, int downloadCount) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.teacherName = teacherName;
		this.uploadedAt = uploadedAt;
		this.downloadCount = downloadCount;
	}

	
	public Long getId() {
		return id;
	}



	public String getFileName() {
		return fileName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public LocalDate getUploadedAt() {
		return uploadedAt;
	}


	public int getDownloadCount() {
		return downloadCount;
	}	
	
	
	
}

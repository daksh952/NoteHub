package com.example.notes.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Note 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileLanguage;

    @Lob
    private byte[] fileData;  // Store the file content as byte array
    
    
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false) // Foreign key linking to User (Teacher)
    private User teacher;
    
    @Column(name = "uploaded_at", nullable = false, updatable = false)  // ✅ Ensure separate column
    @JsonFormat(pattern = "yyyy-MM-dd")  // ✅ Store only date
    private LocalDate uploadedAt;
    
    @Column(name = "download_count", nullable = false)
    private int downloadCount = 0;

    // Default constructor, getters, and setters
    public Note() {
    }

    public Note(String fileName, String fileLanguage, byte[] fileData, User teacher) {
        this.fileName = fileName;
        this.fileLanguage = fileLanguage;
        this.fileData = fileData;
        this.teacher = teacher;
        this.uploadedAt = LocalDate.now(); // ✅ Automatically set upload date
    }
    

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileLanguage() {
        return fileLanguage;
    }

    public void setFileLanguage(String fileLanguage) {
        this.fileLanguage = fileLanguage;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
    
    public void incrementDownloadCount() {
        this.downloadCount++;
    }
}

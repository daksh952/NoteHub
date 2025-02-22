package com.example.notes.models;


import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Announcement 
{
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private String message;

	    @ManyToOne
	    @JoinColumn(name = "teacher_id", nullable = false) // Link to teacher
	    @JsonIgnoreProperties({"id", "password", "email", "role", "notes"})
	    private User teacher;

	    @Column(name = "created_at", nullable = false, updatable = false)
	    private LocalDate createdAt;

	    public Announcement(String message, User teacher) {
	        this.message = message;
	        this.teacher = teacher;
	        this.createdAt = LocalDate.now(); // Auto-set date
	    }
}

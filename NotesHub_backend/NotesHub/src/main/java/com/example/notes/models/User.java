package com.example.notes.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    @Column(unique = true, nullable = false)
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;  // STUDENT or FACULTY
    
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Note> notes;
	
}

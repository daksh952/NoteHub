package com.example.notes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.notes.models.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> 
{
	List<Announcement> findByOrderByIdDesc();
}


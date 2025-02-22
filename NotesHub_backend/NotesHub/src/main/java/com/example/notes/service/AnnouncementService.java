package com.example.notes.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.notes.models.Announcement;
import com.example.notes.models.User;
import com.example.notes.repository.AnnouncementRepository;
import com.example.notes.repository.UserRepository;

@Service
public class AnnouncementService 
{
	@Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private UserRepository userRepository;
    
    // Add new announcement
    public void addAnnouncement(Long teacherId, String message) {
        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        Announcement announcement = new Announcement(message, teacher);
        announcementRepository.save(announcement);
    }
    
    // Get all announcements
    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findByOrderByIdDesc();
    }

    // Delete an announcement
    public void deleteAnnouncement(Long id) {
        announcementRepository.deleteById(id);
    }

}

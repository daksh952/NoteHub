package com.example.notes.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.notes.models.Announcement;
import com.example.notes.service.AnnouncementService;


@RestController
@RequestMapping("/announcement")
@CrossOrigin(origins = "http://localhost:3000")
public class AnnouncementController
{
	@Autowired
    private AnnouncementService announcementService;
	
	@PostMapping("/add/{teacherId}")
    public ResponseEntity<String> addAnnouncement(
            @PathVariable Long teacherId, @RequestParam String message) {
        announcementService.addAnnouncement(teacherId, message);
        return ResponseEntity.ok("Announcement added successfully!");
    }
	
	@GetMapping("/all")
    public List<Announcement> getAllAnnouncements() {
        return announcementService.getAllAnnouncements();
    }
	
	 // Delete announcement (If needed)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAnnouncement(@PathVariable Long id) {
        announcementService.deleteAnnouncement(id);
        return ResponseEntity.ok("Announcement deleted successfully!");
    }

}

package com.example.notes.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.notes.models.Note;
import com.example.notes.models.NotesDetails;
import com.example.notes.service.NoteService;


@RestController
@RequestMapping("/teacher")
@CrossOrigin(origins = "http://localhost:3000")
public class NoteController 
{
	 @Autowired
	    private NoteService noteService;

	 // Uploading file
	 @PostMapping("/upload/{teacherId}")
	 public ResponseEntity<String> uploadNote(
	         @PathVariable Long teacherId,
	         @RequestParam("file") MultipartFile file,
	         @RequestParam("fileName") String fileName,
	         @RequestParam("fileLanguage") String fileLanguage) throws IOException {
	     
	     noteService.uploadNote(teacherId, file, fileName, fileLanguage);
	     return ResponseEntity.ok("Note uploaded successfully on " + LocalDate.now());
	 }

	    // Get all uploaded notes
	    @GetMapping("/notes")
	    public List<Note> getUploadedNotes() {
	        return noteService.getAllNotes();
	    }

	    // Delete a note
	    @DeleteMapping("/delete/{noteId}")
	    public ResponseEntity<String> deleteNote(@PathVariable Long noteId) {
	        noteService.deleteNote(noteId);
	        return ResponseEntity.ok("Note deleted successfully");
	    }

	    

	    // Get note data (byte[])
//	    @GetMapping("/download/{noteId}")
//	    public ResponseEntity<byte[]> getNote(@PathVariable Long noteId) {
//	        byte[] fileData = noteService.getNoteData(noteId);
//	        return ResponseEntity.ok(fileData);
//	    }
	    
//	    @GetMapping("/download/{noteId}")
//	    public ResponseEntity<byte[]> getNote(@PathVariable Long noteId) {
//	        Note note = noteService.getNoteById(noteId);
//	        byte[] fileData = noteService.getNoteData(noteId);
//
//	        return ResponseEntity.ok()
//	            .header("Content-Disposition", "attachment; filename=" + note.getFileName()) // Set filename
//	            .body(fileData);
//	    }

//	    @GetMapping("/download/{id}")
//	    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
//	        return noteService.downloadFile(id);
//	    }
	    
	    // ✅ Download a PDF note & track downloads
	    @GetMapping("/download/{noteId}")
	    public ResponseEntity<Resource> downloadNote(@PathVariable Long noteId) {
	        return noteService.downloadNote(noteId);
	    }

	    // ✅ Get most downloaded notes
	    @GetMapping("/most-downloaded")
	    public List<NotesDetails> getMostDownloadedNotes() {
	        return noteService.getMostDownloadedNotes();
	    }
	    @GetMapping("/allnotes")
	    public List<NotesDetails> getAllNotesByTeacher() {
	    	return noteService.getAllNotesByTeacher();
	    }
	    
	    @GetMapping("/notes/{teacherId}")
	    public List<NotesDetails> getNotesByTeacher(@PathVariable Long teacherId) {
	        return noteService.getNotesByTeacher(teacherId);
	    }

	    @GetMapping("/recent-notes")
	    public List<NotesDetails> getRecentNotes() {
	        return noteService.getRecentNotes();
	    }
}

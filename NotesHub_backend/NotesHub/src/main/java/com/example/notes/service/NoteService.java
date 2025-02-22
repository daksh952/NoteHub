package com.example.notes.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.notes.models.Note;
import com.example.notes.models.NotesDetails;
import com.example.notes.models.User;
import com.example.notes.repository.NoteRepository;
import com.example.notes.repository.UserRepository;

@Service
public class NoteService 
{
	@Autowired
    private NoteRepository noteRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	// Upload a note (file)
	public void uploadNote(Long teacherId, MultipartFile file, String fileName, String fileLanguage) throws IOException {
        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Note note = new Note();
        note.setFileName(fileName);
        note.setFileLanguage(fileLanguage);
        note.setFileData(file.getBytes());
        note.setTeacher(teacher); // Associate note with the teacher
        note.setUploadedAt(LocalDate.now()); // ✅ Automatically set uploaded date

        noteRepository.save(note);
	}

    // Get all uploaded notes
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    // Delete a note
    public void deleteNote(Long noteId) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new RuntimeException("Note not found"));
        noteRepository.delete(note);
    }
    
    public List<NotesDetails> getRecentNotes() {
    	return noteRepository.findTop5ByUploadedAtIsNotNullOrderByIdDesc();
    }



    // Get note content as byte[]
    public byte[] getNoteData(Long noteId) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new RuntimeException("Note not found"));
        return note.getFileData();
    }
    
    public Note getNoteById(Long noteId) {
        return noteRepository.findById(noteId)
                .orElseThrow();
    }
    
    public List<NotesDetails> getAllNotesByTeacher()
    {
    	return noteRepository.findAllByTeacher();
    }
    
    public List<NotesDetails> getNotesByTeacher(Long teacherId) {
        return noteRepository.findByTeacherId(teacherId);
        
    }
    
//    public ResponseEntity<byte[]> downloadFile(Long id) {
//        Note note = noteRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Note not found!"));
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + note.getFileName() + ".pdf")
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(note.getFileData());
//    }
    
 // ✅ Download note as PDF & Increment Download Count
    public ResponseEntity<Resource> downloadNote(Long noteId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found with ID: " + noteId));

        // ✅ Increment download count
        note.setDownloadCount(note.getDownloadCount() + 1);
        noteRepository.save(note);

        ByteArrayResource resource = new ByteArrayResource(note.getFileData());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)  // ✅ Ensures response is always PDF
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + note.getFileName())
                .body(resource);
    }

    // ✅ Get most downloaded notes
    public List<NotesDetails> getMostDownloadedNotes() {
        return noteRepository.findTopDownloadedNotes();
    }
    
    


}

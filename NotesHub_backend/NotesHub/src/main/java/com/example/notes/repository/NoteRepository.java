package com.example.notes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.notes.models.Note;
import com.example.notes.models.NotesDetails;

public interface NoteRepository extends JpaRepository<Note, Long>
{
	@Query("SELECT new com.example.notes.models.NotesDetails(n.id, n.fileName, t.name, n.uploadedAt, n.downloadCount) " +
		       "FROM Note n JOIN n.teacher t ORDER BY n.id DESC")
		List<NotesDetails> findAllByTeacher();
	
//	List<Note> findAllByFileName();
	List<NotesDetails> findByTeacherId(Long teacherId);
	
	List<NotesDetails> findTop5ByUploadedAtIsNotNullOrderByIdDesc();
	
	@Query("SELECT new com.example.notes.models.NotesDetails(n.id, n.fileName, t.name, n.uploadedAt, n.downloadCount) " +
		       "FROM Note n JOIN n.teacher t ORDER BY n.downloadCount DESC")
		List<NotesDetails> findTopDownloadedNotes();
}

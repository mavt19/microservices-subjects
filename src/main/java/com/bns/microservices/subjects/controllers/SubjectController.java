package com.bns.microservices.subjects.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bns.microservices.subjects.models.entity.Student;
import com.bns.microservices.subjects.models.entity.Subject;
import com.bns.microservices.subjects.services.SubjectService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class SubjectController {

	private final SubjectService subjectService;

	@GetMapping
	public ResponseEntity<?> getAllStudents() {

		return ResponseEntity.ok().body(subjectService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable("id") Long id) {
		Optional<Subject> subjectOpt = subjectService.finById(id);
		if (subjectOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(subjectOpt.get());
	}

	@PostMapping
	public ResponseEntity<?> createStudent(@RequestBody Subject subject) {
		Subject studentDb = subjectService.save(subject);
		return ResponseEntity.status(HttpStatus.CREATED).body(studentDb);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateStudent(@RequestBody Subject subject, @PathVariable("id") Long id) {
		Optional<Subject> subjectOpt = subjectService.finById(id);
		if (subjectOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		subject.setId(id);
		Subject subjectDb = subjectService.save(subject);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(subjectDb);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable("id") Long id) {
		Optional<Subject> subjectOpt = subjectService.finById(id);
		if (subjectOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		subjectService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}/add-students-to-subject")
	public ResponseEntity<?> addStudentsToSubject(@RequestBody List<Student> students, @PathVariable("id") Long id) {
		Optional<Subject> subjectOpt = subjectService.finById(id);
		if (subjectOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		subjectOpt.get().setStudents(students);
		Subject subjectDb = subjectService.save(subjectOpt.get());
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(subjectDb);
	}
	
	@PutMapping("/{id}/delete-student-to-subject")
	public ResponseEntity<?> deleteStudentsToSubject(@RequestBody Student student, @PathVariable("id") Long id) {
		Optional<Subject> subjectOpt = subjectService.finById(id);
		if (subjectOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		if(student == null || student.getId() != null) {
			return ResponseEntity.notFound().build();
		}
		List<Student> newStudents = subjectOpt.get().getStudents().parallelStream().filter(x-> !x.getId().equals(student.getId())).collect(Collectors.toList());
		
		subjectOpt.get().setStudents(newStudents);
		Subject subjectDb = subjectService.save(subjectOpt.get());
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(subjectDb);
	}
	
	@GetMapping("/{id}/get-subjet-to-student-id")
	public ResponseEntity<?> getSubjectToStudentId(@PathVariable("id") Long id) {
		Subject subject = subjectService.findSubjectByStudetId(id);
		if (subject == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(subject);
	}
}

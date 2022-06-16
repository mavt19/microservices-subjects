package com.bns.microservices.subjects.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bns.microservices.subjects.models.entity.Exam;
import com.bns.microservices.subjects.models.entity.Student;
import com.bns.microservices.subjects.models.entity.Subject;
import com.bns.microservices.subjects.services.SubjectService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class SubjectController {

	private final SubjectService subjectService;

	@GetMapping
	public ResponseEntity<?> getAllSubjects() {

		return ResponseEntity.ok().body(subjectService.findAll());
	}

	@GetMapping("/page")
	public ResponseEntity<?> getAllSubjects(Pageable pageable) {

		return ResponseEntity.ok().body(subjectService.findAll(pageable));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getSubjectById(@PathVariable("id") Long id) {
		Optional<Subject> subjectOpt = subjectService.finById(id);
		if (subjectOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(subjectOpt.get());
	}

	@PostMapping
	public ResponseEntity<?> createSubject(@Valid @RequestBody Subject subject, BindingResult result) {
		if(result.hasErrors()) {
			return validate(result);
		}
		Subject studentDb = subjectService.save(subject);
		return ResponseEntity.status(HttpStatus.CREATED).body(studentDb);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateSubject(@Valid @RequestBody Subject subject, BindingResult result, @PathVariable("id") Long id) {
		if(result.hasErrors()) {
			return validate(result);
		}
		Optional<Subject> subjectOpt = subjectService.finById(id);
		if (subjectOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		subject.setId(id);
		Subject subjectDb = subjectService.save(subject);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(subjectDb);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSubject(@PathVariable("id") Long id) {
		Optional<Subject> subjectOpt = subjectService.finById(id);
		if (subjectOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		subjectService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}/add-students-to-subject")
	public ResponseEntity<?> addStudentsToSubject(@Valid @RequestBody List<Student> students, BindingResult result, @PathVariable("id") Long id) {
		if(result.hasErrors()) {
			return validate(result);
		}
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
		if(student == null || student.getId() == null) {
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
		List<Long> examensId = (List<Long>) subjectService.getAnswerIdWithAnswersByStudent(id);
		List<Exam> exams = subject.getExams().stream().map(exam ->{
			if(examensId != null && examensId.contains(exam.getId())) {
				exam.setAnswered(true);
			}
			return exam;
		})
				.toList();
		subject.setExams(exams);
		return ResponseEntity.ok().body(subject);
	}
	
	@PutMapping("/{id}/add-exams-to-subject")
	public ResponseEntity<?> addExamsToSubject(@Valid @RequestBody List<Exam> exams, BindingResult result, @PathVariable("id") Long id) {
		if(result.hasErrors()) {
			return validate(result);
		}
		Optional<Subject> subjectOpt = subjectService.finById(id);
		if (subjectOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		subjectOpt.get().setExams(exams);
		Subject subjectDb = subjectService.save(subjectOpt.get());
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(subjectDb);
	}
	
	@PutMapping("/{id}/delete-exam-to-subject")
	public ResponseEntity<?> deleteExamToSubject(@RequestBody Exam exam, @PathVariable("id") Long id) {
		Optional<Subject> subjectOpt = subjectService.finById(id);
		if (subjectOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		System.out.println(exam);
		subjectOpt.get().getExams().forEach(x->{
			System.out.println(" --- "+x.getId() + x.getName());
		});
		if(exam == null || exam.getId() == null) {
			return ResponseEntity.notFound().build();
		}
		List<Exam> newExams = subjectOpt.get().getExams().parallelStream().filter(x-> !x.getId().equals(exam.getId())).collect(Collectors.toList());
		
		subjectOpt.get().setExams(newExams);
		Subject subjectDb = subjectService.save(subjectOpt.get());
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(subjectDb);
	}
	
	private static ResponseEntity<?> validate(BindingResult result){
		Map<String, Object> errors = new HashMap<>();
		result.getFieldErrors().forEach(x->{
			errors.put(x.getField(), " the field : "+ x.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errors);
	}
	
}

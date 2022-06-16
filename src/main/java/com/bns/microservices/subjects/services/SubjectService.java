package com.bns.microservices.subjects.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bns.microservices.subjects.models.entity.Subject;

public interface SubjectService {

	public Iterable<Subject> findAll();
	
	public Page<Subject> findAll(Pageable pageable);
	
	public Optional<Subject> finById(Long id);
	
	public Subject save(Subject subject);
	
	public void deleteById(Long id);
	
	public Subject findSubjectByStudetId(Long id);
	
	public Iterable<Long> getAnswerIdWithAnswersByStudent(Long studentId);
}

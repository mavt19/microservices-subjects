package com.bns.microservices.subjects.services;

import java.util.Optional;

import com.bns.microservices.subjects.models.entity.Subject;

public interface SubjectService {

	public Iterable<Subject> findAll();
	
	public Optional<Subject> finById(Long id);
	
	public Subject save(Subject subject);
	
	public void deleteById(Long id);
	
	public Subject findSubjectByStudetId(Long id);
}

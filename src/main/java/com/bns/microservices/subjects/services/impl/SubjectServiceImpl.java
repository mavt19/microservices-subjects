package com.bns.microservices.subjects.services.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bns.microservices.subjects.clients.AwnserFeignClient;
import com.bns.microservices.subjects.models.entity.Subject;
import com.bns.microservices.subjects.models.repository.SubjectRepository;
import com.bns.microservices.subjects.services.SubjectService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubjectServiceImpl implements SubjectService{

	private final SubjectRepository subjectRepository;
	
	private final AwnserFeignClient awnserFeignClient;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Subject> findAll() {
		// TODO Auto-generated method stub
		return subjectRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Subject> finById(Long id) {
		// TODO Auto-generated method stub
		return subjectRepository.findById(id);
	}

	@Override
	@Transactional
	public Subject save(Subject subject) {
		// TODO Auto-generated method stub
		return subjectRepository.save(subject);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		subjectRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Subject findSubjectByStudetId(Long id) {
		// TODO Auto-generated method stub
		return subjectRepository.findSubjectByStudetId(id);
	}

	@Override
	public Page<Subject> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return subjectRepository.findAll(pageable);
	}

	@Override
	public Iterable<Long> getAnswerIdWithAnswersByStudent(Long studentId) {
		// TODO Auto-generated method stub
		return awnserFeignClient.getAnswerIdWithAnswersByStudent(studentId);
	}

}

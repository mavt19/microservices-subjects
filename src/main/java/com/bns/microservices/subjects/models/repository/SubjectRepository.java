package com.bns.microservices.subjects.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bns.microservices.subjects.models.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>{

	@Query("select c from Subject c join fetch c.students s where s.id = ?1")
	public Subject findSubjectByStudetId(Long id);
}

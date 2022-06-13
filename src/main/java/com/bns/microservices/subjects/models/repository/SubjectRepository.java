package com.bns.microservices.subjects.models.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bns.microservices.subjects.models.entity.Subject;

public interface SubjectRepository extends PagingAndSortingRepository<Subject, Long>{

	@Query("select c from Subject c join fetch c.students s where s.id = ?1")
	public Subject findSubjectByStudetId(Long id);
}

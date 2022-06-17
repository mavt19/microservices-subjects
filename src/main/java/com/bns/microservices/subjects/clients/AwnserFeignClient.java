package com.bns.microservices.subjects.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservices-answers")
public interface AwnserFeignClient {

	@GetMapping("/student/{studentId}/exams-answered")
	public Iterable<Long> getAnswerIdWithAnswersByStudent(@PathVariable("studentId") Long studentId);
}

package com.bns.microservices.subjects.models.entity;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "exams")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exam {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String name;
	
	@CreationTimestamp
    @Column(updatable=false, name = "create_at")
    private OffsetDateTime createAt;
	
	@OneToMany(mappedBy = "exam",  cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties(value = {"exam"}, allowSetters = true)
	private List<Question> questions = new ArrayList<>();

	@Transient
	private boolean answered;
}

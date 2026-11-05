package com.coder.fe_be.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
public class Student {

	@Schema(description = "Unique ID of the student", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Schema(description = "Full name of the student", example = "Rahul Kumar", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank(message = "Student name is required")
	@Size(min = 3, max = 50, message = "Name must contain 3 to 50 characters")
	private String name;

	@Schema(description = "Email address of the student", example = "rahul@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank(message = "Email is required")
	@Email(message = "Please enter a valid email address")
	@Column(nullable = false, length = 254)
	private String email;

	@Schema(description = "Course enrolled by the student", example = "Java", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank(message = "Course is required")
	@Size(min = 2, max = 50, message = "Course must contain 2 to 50 characters")
	private String course;

	public Student() {
	}

	public Student(String name, String email, String course) {
		this.name = name;
		this.email = email;
		this.course = course;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}
}

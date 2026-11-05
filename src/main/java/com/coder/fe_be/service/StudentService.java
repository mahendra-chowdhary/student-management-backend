package com.coder.fe_be.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.coder.fe_be.exception.StudentNotFoundException;
import com.coder.fe_be.dto.StudentPatchRequest;
import com.coder.fe_be.dto.StudentRequestDTO;
import com.coder.fe_be.dto.StudentResponseDTO;
import com.coder.fe_be.entity.Student;
import org.springframework.transaction.annotation.Transactional;
import com.coder.fe_be.repository.StudentRepository;

@Service
public class StudentService {

	private final StudentRepository repository;

	public StudentService(StudentRepository repository) {
		this.repository = repository;
	}

	// GET all students
	@Transactional(readOnly = true)
	public List<StudentResponseDTO> getAllStudents() {

		return repository.findAll().stream().map(student -> new StudentResponseDTO(student.getId(), student.getName(),
				student.getEmail(), student.getCourse())).toList();
	}

	// GET student by ID
	@Transactional(readOnly = true)
	public StudentResponseDTO getStudentById(Long id) {

		Student student = repository.findById(id)
				.orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + id));

		return new StudentResponseDTO(student.getId(), student.getName(), student.getEmail(), student.getCourse());
	}

	// POST - Add student
	@Transactional
	public StudentResponseDTO addStudent(StudentRequestDTO request) {

	    Student student = new Student();

	    student.setName(request.getName());
	    student.setEmail(request.getEmail());
	    student.setCourse(request.getCourse());

	    Student savedStudent = repository.save(student);

	    return new StudentResponseDTO(
	            savedStudent.getId(),
	            savedStudent.getName(),
	            savedStudent.getEmail(),
	            savedStudent.getCourse()
	    );
	}

	// PUT - Update complete student
	@Transactional
	public StudentResponseDTO updateStudent(
	        Long id, StudentRequestDTO request) {

	    Student existing = repository.findById(id)
	            .orElseThrow(() -> new StudentNotFoundException(
	                    "Student not found with ID: " + id
	            ));

	    existing.setName(request.getName());
	    existing.setEmail(request.getEmail());
	    existing.setCourse(request.getCourse());

	    Student updatedStudent = repository.save(existing);

	    return new StudentResponseDTO(
	            updatedStudent.getId(),
	            updatedStudent.getName(),
	            updatedStudent.getEmail(),
	            updatedStudent.getCourse()
	    );
	}

	// PATCH - Update selected fields
	@Transactional
	public StudentResponseDTO patchStudent(
	        Long id, StudentPatchRequest request) {

	    Student existing = repository.findById(id)
	            .orElseThrow(() -> new StudentNotFoundException(
	                    "Student not found with ID: " + id
	            ));

	    if (request.getName() != null) {
	        existing.setName(request.getName());
	    }

	    if (request.getEmail() != null) {
	        existing.setEmail(request.getEmail());
	    }

	    if (request.getCourse() != null) {
	        existing.setCourse(request.getCourse());
	    }

	    Student updatedStudent = repository.save(existing);

	    return new StudentResponseDTO(
	            updatedStudent.getId(),
	            updatedStudent.getName(),
	            updatedStudent.getEmail(),
	            updatedStudent.getCourse()
	    );
	}

	// DELETE - Remove student
	@Transactional
	public void deleteStudent(Long id) {

		if (!repository.existsById(id)) {
			throw new StudentNotFoundException("Student not found with ID: " + id);
		}

		repository.deleteById(id);
	}
}
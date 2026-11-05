package com.coder.fe_be.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coder.fe_be.dto.StudentPatchRequest;
import com.coder.fe_be.dto.StudentRequestDTO;
import com.coder.fe_be.dto.StudentResponseDTO;
import com.coder.fe_be.response.ApiResponseWrapper;
import com.coder.fe_be.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Student Management", description = "APIs for managing student records")
public class StudentController {

	private final StudentService service;

	public StudentController(StudentService service) {
		this.service = service;
	}

	// GET - Fetch all students
	@GetMapping
	@Operation(summary = "Get all students", description = "Retrieves the list of all registered students.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Students retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseWrapper.class), examples = @ExampleObject(name = "Successful Response", value = """
					{
					    "success": true,
					    "message": "Students retrieved successfully",
					    "data": [
					        {
					            "id": 1,
					            "name": "Rahul",
					            "email": "rahul@gmail.com",
					            "course": "Java"
					        }
					    ]
					}
					"""))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Internal Server Error", value = """
					{
					    "success": false,
					    "message": "Something went wrong. Please try again later.",
					    "data": null
					}
					"""))) })
	public ResponseEntity<ApiResponseWrapper<List<StudentResponseDTO>>> getAllStudents() {

		ApiResponseWrapper<List<StudentResponseDTO>> response = new ApiResponseWrapper<>(true,
				"Students retrieved successfully", service.getAllStudents());

		return ResponseEntity.ok(response);
	}

	// GET - Fetch student by ID
	@GetMapping("/{id}")
	@Operation(summary = "Get student by ID", description = "Retrieves student details using the student ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Student retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseWrapper.class), examples = @ExampleObject(name = "Successful Response", value = """
					{
					    "success": true,
					    "message": "Student retrieved successfully",
					    "data": {
					        "id": 1,
					        "name": "Rahul",
					        "email": "rahul@gmail.com",
					        "course": "Java"
					    }
					}
					"""))),
			@ApiResponse(responseCode = "404", description = "Student not found", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Student Not Found", value = """
					{
					    "success": false,
					    "message": "Student not found with ID: 9999",
					    "data": null
					}
					"""))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Internal Server Error", value = """
					{
					    "success": false,
					    "message": "Something went wrong. Please try again later.",
					    "data": null
					}
					"""))) })
	public ResponseEntity<ApiResponseWrapper<StudentResponseDTO>> getStudentById(
			@Parameter(description = "Unique ID of the student", example = "1", required = true) @PathVariable Long id) {

		ApiResponseWrapper<StudentResponseDTO> response = new ApiResponseWrapper<>(true,
				"Student retrieved successfully", service.getStudentById(id));

		return ResponseEntity.ok(response);
	}

	// POST - Add new student
	@PostMapping
	@Operation(summary = "Add a new student", description = "Creates a new student record in the database.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Student created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseWrapper.class), examples = @ExampleObject(name = "Student Created", value = """
					{
					    "success": true,
					    "message": "Student created successfully",
					    "data": {
					        "id": 2,
					        "name": "Kiran",
					        "email": "kiran@gmail.com",
					        "course": "Spring Boot"
					    }
					}
					"""))),
			@ApiResponse(responseCode = "400", description = "Student validation failed", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Validation Error", value = """
					{
					    "success": false,
					    "message": "Validation failed",
					    "data": {
					        "name": "Student name is required",
					        "email": "Please enter a valid email address",
					        "course": "Course is required"
					    }
					}
					"""))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Internal Server Error", value = """
					{
					    "success": false,
					    "message": "Something went wrong. Please try again later.",
					    "data": null
					}
					"""))) })
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Student details to create a new student", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = StudentRequestDTO.class), examples = @ExampleObject(name = "New Student", value = """
			{
			    "name": "Rahul",
			    "email": "rahul@gmail.com",
			    "course": "Java"
			}
			""")))
	public ResponseEntity<ApiResponseWrapper<StudentResponseDTO>> addStudent(
			@Valid @RequestBody StudentRequestDTO student) {

		StudentResponseDTO savedStudent = service.addStudent(student);

		ApiResponseWrapper<StudentResponseDTO> response = new ApiResponseWrapper<>(true, "Student created successfully",
				savedStudent);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// PUT - Update complete student
	@PutMapping("/{id}")
	@Operation(summary = "Update student", description = "Updates all fields of an existing student using the student ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Student updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseWrapper.class), examples = @ExampleObject(name = "Student Updated", value = """
					{
					    "success": true,
					    "message": "Student updated successfully",
					    "data": {
					        "id": 1,
					        "name": "Rahul Kumar",
					        "email": "rahulkumar@gmail.com",
					        "course": "Spring Boot"
					    }
					}
					"""))),
			@ApiResponse(responseCode = "400", description = "Invalid student data", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Validation Error", value = """
					{
					    "success": false,
					    "message": "Validation failed",
					    "data": {
					        "name": "Student name is required"
					    }
					}
					"""))),
			@ApiResponse(responseCode = "404", description = "Student not found", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Student Not Found", value = """
					{
					    "success": false,
					    "message": "Student not found with ID: 9999",
					    "data": null
					}
					"""))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Internal Server Error", value = """
					{
					    "success": false,
					    "message": "Something went wrong. Please try again later.",
					    "data": null
					}
					"""))) })
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Complete student details to update an existing student", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = StudentRequestDTO.class), examples = @ExampleObject(name = "Update Student", value = """
			{
			    "name": "Rahul Kumar",
			    "email": "rahul@gmail.com",
			    "course": "Spring Boot"
			}
			""")))
	public ResponseEntity<ApiResponseWrapper<StudentResponseDTO>> updateStudent(
			@Parameter(description = "Unique ID of the student", example = "1", required = true) @PathVariable Long id,
			@Valid @RequestBody StudentRequestDTO student) {

		StudentResponseDTO updatedStudent = service.updateStudent(id, student);

		ApiResponseWrapper<StudentResponseDTO> response = new ApiResponseWrapper<>(true, "Student updated successfully",
				updatedStudent);

		return ResponseEntity.ok(response);
	}

	// PATCH - Update selected fields
	@PatchMapping("/{id}")
	@Operation(summary = "Partially update student", description = "Updates only the provided fields of an existing student.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Student partially updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseWrapper.class), examples = @ExampleObject(name = "Student Partially Updated", value = """
					{
					    "success": true,
					    "message": "Student partially updated successfully",
					    "data": {
					        "id": 1,
					        "name": "Rahul Kumar",
					        "email": "rahulkumar@gmail.com",
					        "course": "Microservices"
					    }
					}
					"""))),
			@ApiResponse(responseCode = "400", description = "Invalid student data", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Validation Error", value = """
					{
					    "success": false,
					    "message": "Validation failed",
					    "data": {
					        "course": "Course cannot be blank"
					    }
					}
					"""))),
			@ApiResponse(responseCode = "404", description = "Student not found", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Student Not Found", value = """
					{
					    "success": false,
					    "message": "Student not found with ID: 9999",
					    "data": null
					}
					"""))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Internal Server Error", value = """
					{
					    "success": false,
					    "message": "Something went wrong. Please try again later.",
					    "data": null
					}
					"""))) })
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Provide only the student fields that need to be updated", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = StudentPatchRequest.class), examples = @ExampleObject(name = "Partial Student Update", value = """
			{
			    "course": "Spring Boot"
			}
			""")))
	public ResponseEntity<ApiResponseWrapper<StudentResponseDTO>> patchStudent(
			@Parameter(description = "Unique ID of the student", example = "1", required = true) @PathVariable Long id,
			@Valid @RequestBody StudentPatchRequest student) {

		StudentResponseDTO updatedStudent = service.patchStudent(id, student);

		ApiResponseWrapper<StudentResponseDTO> response = new ApiResponseWrapper<>(true,
				"Student partially updated successfully", updatedStudent);

		return ResponseEntity.ok(response);
	}

	// DELETE - Delete student
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete student", description = "Deletes an existing student using the student ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Student deleted successfully. No response body."),
			@ApiResponse(responseCode = "404", description = "Student not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseWrapper.class), examples = @ExampleObject(name = "Student Not Found", value = """
					{
					    "success": false,
					    "message": "Student not found with ID: 9999",
					    "data": null
					}
					"""))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Internal Server Error", value = """
					{
					    "success": false,
					    "message": "Something went wrong. Please try again later.",
					    "data": null
					}
					"""))) })
	public ResponseEntity<Void> deleteStudent(
			@Parameter(description = "Unique ID of the student", example = "1", required = true) @PathVariable Long id) {

		service.deleteStudent(id);

		return ResponseEntity.noContent().build();
	}
}

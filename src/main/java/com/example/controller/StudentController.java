package com.example.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Student;
import com.example.service.StudentService;

@RestController
@RequestMapping("/api/student")
public class StudentController {

	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@PostMapping("/create")
	public Student createStudent(@RequestBody Student student) {
		return studentService.createStudent(student);
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable String id) {
		Optional<Student> studentById = studentService.getStudentById(id);
		return studentById.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
	}

	@GetMapping("/all")
	public List<Student> getAllStudents() {
		return studentService.getAllStudents();
	}

	@PutMapping("/update")
	public Student updateStudent(@RequestBody Student student) {
		return studentService.updateStudent(student);
	}

	@DeleteMapping("/delete/{id}")
	public String deleteStudent(@PathVariable String id) {
		return studentService.deleteStudent(id);
	}

	@GetMapping("/studentsByName/{name}")
	public ResponseEntity<List<Student>> studentsByName(@PathVariable String name) {
		List<Student> students = studentService.getStudentsByName(name);
		return students.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(students);
	}

	@GetMapping("/studentsByNameAndMail")
	public ResponseEntity<Student> studentsByNameAndMail(@RequestParam String name,
			@RequestParam String email) {
		Optional<Student> student = studentService.studentsByNameAndMail(name, email);
		return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
	}

	@GetMapping("/studentsByNameOrMail")
	public ResponseEntity<Student> studentsByNameOrMail(@RequestParam String name,
			@RequestParam String email) {
		Optional<Student> student = studentService.studentsByNameOrMail(name, email);
		return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
	}

	@GetMapping("/allWithPagination")
	public List<Student> getAllWithPagination(@RequestParam int pageNo,
			@RequestParam int pageSize) {
		return studentService.getAllWithPagination(pageNo, pageSize);
	}

	@GetMapping("/allWithSorting")
	public List<Student> allWithSorting() {
		return studentService.allWithSorting();
	}

	@GetMapping("/byDepartmentName")
	public ResponseEntity<List<Student>> byDepartmentName(@RequestParam String deptName) {
		List<Student> students = studentService.byDepartmentName(deptName);
		return students.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(students);
	}

	@GetMapping("/bySubjectName")
	public ResponseEntity<List<Student>> bySubjectName(@RequestParam String subName) {
		List<Student> students = studentService.bySubjectName(subName);
		return students.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(students);
	}

	@GetMapping("/emailLike")
	public ResponseEntity<List<Student>> emailLike(@RequestParam String email) {
		List<Student> students = studentService.emailLike(email);
		return students.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(students);
	}

	@GetMapping("/nameStartsWith")
	public ResponseEntity<List<Student>> nameStartsWith(@RequestParam String name) {
		List<Student> students = studentService.nameStartsWith(name);
		return students.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(students);
	}
}

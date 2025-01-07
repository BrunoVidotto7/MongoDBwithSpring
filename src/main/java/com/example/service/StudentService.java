package com.example.service;

import java.util.Collections;
import java.util.List;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.entity.Student;
import com.example.repository.StudentRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {

	private static final Logger log = LoggerFactory.getLogger(StudentService.class);
	private final StudentRepository studentRepository;

  public StudentService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

	@Transactional
  public Student createStudent(Student student) {
		log.info("Creating student: {}", student);
		return studentRepository.save(student);
	}

	public Optional<Student> getStudentById(String id) {
		if (isNullOrEmpty(id)) {
			throw new IllegalArgumentException("ID cannot be null or blank");
		}
		log.info("Fetching student with ID: {}", id);
		return studentRepository.findById(id);
	}
	
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	@Transactional
	public Student updateStudent(Student student) {
		if (!studentRepository.existsById(student.getId())) {
			throw new IllegalArgumentException("Student with ID " + student.getId() + " does not exist.");
		}
		log.info("Updating student: {}", student);
		return studentRepository.save(student);
	}

	@Transactional
	public String deleteStudent (String id) {
		log.info("Deleting student with ID: {}", id);
		studentRepository.deleteById(id);
		log.info("Student with ID {} deleted successfully", id);
		return "Student has been deleted.";
	}
	
	public List<Student> getStudentsByName(String name) {
		log.info("Fetching students with name: {}", name);
		return studentRepository.findByName(name);
	}

	public Optional<Student> studentsByNameAndMail(String name, String email) {
		log.info("Fetching student by name '{}' and email '{}'", name, email);

		if (isNullOrEmpty(name) || isNullOrEmpty(email)) {
			log.warn("Invalid input: name or email is null/empty");
			return Optional.empty();
		}

		Optional<Student> result = Optional.ofNullable(studentRepository.findByEmailAndName(email, name));
		log.info("Result for student by name '{}' and email '{}': {}", name, email, result.orElse(null));
		return result;
	}

	public Optional<Student> studentsByNameOrMail(String name, String email) {
		log.info("Fetching student by name '{}' or email '{}'", name, email);

		if (isNullOrEmpty(name) && isNullOrEmpty(email)) {
			log.warn("Invalid input: both name and email are null/empty");
			return Optional.empty();
		}

		Optional<Student> result = Optional.ofNullable(studentRepository.findByNameOrEmail(name, email));
		log.info("Result for student by name '{}' or email '{}': {}", name, email, result.orElse(null));
		return result;
	}

	public List<Student> getAllWithPagination(int pageNo, int pageSize) {
		log.info("Fetching all students with pagination: pageNo={}, pageSize={}", pageNo, pageSize);

		if (pageNo < 1 || pageSize < 1) {
			log.error("Invalid pagination parameters: pageNo={}, pageSize={}", pageNo, pageSize);
			throw new IllegalArgumentException("Page number and page size must be greater than 0");
		}

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<Student> result = studentRepository.findAll(pageable).getContent();
		log.info("Fetched {} students with pagination: pageNo={}, pageSize={}", result.size(), pageNo, pageSize);
		return result;
	}

	public List<Student> allWithSorting(String... sortProperties) {
		log.info("Fetching all students with sorting by properties: {}", (Object[]) sortProperties);

		if (sortProperties == null || sortProperties.length == 0) {
			log.warn("No sort properties provided, defaulting to ['name', 'email']");
			sortProperties = new String[]{"name", "email"};
		}

		Sort sort = Sort.by(Sort.Direction.ASC, sortProperties);
		List<Student> result = studentRepository.findAll(sort);
		log.info("Fetched {} students sorted by properties: {}", result.size(), sortProperties);
		return result;
	}

	public List<Student> byDepartmentName(String deptName) {
		log.info("Fetching students by department name: '{}'", deptName);

		if (isNullOrEmpty(deptName)) {
			log.warn("Invalid input: department name is null/empty");
			return Collections.emptyList();
		}

		List<Student> result = studentRepository.findByDepartmentDepartmentName(deptName);
		log.info("Fetched {} students by department name: '{}'", result.size(), deptName);
		return result;
	}

	public List<Student> bySubjectName(String subName) {
		log.info("Fetching students by subject name: '{}'", subName);

		if (isNullOrEmpty(subName)) {
			log.warn("Invalid input: subject name is null/empty");
			return Collections.emptyList();
		}

		List<Student> result = studentRepository.findBySubjectsSubjectName(subName);
		log.info("Fetched {} students by subject name: '{}'", result.size(), subName);
		return result;
	}

	public List<Student> emailLike(String email) {
		log.info("Fetching students with email like: '{}'", email);

		if (isNullOrEmpty(email)) {
			log.warn("Invalid input: email is null/empty");
			return Collections.emptyList();
		}

		List<Student> result = studentRepository.findByEmailIsLike(email);
		log.info("Fetched {} students with email like: '{}'", result.size(), email);
		return result;
	}

	public List<Student> nameStartsWith(String name) {
		log.info("Fetching students whose name starts with: '{}'", name);

		if (isNullOrEmpty(name)) {
			log.warn("Invalid input: name is null/empty");
			return Collections.emptyList();
		}

		List<Student> result = studentRepository.findByNameStartsWith(name);
		log.info("Fetched {} students whose name starts with: '{}'", result.size(), name);
		return result;
	}

	/**
	 * Utility method to check if a string is null or empty.
	 */
	private boolean isNullOrEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}

}

package bt.pre.redriver.student.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import bt.pre.redriver.student.demo.criteria.StudentSearchCriteria;
import bt.pre.redriver.student.demo.dto.StudentsDto;
import bt.pre.redriver.student.demo.entity.Student;
import bt.pre.redriver.student.demo.service.StudentService;
import bt.pre.redriver.student.demo.validation.StudentValidationError;

@RestController
@RequestMapping("api/students")
public class StudentController {
	private final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private StudentService studentService;

	@GetMapping("")
	@ResponseBody
	public List<Student> getStudents() {
		this.logger.info("Fetching students");
//		return studentService.findAll().stream().sorted(Comparator.comparing(Student::getId).reversed()).collect(Collectors.toList());
		return studentService.findAll();
	}

	@GetMapping("/search")
	@ResponseBody
	public StudentsDto searchStudents(@RequestParam Integer currentPage, @RequestParam Integer pageSize, @RequestParam String firstName,
			@RequestParam String lastName) {
		this.logger.info("Search student by criteria. CurrentPage: " + currentPage + ", PageSize: " + pageSize + ", FirstName: " + firstName + ", LastName: "
				+ lastName);
		StudentSearchCriteria studentSearchCriteria = new StudentSearchCriteria(pageSize, currentPage, firstName, lastName);
		return this.studentService.findBySearchCriteria(studentSearchCriteria);
	}

	@GetMapping("/{id}")
	@ResponseBody
	public Student getStudent(@PathVariable Long id) {
		this.logger.info("Fetching student ID: " + id);
		return this.studentService.findById(id);
	}

	@PostMapping("")
	@ResponseBody
	public Student createStudent(@Valid @RequestBody Student student, Errors errors) {
		if (errors.hasErrors()) {
			throw new IllegalArgumentException(getErrorMessages(errors));
		}
		this.logger.info("Creating a new student. " + student.toString());
		return this.studentService.create(student);
	}

	@PutMapping("/{id}")
	@ResponseBody
	public Student saveStudent(@PathVariable Long id, @Valid @RequestBody Student student, Errors errors) {
		if (errors.hasErrors()) {
			throw new IllegalArgumentException(getErrorMessages(errors));
		}
		this.logger.info("Update a student. id: " + id + ", student: " + student.toString());
		return this.studentService.update(id, student);
	}

	private String getErrorMessages(Errors errors) {
		if (errors == null) {
			return "";
		}
		return errors.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList()).toString();
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	public boolean deleteStudent(@PathVariable Long id) {
		this.logger.info("Delete a student. id: " + id);
		return this.studentService.delete(id);
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public StudentValidationError handleException(Exception exception) {
		return new StudentValidationError(exception.getMessage());
	}

}

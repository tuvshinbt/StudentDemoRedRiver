package bt.pre.redriver.student.demo.service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import bt.pre.redriver.student.demo.criteria.StudentSearchCriteria;
import bt.pre.redriver.student.demo.dto.StudentsDto;
import bt.pre.redriver.student.demo.entity.Student;
import bt.pre.redriver.student.demo.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	private final Logger logger = LogManager.getLogger(this.getClass());
	private final Integer INVALID_ID = 0;

	@Autowired
	private StudentRepository studentRepository;

	@Override
	public List<Student> findAll() {
		this.logger.info("Find all student.");
		return this.studentRepository.findAll();
	}

	@Override
	public StudentsDto findBySearchCriteria(StudentSearchCriteria studentSearchCriteria) {
		if (studentSearchCriteria == null || studentSearchCriteria.getPageSize() < 0 || studentSearchCriteria.getCurrentPage() < 0) {
			throw new IllegalArgumentException("Invalid search criteria!");
		}
		// Create Example & Matcher
		ExampleMatcher customExampleMatcher = ExampleMatcher.matchingAny().withMatcher("firstName", match -> match.contains())
				.withMatcher("lastName", match -> match.contains()).withIgnorePaths("id", "dob");
		// Select a demo student
		Student demoStudent = this.createDemoStudent(studentSearchCriteria);
		Example<Student> studentExample = Example.of(demoStudent, customExampleMatcher);
		// Create a pageable
		Pageable pageable = PageRequest.of(studentSearchCriteria.getCurrentPage(), studentSearchCriteria.getPageSize(), Sort.by(Sort.Direction.DESC, "id"));
		// Execute a query
		Page<Student> pageStudent = this.studentRepository.findAll(studentExample, pageable);
		this.logger.info("Page student:" + pageStudent);

		// Initiate a result
		if (pageStudent == null) {
			throw new IllegalArgumentException("Repository operation exception!");
		}
		StudentsDto studentsDto = new StudentsDto(pageStudent.getContent(), pageStudent.getTotalElements(), pageStudent.getTotalPages(),
				pageStudent.getNumber(), pageStudent.getSize());
		return studentsDto;
	}

	private Student createDemoStudent(StudentSearchCriteria studentSearchCriteria) {
		Student demoStudent = new Student();
		if (!studentSearchCriteria.getFirstName().isEmpty()) {
			demoStudent.setFirstName(studentSearchCriteria.getFirstName());
		}
		if (!studentSearchCriteria.getLastName().isEmpty()) {
			demoStudent.setLastName(studentSearchCriteria.getLastName());
		}
		return demoStudent;
	}

	@Override
	public Student findById(long id) {
		if (id <= INVALID_ID) {
			throw new IllegalArgumentException("Invalid student id!");
		}
		Optional<Student> result = this.studentRepository.findById(id);
		Student student = result.orElseThrow(() -> new NoSuchElementException("Not found student with id: " + id));
		return student;
	}

	@Override
	public Student create(Student student) {
		if (student == null) {
			throw new IllegalArgumentException("Student is null!");
		} else if (student.getId() != 0) {
			throw new IllegalArgumentException("Student has id!");
		}
		return this.studentRepository.save(student);
	}

	@Override
	public Student update(long id, Student student) {
		if (student == null) {
			throw new IllegalArgumentException("Student is null!");
		}
		if (id <= INVALID_ID || !this.studentRepository.existsById(id) || id != student.getId()) {
			throw new IllegalArgumentException("Invalid student id!");
		}
		return this.studentRepository.save(student);
	}

	@Override
	public boolean delete(long id) {
		if (id <= INVALID_ID) {
			throw new IllegalArgumentException("Invalid student id!");
		}
		this.studentRepository.deleteById(id);
		return true;
	}

}

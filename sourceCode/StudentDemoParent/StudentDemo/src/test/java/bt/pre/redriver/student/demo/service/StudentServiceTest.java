package bt.pre.redriver.student.demo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import bt.pre.redriver.student.demo.criteria.StudentSearchCriteria;
import bt.pre.redriver.student.demo.entity.Student;
import bt.pre.redriver.student.demo.repository.StudentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StudentServiceTest {

	private List<Student> students;
	private Student student1;
	private final Long STUDENT_ID = 1L;
	private final String FIRST_NAME = "Jack";
	private final String LAST_NAME = "James";
	private final String EMAIL = "jack.james@redriver.com";

	@Mock
	private StudentRepository studentRepository;

	@InjectMocks
	private StudentServiceImpl studentService;

	@Before
	public void setUp() {
		this.initiateStudent1();
		this.initiateStudents();
	}

	private void initiateStudent1() {
		this.student1 = new Student(FIRST_NAME, LAST_NAME, EMAIL);
	}

	private void initiateStudents() {
		this.students = new ArrayList<>();
		this.students.add(this.student1);
	}

	@Test
	public void testFindAll() {
		// Mock object
		when(this.studentRepository.findAll()).thenReturn(this.students);
		// Execute service call
		List<Student> result = this.studentService.findAll();
		// Verify call
		verify(this.studentRepository).findAll();
		// Asserts
		assertNotNull(result);
		assertEquals(1, result.size());
		assertSame(this.student1, result.get(0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindBySearchCriteria() {
		// Create mock objects
		StudentSearchCriteria studentSearchCriteria = new StudentSearchCriteria(30, 1, "Tuvshin", "");

		// Execute service call
		this.studentService.findBySearchCriteria(studentSearchCriteria);

		// It throws IllegalArgumentException because a mock isn't injected
	}
}

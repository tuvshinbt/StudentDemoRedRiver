package bt.pre.redriver.student.demo.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import bt.pre.redriver.student.demo.criteria.StudentSearchCriteria;
import bt.pre.redriver.student.demo.dto.StudentsDto;
import bt.pre.redriver.student.demo.entity.Student;
import bt.pre.redriver.student.demo.service.StudentService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

	private final static String URI = "/api/students";
	private final static String URI_SEARCH = URI + "/search";
	private final static String URI_STUDENT_BY_ID = URI + "/1";
	private final static String URI_NO_STUDENT_BY_ID = URI + "/0";

	private List<Student> students;
	private Student student1;
	private final Long STUDENT_ID = 1L;
	private final String FIRST_NAME = "Jack";
	private final String LAST_NAME = "James";
	private final String EMAIL = "jack.james@redriver.com";

	@Autowired
	private MockMvc mvc;

	@MockBean
	private StudentService studentService;

	@InjectMocks
	private StudentController studentController;

	@Test
	@Ignore
	public void testIndexPage() throws Exception {
		this.mvc.perform(get("")).andExpect(status().is4xxClientError());
	}

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
	public void testGetStudentList() throws Exception {
		// Mock object
		when(this.studentService.findAll()).thenReturn(this.students);
		// Perform HTTP Call
		this.mvc.perform(get(URI)).andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$[0].firstName").value("Jack")).andExpect(jsonPath("$[0].lastName").value("James")).andReturn();
		// verify
		verify(this.studentService).findAll();
	}

	@Test
//	@Ignore
	public void testGetStudentListBySearchCriteria() throws Exception {
		// Mock object
		StudentSearchCriteria studentSearchCriteria = new StudentSearchCriteria(30, 0, FIRST_NAME, LAST_NAME);
		StudentsDto studentsDto = new StudentsDto(students, 1, 1, 0, 30);
		when(this.studentService.findBySearchCriteria(studentSearchCriteria)).thenReturn(studentsDto);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("currentPage", "0");
		params.add("pageSize", "30");
		params.add("firstName", FIRST_NAME);
		params.add("lastName", LAST_NAME);
		// Perform HTTP Call
		this.mvc.perform(get(URI_SEARCH).params(params)).andDo(print()).andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$[0].firstName").value(FIRST_NAME))
//				.andExpect(jsonPath("$[0].lastName").value(LAST_NAME)).andReturn()
		;
	}

	@Test
	public void testGetStudent() throws Exception {
		// Mock object
		when(this.studentService.findById(STUDENT_ID)).thenReturn(this.student1);
		// Perform HTTP Call
		this.mvc.perform(get(URI_STUDENT_BY_ID)).andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
	}

	@Test
	public void testGetNoStudent() throws Exception {
		// Perform HTTP Call
		this.mvc.perform(get(URI_NO_STUDENT_BY_ID)).andDo(print()).andExpect(status().isOk());
	}

}

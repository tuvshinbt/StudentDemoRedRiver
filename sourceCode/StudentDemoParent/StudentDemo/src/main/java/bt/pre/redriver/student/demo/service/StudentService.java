package bt.pre.redriver.student.demo.service;

import java.util.List;

import bt.pre.redriver.student.demo.criteria.StudentSearchCriteria;
import bt.pre.redriver.student.demo.dto.StudentsDto;
import bt.pre.redriver.student.demo.entity.Student;

/**
 * Service layer for Student domain
 *
 */
public interface StudentService {

	List<Student> findAll();

	StudentsDto findBySearchCriteria(StudentSearchCriteria studentSearchCriteria);

	Student findById(final long id);

	Student create(Student student);

	Student update(final long id, final Student student);

	boolean delete(final long id);

}

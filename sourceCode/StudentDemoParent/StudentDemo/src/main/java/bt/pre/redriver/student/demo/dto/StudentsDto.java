package bt.pre.redriver.student.demo.dto;

import java.util.List;

import bt.pre.redriver.student.demo.entity.Student;

public class StudentsDto {

	private List<Student> students;
	private long totalElement;
	private int totalPage;
	private int currentPage;
	private int pageSize;

	public StudentsDto(List<Student> students, long totalElement, int totalPage, int currentPage, int pageSize) {
		this.students = students;
		this.totalElement = totalElement;
		this.totalPage = totalPage;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public long getTotalElement() {
		return totalElement;
	}

	public void setTotalElement(long totalElement) {
		this.totalElement = totalElement;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}

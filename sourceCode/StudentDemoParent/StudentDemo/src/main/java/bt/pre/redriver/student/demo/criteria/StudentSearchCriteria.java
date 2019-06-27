package bt.pre.redriver.student.demo.criteria;

public class StudentSearchCriteria extends SearchCriteria {
	private String firstName;
	private String lastName;

	public StudentSearchCriteria(int pageSize, int currentPage, String firstName, String lastName) {
		super(pageSize, currentPage);
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

}

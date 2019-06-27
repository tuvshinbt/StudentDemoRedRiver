package bt.pre.redriver.student.demo.validation;

public abstract class ValidationError {

	private String errorMessage;

	public ValidationError(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}

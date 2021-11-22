package by.itacademy.javaenterprise.knyazev.dao.exceptions;

public abstract class ExceptionDAO extends Exception {
	
	private static final long serialVersionUID = 6528834898727173442L;
	
	private static final String DEFAULT = "Unknown error was occured id DAO class.";
	
	
	ExceptionDAO(String message) {
		super(message);
	}
	
	ExceptionDAO(String message, Throwable cause) {
		super(message, cause);
	}
	
	ExceptionDAO() {
		super(DEFAULT);
	}
	
}

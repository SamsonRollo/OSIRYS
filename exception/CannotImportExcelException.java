package exception;

public class CannotImportExcelException extends Exception{
	public CannotImportExcelException(String message){
		super(message);
	}

	public CannotImportExcelException(){
		super("No Category Found!");
	}
}

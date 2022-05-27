package exception;

public class CategoryException extends Exception{
	public CategoryException(String message){
		super(message);
	}

	public CategoryException(){
		super("No Category Found!");
	}
}

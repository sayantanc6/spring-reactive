package dummy.exception;

public class ProductNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ProductNotFoundException(String i) {
        super("not found: "+i);
    }

}

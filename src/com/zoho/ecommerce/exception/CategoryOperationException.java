package src.com.zoho.ecommerce.exception;

public class CategoryOperationException extends EcommerceException {

    public CategoryOperationException(String message) {
        super(message);
    }
    public CategoryOperationException(String message, Throwable cause) {
        super(message, cause);
    }
    
}

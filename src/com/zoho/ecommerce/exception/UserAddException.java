package src.com.zoho.ecommerce.exception;

public class UserAddException extends EcommerceException {
    public UserAddException() {
        super();
    }

    public UserAddException(String message) {
        super(message);
    }

    public UserAddException(String message, Throwable cause) {
        super(message, cause);
    }
    
}

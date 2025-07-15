package src.com.zoho.ecommerce.exception;

import java.io.PrintStream;

public class EcommerceException extends Exception {

    public EcommerceException() {
        super();
    }

    public EcommerceException(String message) {
        super(message);
    }

    public EcommerceException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String toString() {
        return "EcommerceException [message=" + getMessage() + "]";
    }

    @Override
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
    }

    // No need to override getMessage() — super.getMessage() is already correct
}

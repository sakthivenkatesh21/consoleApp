package src.com.zoho.ecommerce.exception;

public  class ProductOperationException extends EcommerceException {

    public ProductOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
     
}


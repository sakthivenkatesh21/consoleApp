package src.com.zoho.ecommerce.exception;


    // Custom Exception Classes
    public  class ProductNotFoundException extends EcommerceException {
        public ProductNotFoundException(String message) {
            super(message);
        }
    }

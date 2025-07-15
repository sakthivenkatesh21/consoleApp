package src.com.zoho.ecommerce.exception;

    public  class CartOperationException extends EcommerceException 
    {
        public CartOperationException(String message) {
            super(message);
        }

        @Override
        public String getMessage() {
            return super.getMessage();
        }
        
    }
package src.com.zoho.ecommerce.view;

import src.com.zoho.ecommerce.interfaceController.Execute;

public class UserAccess {
    private Execute execute;

    public UserAccess(Execute execute) {
        this.execute = execute;
    }

    public void operation() {
        execute.operation();
    }
}

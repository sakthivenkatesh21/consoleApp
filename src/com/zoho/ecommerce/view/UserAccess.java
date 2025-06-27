package com.zoho.ecommerce.view;

import com.zoho.ecommerce.interfaceController.Execute;

public class UserAccess {
    private Execute execute;

    public UserAccess(Execute execute) {
        this.execute = execute;
    }

    public void operation() {
        execute.operation();
    }
}

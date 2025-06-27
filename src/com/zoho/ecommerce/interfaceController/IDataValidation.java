package com.zoho.ecommerce.interfaceController;

public interface IDataValidation {
        String name(String info);
        String email(String info);
        String phone(String info);
        String password(String info);
        String address(String info);
        String gender(String info);
}


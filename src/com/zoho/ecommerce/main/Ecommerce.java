package com.zoho.ecommerce.main;

import com.zoho.ecommerce.view.Navigation;

public class Ecommerce {
    public static void main(String[] args) {

        Navigation navigation = Navigation.getNavigation();
        navigation.menu();
    }
}

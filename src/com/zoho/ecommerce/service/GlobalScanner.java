package src.com.zoho.ecommerce.service;

import java.util.Scanner;

public class GlobalScanner {

    private GlobalScanner(){}
    private static Scanner sc;

    public static Scanner getScanner() {
        if (sc == null) {
            sc = new Scanner(System.in);
        }
        return sc;
    }
    public static  void exit(Scanner sc) {
        try (sc) {
            System.out.println("Thank you for using E - Commerce");
        }
        System.exit(0);
    }

}

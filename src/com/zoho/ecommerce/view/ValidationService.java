package com.zoho.ecommerce.view;

import com.zoho.ecommerce.interfaceController.IDataValidation;
import java.util.Scanner;

public class ValidationService implements IDataValidation {
	private final Scanner sc = GlobalScanner.getScanner();

	@Override
	public String name(String info) {
		String name;
		while (true) {
			System.out.print(info);
			name = sc.nextLine();
			if (name.matches("^[a-zA-Z\\s]{3,25}$")) {
				return name;
			} else {
				System.out.println("⚠️ -> Name should contain only alphabets and spaces, and be 6 to 40 characters long.");
			}
		}
	}

	@Override
	public String email(String info) {
		String email;
		while (true) {
			System.out.print(info);
			email = sc.nextLine();
			if (email.matches("^[a-z0-9]+@[a-z]+\\.[a-z]{2,3}$")) {
				return email;
			} else {
				System.out.println("⚠️ -> Mail should be lowercase format is .xxxx@gmail.com");
			}
		}
	}

	@Override
	public String phone(String info) {
		String mobile;
		while (true) {
			System.out.print(info);
			mobile = sc.nextLine();
			if (mobile.matches("^[6-9]\\d{9}$")) {
				return mobile;
			} else {
				System.out.println("⚠️ -> Number should contain 10 digits and start from 6 to 9");
			}
		}
	}

	@Override
	public String password(String info) {
		String pass;
		while (true) {
			System.out.print(info);
			pass = sc.nextLine();
			if (pass.matches("^(?=.*[0-9A-Za-z*.!@$%^&*]).{8,15}$")) {
				System.out.println("🔄 Re-enter to confirm your Password:");
				if (pass.equals(sc.nextLine())) {
					return pass;
				} else {
					System.out.println("⚠️ -> Password should contain (Minimum 8 to Maximum 15 Characters)...");
				}
			}
		}
	}

	@Override
	public String address(String info) {
		String address;
		while (true) {
			System.out.print(info);
			address = sc.nextLine();
			if (address.matches("^[a-zA-Z0-9/,-.\\s]{6,50}$")) {
				return address;
			} else {
				System.out.println("⚠️ -> Characters should be 10 to 50 characters (letters, numbers, / , - . , and spaces).");
			}
		}
	}

    @Override
	public String gender(String info) {
		System.out.println("1️⃣ Male\n2️⃣ Female\n3️⃣ Other");
		System.out.print(info);
            return switch (sc.nextLine()) {
                case "1" -> "Male";
                case "2" -> "Female";
                default -> "Other";
            };
	}
}
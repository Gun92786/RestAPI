package com.lng.assignment2.validation;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.http.HttpStatus;
import com.lng.assignment2.exception.InvalidCustomException;

public class Validations {
	public static boolean validatePath(String filePath) throws InvalidCustomException {
		int dotIndex = filePath.lastIndexOf(".") + 1;
		if (filePath.substring(dotIndex, filePath.length()).equals("csv")) {
			File file = new File(filePath);
			if (file.exists()) {
				return true;
			} else {
				throw new InvalidCustomException("File does not exist", HttpStatus.NOT_FOUND);
			}
		} else {
			throw new InvalidCustomException("File must be csv", HttpStatus.NOT_FOUND);
		}

	}

	public static boolean isValidRecord(String line) throws ParseException {
		String[] lineElements = line.split(",");
		if (lineElements.length == 8) {
			boolean userObj = validateLineElements(lineElements);
			return userObj;
		} else {
			return false;
		}
	}

	public static boolean validateLineElements(String[] lineElements) throws ParseException {
		// TODO Auto-generated method stub
		if (!validateName(lineElements[1]))
			return false;
		if (!validateEmail(lineElements[2]))
			return false;
		if (!validateMobileNo(lineElements[3]))
			return false;
		if (!validateDateOfBirth(lineElements[4])) {
			return false;
		}
		if (!validateCountry(lineElements[5]))
			return false;
		if (!validateState(lineElements[6]))
			return false;
		if (!validateCity(lineElements[7]))
			return false;

		return true;

	}

	private static boolean validateCity(String city) {
		return isString(city);
	}

	private static boolean validateState(String state) {
		return isString(state);
	}

	private static boolean validateCountry(String country) {
		return isString(country);
	}

	private static boolean validateDateOfBirth(String dateOfBirth) {
		try {
			Date parsedDate = new SimpleDateFormat("dd-MM-yyyy").parse(dateOfBirth);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private static boolean validateMobileNo(String mobileNo) {
		return mobileNo.matches("\\d{10}$");
	}

	private static boolean validateEmail(String email) {
		return email.matches("[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+");
	}

	private static boolean validateName(String name) {
		return isString(name);
	}

	public static boolean isString(String s) {
		return s.matches("([a-zA-Z]*$)");
	}

}

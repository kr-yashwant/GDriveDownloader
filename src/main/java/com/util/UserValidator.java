package com.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

public class UserValidator {
	public boolean isValidatedUser(String userName, String password) {
		boolean mark = false;
		Properties prop = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("appuser.properties");
		if(inputStream != null) {
			try {
				prop.load(inputStream);
			} catch (IOException e) {
				System.out.println("No users currently added to the system");
			} finally {
				try {
					inputStream.close();
				} catch (IOException e) {
					System.out.println("Problem occurred while closing the file stream");
				}
			}
		}
		if(prop.containsKey(userName)) {
			if(prop.getProperty(userName).equals(password)){
				mark = true;
			}
		}
		return mark;
	}
	
	public boolean validateExistingSession(String userName) {
		boolean mark = false;
		Properties prop = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("appuser.properties");
		if(inputStream != null) {
			try {
				prop.load(inputStream);
			} catch (IOException e) {
				System.out.println("No users currently added to the system");
			} finally {
				try {
					inputStream.close();
				} catch (IOException e) {
					System.out.println("Problem occurred while closing the file stream");
				}
			}
		}
		if(prop.containsKey(userName)) {
				mark = true;
		}
		return mark;
	}
	
	public void addUser(String username, String password) {
		try {
			Properties configProperty = new Properties();
			configProperty.setProperty(username, password);
			File file = new File("appuser.properties");
			FileOutputStream fileOut = new FileOutputStream(file);
			configProperty.store(fileOut, "sample properties");
			fileOut.close();
		} catch (FileNotFoundException e) {
			System.out.println("Problem occurred in adding user to the system");
		} catch (IOException e) {
			System.out.println("Problem occurred in adding user to the system");
		}
	}

}

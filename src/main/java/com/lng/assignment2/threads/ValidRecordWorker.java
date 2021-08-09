package com.lng.assignment2.threads;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.lng.assignment2.entity.User;
import com.lng.assignment2.repository.UserRepository;

@Service
public class ValidRecordWorker extends Thread {
	ArrayList<User> validRecords;
	UserRepository userRepositoryObj;

	public UserRepository getUserRepositoryObj() {
		return userRepositoryObj;
	}

	public void setUserRepositoryObj(UserRepository userRepositoryObj) {
		this.userRepositoryObj = userRepositoryObj;
	}

	public void run() {
		Iterator i = getValidRecords().iterator();
		while (i.hasNext()) {
			User tempUser = (User) i.next();
			if (userRepositoryObj.ifExistEmail(tempUser.getEmail())) {
				FileWriter fileWriterObj;
				try {
					fileWriterObj = new FileWriter(
							"C:\\Users\\Dell\\Downloads\\assignment2\\src\\main\\resources\\InvalidRecords.csv", true);
					BufferedWriter bufferWriterObj = new BufferedWriter(fileWriterObj);
					bufferWriterObj.write(tempUser.getName() + "," + tempUser.getEmail() + "," + tempUser.getMobileNo()
							+ "," + tempUser.getDateOfBirth() + "," + tempUser.getCountry() + "," + tempUser.getState()
							+ "," + tempUser.getCity() + "\n");
					bufferWriterObj.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Some exception occured in ValidRecordWorker");
				}
			} else {
				userRepositoryObj.save(tempUser);
			}
		}

	}

	public ArrayList<User> getValidRecords() {
		return validRecords;
	}

	public void setValidRecords(ArrayList<User> validRecords) {
		this.validRecords = validRecords;
	}
}

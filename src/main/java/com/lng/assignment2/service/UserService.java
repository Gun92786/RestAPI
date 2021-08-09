package com.lng.assignment2.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.lng.assignment2.entity.User;
import com.lng.assignment2.exception.InvalidCustomException;
import com.lng.assignment2.repository.UserRepository;
import com.lng.assignment2.requestMapper.RequestModelMapper;
import com.lng.assignment2.responseMapper.ResponseModelMapper;
import com.lng.assignment2.threads.InvalidRecordWorker;
import com.lng.assignment2.threads.ValidRecordWorker;
import com.lng.assignment2.validation.Validations;

@Service
public class UserService {

	@Autowired
	UserRepository userRepoObj;
	ArrayList<String> invalidRecordsList = new ArrayList<String>();
	ArrayList<User> validRecordsList = new ArrayList<User>();

	public ResponseEntity<String> processFile(RequestModelMapper request)
			throws IOException, InvalidCustomException, ParseException {
		String filePath = request.getFilePath();
		if (Validations.validatePath(filePath)) {
			readFile(filePath);
			InvalidRecordWorker invalidRecordWorkerObject = new InvalidRecordWorker();
			invalidRecordWorkerObject.setInvalidRecords(invalidRecordsList);
			invalidRecordWorkerObject.start();
			ValidRecordWorker validRecordWorkerObject = new ValidRecordWorker();
			validRecordWorkerObject.setUserRepositoryObj(userRepoObj);
			validRecordWorkerObject.setValidRecords(validRecordsList);
			validRecordWorkerObject.start();
			return new ResponseEntity<>("File processed Successfully", HttpStatus.CREATED);
		} else
			return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
	}

	private void readFile(String filePath) throws IOException, ParseException {
		File file = new File(filePath);
		FileReader fileReaderObj = new FileReader(file);
		BufferedReader bufferReaderObj = new BufferedReader(fileReaderObj);
		String line = bufferReaderObj.readLine();
		while ((line = bufferReaderObj.readLine()) != null) {
			boolean isValid = Validations.isValidRecord(line);
			if (isValid) {
				User userBean = gerUserBean(line);
				validRecordsList.add(userBean);
			} else {
				invalidRecordsList.add(line);
			}
		}
	}

	public User gerUserBean(String line) throws ParseException {
		String[] lineElements = line.split(",");
		User userObj = new User();
		userObj.setName(lineElements[1]);
		userObj.setEmail(lineElements[2]);
		userObj.setMobileNo(Long.valueOf(lineElements[3]));
		userObj.setMobileNo(Long.valueOf(lineElements[3]));
		userObj.setDateOfBirth(new SimpleDateFormat("dd-MM-yyyy").parse(lineElements[4]));
		userObj.setCountry(lineElements[5]);
		userObj.setState(lineElements[6]);
		userObj.setCity(lineElements[7]);
		return userObj;
	}

	public ResponseModelMapper getByNameAndEmail(String name, String email) throws InvalidCustomException {
		ResponseModelMapper responseMapperObj = new ResponseModelMapper();
		User userObj = userRepoObj.getByNameAndEmail(name.toUpperCase(), email);
		if (userObj == null)
			throw new InvalidCustomException("User does not exist", HttpStatus.NOT_FOUND);
		BeanUtils.copyProperties(userObj, responseMapperObj);
		return responseMapperObj;
	}

	public ResponseModelMapper getByMobileNo(Long mobileNo) throws InvalidCustomException {
		ResponseModelMapper responseMapperObj = new ResponseModelMapper();
		;
		User userObj = userRepoObj.getByMobileNo(mobileNo);
		if (userObj == null)
			throw new InvalidCustomException("User does not exist", HttpStatus.NOT_FOUND);
		BeanUtils.copyProperties(userObj, responseMapperObj);
		return responseMapperObj;
	}
	// could be in one as well
}

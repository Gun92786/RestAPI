package com.lng.dbDemo.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lng.dbDemo.bean.User;
import com.lng.dbDemo.exception.InvalidIdException;
import com.lng.dbDemo.service.UserService;

@RestController
@RequestMapping(value = { "/dbDemo" })
@Validated
public class UserController {
	@Autowired
	UserService userService;

	private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@PostMapping(value = "/createUser")
	public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
		LOGGER.info("POST :" +user);
		if (user.getCreatedDate() == null) {
			Date date = new Date();
			DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
			String formattedDate = dateFormat.format(date);
			user.setCreatedDate(formattedDate);
		}
		String country = user.getCountry();
		String name = user.getName();
		int age=user.getAge();
		if (country == null) {
			user.setCountry("IND");
		}
		if (name.matches("([a-zA-Z]*$)")) {
			if (country == null) {
				user.setCountry("IND");
			} else {
				if (country.matches("([a-zA-Z]*$)"))
				{
					if( String.valueOf(age)!=null && Integer.parseInt(String.valueOf(age))!=age)
					throw new InvalidIdException("Please enter a valid age", HttpStatus.NOT_FOUND);
					userService.createUser(user);
				}
				else
					throw new InvalidIdException("Please enter a valid country", HttpStatus.NOT_FOUND);
			}
		} else
			throw new InvalidIdException("Please enter a valid name", HttpStatus.NOT_FOUND);

		
		
		return new ResponseEntity<String>("User Created successfully", HttpStatus.CREATED);
	}

	@GetMapping(value = "/getUserById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUserById(@Valid @PathVariable("id") long id) throws InvalidIdException {
		LOGGER.info("GET getUserById :" +id);
		if (id < 0)
			throw new InvalidIdException("Id cant be less than zero", HttpStatus.NOT_FOUND);
		User user = userService.findById(id);
		if (user == null)
			throw new InvalidIdException("User not present", HttpStatus.NOT_FOUND);

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@GetMapping(value = "/getAllUsers")
	public List<User> getAllUser() {
		LOGGER.info("GET getAllUsers ");
		List<User> tasks = userService.getUser();
		return tasks;
	}

	@DeleteMapping(value = "/deleteById/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") long id) {
		LOGGER.info("DELETE deleteById :" +id);
		User user = userService.findById(id);
		if (user == null) {
			return new ResponseEntity<String>("", HttpStatus.NOT_FOUND);
		}
		userService.deleteUserById(id);
		return new ResponseEntity<String>("User Deleted Successfully", HttpStatus.OK);
	}

		@PutMapping(value="/update/{id}")
		public ResponseEntity<Void> updateUser(@RequestBody User currentUser,@PathVariable("id") long id) 
		{
			LOGGER.info("GET update :"+currentUser+"and id=" +id);
		User user = userService.findById(id);
		if (user==null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		userService.update(currentUser,id);
		return new ResponseEntity<Void>(HttpStatus.OK);
		}

}

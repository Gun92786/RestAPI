package com.lng.assignment2.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lng.assignment2.exception.InvalidCustomException;
import com.lng.assignment2.repository.UserRepository;
import com.lng.assignment2.requestMapper.RequestModelMapper;
import com.lng.assignment2.responseMapper.ResponseModelMapper;
import com.lng.assignment2.service.UserService;

@RestController
@RequestMapping("/assignment2")
public class UserController {

	@Autowired
	UserService userServiceObj;
	@Autowired
	UserRepository userRepoObj;

	@PostMapping(value = "/processFile")
	public ResponseEntity<String> processFile(@RequestBody RequestModelMapper request)
			throws IOException, InvalidCustomException, ParseException {
		return userServiceObj.processFile(request);
	}

	@GetMapping(value = "/getDetails")
	public ResponseModelMapper getDetails(@RequestParam Map<String, String> allParams) throws InvalidCustomException {
		ResponseModelMapper response;
		if (allParams.containsKey("mobileNo"))
			response = userServiceObj.getByMobileNo(Long.valueOf(allParams.get("mobileNo")));
		else if (allParams.containsKey("name") && allParams.containsKey("email"))
			response = userServiceObj.getByNameAndEmail(allParams.get("name"), allParams.get("email"));
		else
			throw new InvalidCustomException("Please enter either mobileNo OR name and email of user",
					HttpStatus.NOT_ACCEPTABLE);
		return response;
	}
}

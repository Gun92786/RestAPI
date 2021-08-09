/**
 * 
 */
package com.lng.assignment2.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lng.assignment2.exception.InvalidCustomException;
import com.lng.assignment2.repository.UserRepository;
import com.lng.assignment2.requestMapper.RequestModelMapper;
import com.lng.assignment2.responseMapper.ResponseModelMapper;
import com.lng.assignment2.service.UserService;

/**
 * @author Dell
 *
 */

@WebMvcTest(UserController.class)
public class UserControllerTest {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper objMapper;

	@MockBean
	UserRepository userRepo;
	@MockBean
	UserService userServiceObj;

	@Test
	public void testGetDetails() throws Exception, InvalidCustomException {
		ResponseModelMapper mockObj = new ResponseModelMapper();
		mockObj.setCity("Rajpur");
		mockObj.setCountry("India");
		mockObj.setDateOfBirth(new SimpleDateFormat("dd-MM-yyyy").parse("24-10-1990"));
		mockObj.setEmail("Sajjan@gmail.com");
		mockObj.setMobileNo(Long.valueOf("9034128910"));
		mockObj.setName("Sajjan");
		mockObj.setState("Punjab");
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "Sajjan");
		map.put("email", "Sajjan@gmail.com");
		Mockito.when(userServiceObj.getByNameAndEmail(map.get("name"), map.get("email"))).thenReturn(mockObj);
		MvcResult result = mockMvc
				.perform(get("/assignment2/getDetails").param("name", "Sajjan").param("email", "Sajjan@gmail.com"))
				.andExpect(status().isOk()).andReturn();
		String resultInString = result.getResponse().getContentAsString();
		ResponseModelMapper mappedResult = objMapper.readValue(resultInString, ResponseModelMapper.class);
		Assert.assertEquals(mockObj.getCity(), mappedResult.getCity());
	}

	@Test
	public void testGetDetails_mobile() throws Exception, InvalidCustomException {
		ResponseModelMapper mockObj = new ResponseModelMapper();
		mockObj.setCity("Rajpur");
		mockObj.setCountry("India");
		mockObj.setDateOfBirth(new SimpleDateFormat("dd-MM-yyyy").parse("24-10-1990"));
		mockObj.setEmail("Sajjan@gmail.com");
		mockObj.setMobileNo(Long.valueOf("9034128910"));
		mockObj.setName("Sajjan");
		mockObj.setState("Punjab");
		Map<String, String> map = new HashMap<String, String>();
		map.put("mobileNo", "9034128910");
		Mockito.when(userServiceObj.getByMobileNo(Long.valueOf(map.get("mobileNo")))).thenReturn(mockObj);
		MvcResult result = mockMvc.perform(get("/assignment2/getDetails").param("mobileNo", "9034128910"))
				.andExpect(status().isOk()).andReturn();
		Assert.assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	public void testProcessFile() throws IOException, ParseException, InvalidCustomException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		ResponseEntity<String> message = new ResponseEntity<>("File processed", HttpStatus.OK);
		when(userServiceObj.processFile(any(RequestModelMapper.class))).thenReturn(message);
		RequestModelMapper mapperRequest = new RequestModelMapper();
		mapperRequest.setFilePath("http://localhost:8080/assignment2/processFile");
		ResponseEntity<String> responseEntity = userServiceObj.processFile(mapperRequest);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);

	}

}

package com.lng.dbDemo.service;

import java.time.LocalDate;
import java.util.List;

import com.lng.dbDemo.bean.User;


public interface UserService {
	public void createUser(User user);
	public User findById(long id);
	public void deleteUserById(long id);
	
	public List<User> getUser();
	public void update(User currentUser, long id);

}

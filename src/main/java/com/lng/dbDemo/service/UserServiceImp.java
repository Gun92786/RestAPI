package com.lng.dbDemo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.lng.dbDemo.bean.User;
import com.lng.dbDemo.exception.InvalidIdException;
import com.lng.dbDemo.repository.UserRepository;


@Service
public class UserServiceImp implements UserService{
	@Autowired
	UserRepository userRepository;
	public void createUser(User user) {
		// TODO Auto-generated method stub
		System.out.println("user info "+user.getCountry()+" and "+user.getCreatedDate());
		userRepository.save(user);
	}
	public User findById(long id) {
		// TODO Auto-generated method stub
		if(userRepository.findById(id)!=null)
		return userRepository.findById(id).get();
		else
			throw new InvalidIdException("Id does not exist",HttpStatus.NOT_FOUND);
		//return null;
	}
	public void deleteUserById(long id) {
		// TODO Auto-generated method stub
		userRepository.deleteById(id);
	}
	public void update(User user, long id) {  
		System.out.println("UPDATE "+user.getName());
		// TODO Auto-generated method stub 
		User oldData= userRepository.findById(id).get();
		String name=user.getName();
		int age=user.getAge();
		String country=user.getCountry();
		String email=user.getEmail();
		if(name==null)
			name=oldData.getName();
		if(country==null)
			country=oldData.getCountry();
		if(email==null)
			email=oldData.getEmail();
	   if(age==0)
		   age=oldData.getAge();
		 userRepository.update(name,age,country,email,id);  
	}

	public List<User> getUser() {
		return (List<User>) userRepository.fetchAll("IND");
	}


}

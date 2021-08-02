package com.lng.dbDemo.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="user")
public class User {

	@Id
	@Digits(fraction = 0, integer = 7)
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	
	@NotBlank(message="Please enter valid name")
	@Column(name="name")
	String name;
	
	
	@NotNull @Min(18) @Max(100)
	@Digits(fraction = 0, integer = 3)
	@Column(name="age") 
     int age;

	
	 @NotEmpty @Email(message="Please enter valid email id")
	    private String email;
	
	@Column(name="country") 
	String country;
	
//	@NotNull(message="Date of Birth can not be null")
//	@Past(message="Date of birth must be the past date")
//	@JsonFormat(pattern="YYYY-mm-dd")
//	Date dateOfBirth;
//	
//	public Date getDateOfBirth() {
//		return dateOfBirth;
//	}
//	public void setDateOfBirth(Date dateOfBirth) {
//		this.dateOfBirth = dateOfBirth;
//	}
	
	

	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Column
	@JsonFormat(pattern="YYYY-mm-dd")
	String createdDate;
	
	
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String formattedDate) {
		this.createdDate = formattedDate;
	}

	public void setId(long id) {
		this.id = id;
	}


@Override
public String toString() {
	// TODO Auto-generated method stub
	String input="name:"+getName()+",country:"+getCountry()+",age:"+getAge()+",email:"+getEmail();
	return input;
}
	
}

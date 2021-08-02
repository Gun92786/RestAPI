package com.lng.dbDemo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lng.dbDemo.bean.User;

public interface UserRepository  extends JpaRepository<User, Long> {
	@Query(value="select * from test4 U", nativeQuery=true)
	List<User> fetchAll(String stitle);
	
	@Transactional
	@Modifying(clearAutomatically = true) 
	@Query(value="update test4  set name =:name,age=:age,country=:country,email=:email where id =:id",nativeQuery = true)
	void update(@Param("name") String name,@Param("age") int age,@Param("country") String country,@Param("email") String email, @Param("id") Long id);
	
}
 
package com.lng.assignment2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.lng.assignment2.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "select exists(select 1 from user_Detail where email=:email)", nativeQuery = true)
	boolean ifExistEmail(@Param("email") String email);

	@Query(value = "select * from user_Detail where email=:email and upper(name)=:name", nativeQuery = true)
	User getByNameAndEmail(@Param("name") String name, @Param("email") String email);

	@Query(value = "select * from user_Detail where mobile_no=:mobileNo", nativeQuery = true)
	User getByMobileNo(@Param("mobileNo") Long mobileNo);
}

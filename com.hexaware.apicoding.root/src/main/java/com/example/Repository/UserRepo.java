package com.example.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Entity.Users;

@Repository
public interface UserRepo extends JpaRepository<Users,Integer>{

	@Query("SELECT u FROM Users u WHERE u.username = :username")
	Users findByUsername(@Param("username") String username);


}

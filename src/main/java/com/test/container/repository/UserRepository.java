package com.test.container.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.container.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	public List<User> findByFirstName(String firstName);
}

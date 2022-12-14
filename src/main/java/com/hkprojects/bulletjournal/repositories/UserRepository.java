package com.hkprojects.bulletjournal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hkprojects.bulletjournal.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);
	@Query("FROM User u WHERE u.email = :email")
	User findByEmail(String email);
}

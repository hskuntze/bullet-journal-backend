package com.hkprojects.bulletjournal.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hkprojects.bulletjournal.entities.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>{
	@Query("FROM Todo t WHERE t.user.username = :username")
	Page<Todo> findByUsername(String username, Pageable pageable);
	
	@Query("SELECT DISTINCT obj FROM Todo obj "+
			" WHERE (LOWER(obj.title) LIKE LOWER(CONCAT('%',:title,'%'))) "+
			"AND (LOWER(obj.priority) LIKE LOWER(CONCAT('%',:priority,'%')))"+
			"AND obj.user.username = :username")
	Page<Todo> findByUsernameWithFilters(String title, String priority, String username, Pageable pageable);
}

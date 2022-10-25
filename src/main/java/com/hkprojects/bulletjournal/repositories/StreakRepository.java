package com.hkprojects.bulletjournal.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hkprojects.bulletjournal.entities.Streak;

@Repository
public interface StreakRepository extends JpaRepository<Streak, Long>{
	
	@Query("FROM Streak s WHERE s.user.username = :username")
	Page<Streak> findByUsername(String username, Pageable pageable);
	
	@Query("SELECT DISTINCT obj FROM Streak obj "+
			" WHERE (LOWER(obj.title) LIKE LOWER(CONCAT('%',:title,'%'))) "
			+ " AND obj.user.username = :username")
	Page<Streak> findByUsernameWithFilters(String title, String username, Pageable pageable);
}

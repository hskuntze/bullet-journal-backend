package com.hkprojects.bulletjournal.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hkprojects.bulletjournal.entities.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>{
	@Query("FROM Card c WHERE c.user.username = :username")
	Page<Card> findByUsername(String username, Pageable pageable);
	
	@Query("SELECT DISTINCT obj FROM Card obj "+
			" WHERE (LOWER(obj.title) LIKE LOWER(CONCAT('%',:title,'%'))) "
			+ " AND obj.user.username = :username")
	Page<Card> findByUsernameWithFilters(String username, String title, Pageable pageable);
}

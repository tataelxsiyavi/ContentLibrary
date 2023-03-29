package com.content.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.content.model.ContentPeople;
import com.content.model.PeopleLibrary;

public interface ContentPeopleRepo extends JpaRepository<ContentPeople, Long>{
//	@Modifying
//	@Query("delete from ContentPeople c where c.people_id.id = :id")
//	void deleteContentpeopleByPeopleId(@Param("id")long id );
	@Query("SELECT e FROM ContentPeople e JOIN e.people_id  c WHERE c.people_id = :id")
	List<ContentPeople> findPeopleByIdInContentPeople(@Param("id") long people_id);
	@Query("SELECT e FROM ContentPeople e JOIN e.people_id  c WHERE c.people_id = :id")
	Optional<ContentPeople> findContentPeopleBypeopleId(@Param("id") long people_id);
	
	@Query("Select e FROM ContentPeople e WHERE e.person_type = :persontype")
	Optional<ContentPeople> findContentPeopleByPersonType(@Param("persontype") String persontype);
}

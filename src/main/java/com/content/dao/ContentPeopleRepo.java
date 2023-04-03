package com.content.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.content.model.ContentLibrary;
import com.content.model.ContentPeople;
import com.content.model.PeopleLibrary;

public interface ContentPeopleRepo extends JpaRepository<ContentPeople, Long>{

	@Query("SELECT e FROM ContentPeople e JOIN e.people_id  c WHERE c.people_id = :id")
	List<ContentPeople> findPeopleByIdInContentPeople(@Param("id") long people_id);
	
	 @Query("SELECT COUNT(e) > 0 FROM ContentPeople e JOIN e.people_id c WHERE c.people_id = :myValue")
	    boolean existsByPeopleId(@Param("myValue") Long people_id);
	

	   @Query("SELECT COUNT(e) > 0 FROM ContentPeople e WHERE e.person_type = :myValue")
	    boolean existsByPersonType(@Param("myValue") String persontype);
	   

		@Query("SELECT e FROM ContentPeople  e WHERE e.content_id.content_id = :id")
		List<ContentPeople> findContentPeopleByContentId(@Param("id") long cate_id);
	
		@Query("SELECT e.content_id FROM ContentPeople e where e.people_id.people_id= :id")
		List<ContentLibrary> getContentLibraryByPeopleid(@Param("id") long people_id);
}

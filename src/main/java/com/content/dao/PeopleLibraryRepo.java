package com.content.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.content.model.PeopleLibrary;

public interface PeopleLibraryRepo extends JpaRepository<PeopleLibrary, Long> {
//	@Query("SELECT e FROM FeaturedSection e JOIN e.Category  c WHERE c.category_id = :id")
//	//SELECT s FROM Student s JOIN s.courses c WHERE c.name = :courseName
//	List<FeaturedSection> findCategoryByIdInFeatured(@Param("id") long cate_id);
	
	
}

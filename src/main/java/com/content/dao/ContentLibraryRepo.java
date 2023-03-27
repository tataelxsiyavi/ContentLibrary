package com.content.dao;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.content.model.ContentLibrary;

public interface ContentLibraryRepo extends JpaRepository<ContentLibrary, Long>{
	@Query("SELECT e FROM ContentLibrary  e WHERE e.categories.category_id = :id")
	List<ContentLibrary> findByCategoryIdInContent(@Param("id") long cate_id); 
}

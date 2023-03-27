package com.content.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.content.model.Category;
@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

	
}

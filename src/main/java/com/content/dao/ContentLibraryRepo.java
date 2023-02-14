package com.content.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.content.model.ContentLibrary;

public interface ContentLibraryRepo extends JpaRepository<ContentLibrary, Long>{

}

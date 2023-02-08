package com.content.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.content.model.PeopleLibrary;

public interface PeopleLibraryRepo extends JpaRepository<PeopleLibrary, Long> {

}

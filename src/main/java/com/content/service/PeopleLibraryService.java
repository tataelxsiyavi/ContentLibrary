package com.content.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.content.dao.PeopleLibraryRepo;
import com.content.model.PeopleLibrary;

@Service
public class PeopleLibraryService {
	@Autowired
	PeopleLibraryRepo peoplerepo;
	
	public List<PeopleLibrary> getAllPeoples(){
		return peoplerepo.findAll();
	}
	
	public void createPeople(PeopleLibrary peoplelibrary) throws Exception {
		Optional<PeopleLibrary> st = peoplerepo.findById(peoplelibrary.getPeople_id());
		if (st.isPresent()) {
			throw new Exception(" is already present");
		}
        
		peoplerepo.save(peoplelibrary);
	}
	
	

}

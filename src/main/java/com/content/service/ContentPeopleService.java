package com.content.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.content.dao.ContentPeopleRepo;
import com.content.model.ContentPeople;

@Service
public class ContentPeopleService {
	@Autowired
	ContentPeopleRepo contentpeoplerepo;
	
	  public List<ContentPeople> getAllContentPeople(){
		return contentpeoplerepo.findAll();
	}
    
	public ContentPeople create(ContentPeople people) throws Exception {
		
		return contentpeoplerepo.save(people);
		
	}
	public Optional<ContentPeople> getContentPeopleById(long id){
		return  contentpeoplerepo.findById(id);
	}
	
	public ContentPeople update(ContentPeople people) {
		ContentPeople content=contentpeoplerepo.findById(people.getContent_people_id()).get();

		 if(people.getPerson_type()!=null) {
		 content.setPerson_type(people.getPerson_type());
		 }
		 if(people.getContent_id()!=null) {
		 content.setContent_id(people.getContent_id());
		 }
		 if(people.getPeople_id()!=null) {
		 content.setPeople_id(people.getPeople_id());
		
		 }
		
		return contentpeoplerepo.save(content);
	}
	
	public void deleteContentPeopleById(long id) {
		contentpeoplerepo.deleteById(id);
		
	}
}

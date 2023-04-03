package com.content.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.content.model.ContentLibrary;
import com.content.model.ContentPeople;
import com.content.model.PeopleLibrary;
import com.content.service.ContentPeopleService;
import com.content.service.PeopleLibraryService;

//@Controller
@RestController
//@Controller
public class ContentPeopleController {

	
	@Autowired
	ContentPeopleService contentpeopleservice;
	@Autowired
	PeopleLibraryService peopleservice;
	
	@GetMapping("/getcontepeople")
	public List<ContentPeople> getAllContentPeople(){
		return contentpeopleservice.getAllContentPeople();
	}
	@PostMapping("/deleteContentPeople/{id}")
	
	public void deleteContentPeopleById(@PathVariable long id) {
		
		
		contentpeopleservice.deleteContentPeopleById(id);
		
		
	}
	
}

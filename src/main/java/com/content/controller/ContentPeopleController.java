package com.content.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.content.model.ContentLibrary;
import com.content.model.ContentPeople;
import com.content.model.PeopleLibrary;
import com.content.service.ContentPeopleService;
import com.content.service.PeopleLibraryService;

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
	
//	@PostMapping("/createcontentpeople")
//	public String createContentPeople(@RequestParam("person_type")String person_type,
//	        @RequestParam("people")Long  people,
//	        @RequestParam("content_id")Long contentId) throws Exception {
//		
//		
//		ContentLibrary conid=new ContentLibrary(contentId);
//		
//		
//		ContentPeople con=new ContentPeople();
//		con.setContent_id(conid);
//		
//			PeopleLibrary peo=peopleservice.findPeopleById(people).get();
//			con.setPeople_id(peo);
//		
//		
//			con.setPerson_type(person_type);
//		
//		 contentpeopleservice.create(con);
//		
//		
//		 return "addcontent";
//		
//		
//	}
	
}

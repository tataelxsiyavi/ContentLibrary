//package com.content.service;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.content.dao.ContentLibraryRepo;
//import com.content.model.ContentLibrary;
//
//
//
//@Service
//public class ContentLibraryService {
//
//	@Autowired
//	ContentLibraryRepo contentrepo;
//	
//	public List<ContentLibrary> getAllContents(){
//		return contentrepo.findAll();
//	}
//	
//	public ContentLibrary createContent(ContentLibrary contentlib) throws Exception {
//		Optional<ContentLibrary> st = contentrepo.findById(contentlib.getContent_id());
//		if (st.isPresent()) {
//			throw new Exception("ContentId is already present");
//		}
//        
//        
//		return contentrepo.save(contentlib);
//	}
//	
//	
//}

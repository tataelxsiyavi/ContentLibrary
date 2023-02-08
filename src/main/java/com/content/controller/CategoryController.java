//package com.content.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.content.model.Category;
//import com.content.service.CategoryService;
//
//@RestController
//public class CategoryController {
//	@Autowired
//	CategoryService categoryservice;
//	
//	@GetMapping("/allcategories")
//	
//	public List<Category> getAllStudents(){
//		return categoryservice.getAllCategories();
//	}
//	@PostMapping("/createcategory")
//	public void createStudent(@RequestBody  Category category) throws Exception {    //save
//		
//		 categoryservice.saveCategories(category);
//	
//
//}
//	}

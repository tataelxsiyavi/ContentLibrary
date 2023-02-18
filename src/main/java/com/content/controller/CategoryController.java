package com.content.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import com.content.dao.CategoryRepo;
import com.content.model.Category;
import com.content.service.CategoryService;

//@RestController
@Controller
public class CategoryController {
	@Autowired
	CategoryService categoryservice;
	@Autowired
	CategoryRepo categoryrepo;

	@GetMapping("/category")
	public String categoryPage(Model model) {
		List<Category> categorylist = categoryrepo.findAll();
		model.addAttribute("category", new Category());
		model.addAttribute("categorylist", categorylist);
		return "managecategory";
	}

	@GetMapping("/bulkupload")
	public String bulkUploadPage() {
		return "bulkupload";
	}

	@GetMapping("/getcategoryRest")

	public List<Category> getAllStudents() {
		return categoryservice.getAllCategories();
	}

	@PostMapping("/categoryRest")
	public String createStudent(@RequestParam("category_name") String category_name) throws Exception { // save //
		//

		Category category = new Category();
		category.setCategory_name(category_name);
		categoryservice.saveCategories(category);
		return "redirect:/category";
	}
	// @PostMapping("/addcategory")
	//

	@GetMapping("/deletecategory/{id}")
	public String deleteCatgeory(@PathVariable long id) {
		categoryservice.deleteCategory(id);
		return "redirect:/category";
	}
}

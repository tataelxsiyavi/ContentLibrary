package com.content.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.content.dao.CategoryRepo;
import com.content.dao.ContentLibraryRepo;
import com.content.dao.FeaturedSectionRepo;
import com.content.dao.PlaylistLibraryRepo;
import com.content.model.Category;
import com.content.model.ContentLibrary;
import com.content.model.FeaturedSection;
import com.content.model.PlaylistLibrary;
import com.content.service.CategoryService;
import com.content.service.ContentLibraryService;

//@RestController
@Controller
public class CategoryController {
	@Autowired
	CategoryService categoryservice;
	@Autowired
	CategoryRepo categoryrepo;
	@Autowired
	ContentLibraryService contentservice;
	@Autowired
	PlaylistLibraryRepo playlistrepo;
	@Autowired
	FeaturedSectionRepo featuredrepo;
	@Autowired
	ContentLibraryRepo contentrepo;

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
	public String deleteCatgeory(@PathVariable long id,RedirectAttributes redir) {
		
		List<ContentLibrary>content1 =contentrepo.findByCategoryIdInContent(id);
		List<PlaylistLibrary>playlist=playlistrepo.findCategoryByIdInPlaylist(id);
		List<FeaturedSection> featured=featuredrepo.findCategoryByIdInFeatured(id);
//		System.out.println("Playlist "+playlist);
//		System.out.println("Featured "+featured);
//		System.out.println(content1);
		if(content1.size()>0 || playlist.size()>0 || featured.size()>0) {//|
			redir.addFlashAttribute("Perror","Category cannot delete...");
		}
		else {
			categoryservice.deleteCategory(id);
		}
		
		return "redirect:/category";
	}
}

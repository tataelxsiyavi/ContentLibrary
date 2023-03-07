package com.content.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.content.dao.CategoryRepo;
import com.content.dao.ContentLibraryRepo;
import com.content.dao.FeaturedSectionRepo;
import com.content.model.Category;
import com.content.model.ContentLibrary;
import com.content.model.FeaturedSection;
import com.content.service.CategoryService;
import com.content.service.ContentLibraryService;
import com.content.service.FeaturedSectionService;

//@RestController
@Controller 
public class FeaturedSectionController {

	@Autowired
	FeaturedSectionService sectionservice;

	@Autowired
	CategoryService categoryservice;

	@Autowired
	ContentLibraryService contentservice;

	@Autowired
	FeaturedSectionService featuredservice;

	@Autowired
	FeaturedSectionRepo sectionrepo;
	
	@Autowired
	CategoryRepo categoryrepo;
	
	@Autowired
	ContentLibraryRepo contentrepo;
	
	

	
	@GetMapping("/featuredsections")
	public String FeaturedsectionsPage(Model model) {
		List<FeaturedSection> sectionlist=sectionrepo.findAll();
		model.addAttribute("sectionlist", sectionlist);
		return "Featuredsections";
//		return sectionlist;
	}
	
	

//	@GetMapping("/Addsection")
//	public String AddsectionPage() {
//		return "Addsection";
//	}
	
	
	@GetMapping("/Addsection")
	public String AddSectionPage(Model model) {
		List<Category> categories = categoryrepo.findAll();
		List<ContentLibrary> contentlist = contentrepo.findAll();
		model.addAttribute("contentlist", contentlist);
		model.addAttribute("featuredsection", new FeaturedSection());
		model.addAttribute("categories", categories);
		return "Addsection";
	}
	
	
	
	@GetMapping("/Savefeaturedsection")
	public String featuredsectionPage() {
		return "Featuredsections";
	}

	

	@GetMapping("/featuredsection")
	public List<FeaturedSection> getAllsection() {
		return sectionservice.getAllsection();
	}

	
	
	 
	@PostMapping("/createsection")
	public String createSection  (
     	    @Nullable@RequestParam(required=false,value="category_Id") List<Long> category_Id,
			@Nullable@RequestParam(required=false,value="content_Id") List<Long> content_Id, 
			@RequestParam(required=false,value="content_Type") String content_Type,
			@RequestParam(required=false,value="section_Name") String section_Name, 
			@RequestParam(required=false,value="section_Type") String section_Type,
			@Nullable@RequestParam( required=false,value="criteria") String criteria, 
			@Nullable@RequestParam(required=false,value="content_limit") Long content_Limit,Model model)

			throws Exception {
	



		List<Category>  cat = new ArrayList<>();
		if(category_Id != null) {
			
		for (int i = 0; i < category_Id.size(); i++) {

			Optional<Category> category_id = categoryservice.getCategoryById(category_Id.get(i));

			if (category_id.isEmpty()) {

				throw new Exception("Is empty categoryID");
			}

			cat.add(new Category(category_Id.get(i)));

		}
		}
		else {
			cat=null;
		}
		
		List<ContentLibrary> con=new ArrayList<>();
		if(content_Id!=null) {
		
	
		for (int i = 0; i < content_Id.size(); i++) {

			Optional<ContentLibrary> content_id = contentservice.findContentById(content_Id.get(i));

			if (content_id.isEmpty()) {

				throw new Exception("Is empty contentID");
			}

			con.add(new ContentLibrary(content_Id.get(i)));
		}
		
		}
		else {
			con=null;
		}
		
		
		
	
	
		String section_type="Manual";
		

		if(section_Type.equals(section_type)) {

			criteria=null;
			content_Limit=null;
			
			
		}
		
		FeaturedSection featuredSection = new FeaturedSection();
		
		featuredSection.setContentLibrary(con);
		featuredSection.setContent_Type(content_Type);
		featuredSection.setSection_Name(section_Name);
		featuredSection.setSection_Type(section_Type);
		featuredSection.setCategory(cat);
		featuredSection.setCriteria(criteria);
		featuredSection.setContent_Limit(content_Limit);
		
		
		
		featuredservice.createSection(featuredSection);
		
		return "redirect:/featuredsections";

	}

	
	@GetMapping("/featuredsection/{id}")
	public String deleteSection(@PathVariable long id) {
		sectionservice.deleteSection(id);
		return "redirect:/featuredsections";

	}

	
	@GetMapping("/updatesection/{id}")
    public ModelAndView ShowEditSectionPage (Model model, @PathVariable long id) 	{
		ModelAndView editView = new ModelAndView("edit-section-form");
		FeaturedSection section = sectionrepo.findById(id).get();
		List<Category> cat=section.getCategory();
		List<Category> categories = categoryrepo.findAll();
		List<ContentLibrary> con = section.getContentLibrary();
		List<ContentLibrary> contentlist = contentrepo.findAll();
		model.addAttribute("contentlist", contentlist);
		model.addAttribute("categories", categories);
		model.addAttribute("cat", cat);
		model.addAttribute("con", con);
        editView.addObject("section",section);
		return editView;
		
	}

	@PostMapping("/editsection")
	public String updateFeaturedSection(
			@RequestParam("featured_section_id") long featured_section_id, 
			@Nullable@RequestParam(required=false,value="category_Id") List<Long> category_Id,
			@Nullable@RequestParam(required=false,value="content_Id") List<Long> content_Id, 
			//@RequestParam("content_Type") String content_Type, 
			@RequestParam("section_Name") String section_Name, 
			@Nullable@RequestParam("criteria") String criteria, 
			@Nullable@RequestParam("content_limit") Long content_Limit) throws Exception {
		
		
		List<Category> cat = null;
		if(category_Id != null) {
			cat = new ArrayList<>();
		for (int i = 0; i < category_Id.size(); i++) {

			Optional<Category> category_ids = categoryservice.getCategoryById(category_Id.get(i));

			if (category_ids.isEmpty()) {

				throw new Exception("Is empty categoryID");
			}

			cat.add(categoryservice.updateCategory(new Category(category_Id.get(i))));//new Category(category_Id.get(i))

		}
		}
		else {
			cat=null;
		}
//		
		List<ContentLibrary> con=null;
		if(content_Id!=null) {
		 con= new ArrayList<>();
	
		for (int i = 0; i < content_Id.size(); i++) {

			Optional<ContentLibrary> content_ids =  contentservice.findContentById(content_Id.get(i));

			if (content_ids.isEmpty()) {

				throw new Exception("Is empty contentID");
			}

			con.add(contentservice.updateContent(new ContentLibrary(content_Id.get(i))));//
		}
		
		}
		else {
			con=null;
		}
		
		String criteria_type = "select";
		if(criteria.equals(criteria_type)) {

			criteria=null;
//			content_Limit=null;
			
			
		}
		
		
		FeaturedSection featuredSection = sectionrepo.findById(featured_section_id).get();
		

		    if(cat!=null) {
		    featuredSection.setCategory(cat);
			}
			
			if(con!=null) {
		    featuredSection.setContentLibrary(con);
			}
			
	        if(section_Name!=null)	{	
			featuredSection.setSection_Name(section_Name);
	        }
	        if(criteria!=null) {
			featuredSection.setCriteria(criteria);
	        }
	        
	        if(content_Limit!=null) {
			featuredSection.setContent_Limit(content_Limit);
	        }
	        
			featuredservice.updateFeaturedSection(featuredSection);
		
		
		return "redirect:/featuredsections";


	}
}


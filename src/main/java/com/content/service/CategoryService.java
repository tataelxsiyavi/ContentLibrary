package com.content.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.content.dao.CategoryRepo;
import com.content.model.Category;

@Service
public class CategoryService {
	@Autowired
	CategoryRepo categoryrepo;

	public List<Category> getAllCategories() {
		return categoryrepo.findAll();
	}

	public void saveCategories(Category category) throws Exception {
		Optional<Category> el = categoryrepo.findById(category.getCategory_id());
		if (el.isPresent()) {
			throw new Exception("ContentId is already present");

		}
		categoryrepo.save(category);

	}

	public Optional<Category> getCategoryById(long id) {

		return categoryrepo.findById(id);
	}

	public Category updateCategory(Category category) {
		Category st = categoryrepo.findById(category.getCategory_id()).get();
		//
		// if(category.getCategory_name()!=null) {
		// st.setCategory_name(category.getCategory_name());
		// }
		return categoryrepo.save(st);

	}

	public void deleteCategory(long id) {
		categoryrepo.deleteById(id);
	}

}

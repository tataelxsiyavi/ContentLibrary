package com.content.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="featured_section")
public class FeaturedSection {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long featuredSection_Id;
	private String content_Type;
	private String section_Name;
	private String section_Type;
	private String criteria;
	private int    content_Limit;
	@ManyToMany
    @JoinColumn(name="category")
	private List<Category> Category;
    @ManyToMany
    @JoinColumn(name="content")
    private List<ContentLibrary> contentLibrary;
	public FeaturedSection(String content_Type, String section_Name, String section_Type, String criteria,
			int content_Limit, List<com.content.model.Category> category, List<ContentLibrary> contentLibrary) {
		super();
		this.content_Type = content_Type;
		this.section_Name = section_Name;
		this.section_Type = section_Type;
		this.criteria = criteria;
		this.content_Limit = content_Limit;
		Category = category;
		this.contentLibrary = contentLibrary;
	}
    
	    
	    
}
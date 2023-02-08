package com.content.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContentLibrary {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long content_id;
	private String content_type;
	private String content_format;
	private String content_group;
	private String content_name;
	private String permalink;
	@OneToOne
	@JoinColumn(name="category_id_fk")
	private Category categories;
	private String story;
	private String search_tags;
	private String person_type;
	@ManyToMany
	@JoinColumn(name="content_people")
	private List<PeopleLibrary> people_library;

	@OneToOne
	@JoinColumn(name="media_asset_id_fk")
	private AssetLibrary media_assets;
	@OneToOne
	@JoinColumn(name="additional_asset_id_fk")
	private AssetLibrary additional_assets;
	@OneToOne
	@JoinColumn(name="thumbnail_asset_id_fk")
	private AssetLibrary thumbnail_assets;
	@OneToOne
	@JoinColumn(name="banner_asset_id_fk")
	private AssetLibrary banner_assets;
	public ContentLibrary(String content_type, String content_format, String content_group, String content_name,
			String permalink, Category categories, String story, String search_tags, String person_type,
			List<PeopleLibrary> people_library, AssetLibrary media_assets, AssetLibrary additional_assets,
			AssetLibrary thumbnail_assets, AssetLibrary banner_assets) {
		super();
		this.content_type = content_type;
		this.content_format = content_format;
		this.content_group = content_group;
		this.content_name = content_name;
		this.permalink = permalink;
		this.categories = categories;
		this.story = story;
		this.search_tags = search_tags;
		this.person_type = person_type;
		this.people_library = people_library;
		this.media_assets = media_assets;
		this.additional_assets = additional_assets;
		this.thumbnail_assets = thumbnail_assets;
		this.banner_assets = banner_assets;
	}
	
	

}

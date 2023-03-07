package com.content.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import lombok.ToString;

@Entity
@ToString
public class PlaylistLibrary {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long playlist_id;
	private String playlist_type;
	
	private String title;
	private String permalink;
	
	@OneToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="category_id")
	private Category categories;
	private String description;
	private String search_tags;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="thumbnail_asset_id")
	private AssetLibrary thumbnail_assets;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="banner_asset_id")
	private AssetLibrary banner_assets;
	
	@ManyToMany
    @JoinColumn(name="content_id")
    private List<ContentLibrary> contentLibrary;
	public PlaylistLibrary(String playlist_type, String title, String permalink, Category categories,
			String description, String search_tags, AssetLibrary thumbnail_assets, AssetLibrary banner_assets,
			List<ContentLibrary> contentLibrary) {
		super();
		this.playlist_type = playlist_type;
		this.title = title;
		this.permalink = permalink;
		this.categories = categories;
		this.description = description;
		this.search_tags = search_tags;
		this.thumbnail_assets = thumbnail_assets;
		this.banner_assets = banner_assets;
		this.contentLibrary = contentLibrary;
	}
	
	
	public PlaylistLibrary(long playlist_id) {
		super();
		this.playlist_id = playlist_id;
	}
	
	
	

	public PlaylistLibrary() {
		super();
		
	}


	public long getPlaylist_id() {
		return playlist_id;
	}


	public void setPlaylist_id(long playlist_id) {
		this.playlist_id = playlist_id;
	}


	public String getPlaylist_type() {
		return playlist_type;
	}


	public void setPlaylist_type(String playlist_type) {
		this.playlist_type = playlist_type;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getPermalink() {
		return permalink;
	}


	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}


	public Category getCategories() {
		return categories;
	}


	public void setCategories(Category categories) {
		this.categories = categories;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getSearch_tags() {
		return search_tags;
	}


	public void setSearch_tags(String search_tags) {
		this.search_tags = search_tags;
	}


	public AssetLibrary getThumbnail_assets() {
		return thumbnail_assets;
	}


	public void setThumbnail_assets(AssetLibrary thumbnail_assets) {
		this.thumbnail_assets = thumbnail_assets;
	}


	public AssetLibrary getBanner_assets() {
		return banner_assets;
	}


	public void setBanner_assets(AssetLibrary banner_assets) {
		this.banner_assets = banner_assets;
	}


	public List<ContentLibrary> getContentLibrary() {
		return contentLibrary;
	}


	public void setContentLibrary(List<ContentLibrary> con) {
		this.contentLibrary = con;
	}
}

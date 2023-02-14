package com.content.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
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
@Table(name="playlist_library")
public class PlaylistLibrary {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long playlist_id;
	private String playlist_type;
	private String title;
	private String permalink;
	@OneToOne
	@JoinColumn(name="category")
	private Category categories;
	private String description;
	private String search_tags;
	@OneToOne
	@JoinColumn(name="thumbnail_asset")
	private AssetLibrary thumbnail_assets;
	@OneToOne
	@JoinColumn(name="banner_asset")
	private AssetLibrary banner_assets;
	@ManyToMany
    @JoinColumn(name="content")
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
	
	

}

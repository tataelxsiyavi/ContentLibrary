package com.content.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AssetLibrary {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long asset_id;
	private String asset_type;
	private String asset_name;
	private String asset_size;
	private String asset_filepath;

	public AssetLibrary(String asset_type, String asset_name, String asset_size, String asset_filepath) {
		super();
		this.asset_type = asset_type;
		this.asset_name = asset_name;
		this.asset_size = asset_size;
		this.asset_filepath = asset_filepath;
	}

	
	
	

}
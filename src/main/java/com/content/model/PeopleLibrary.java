package com.content.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
public class PeopleLibrary {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long people_id;
	private String people_name;
	private String bio;
//	private String profile_picture;

	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn()
	private AssetLibrary assets;

	public PeopleLibrary(String people_name, String bio,  AssetLibrary assets) {
		super();
		this.people_name = people_name;
		this.bio = bio;
		
		this.assets = assets;
	}
	

	
	
	
}

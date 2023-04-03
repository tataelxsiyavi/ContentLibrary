package com.content.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
public class PeopleLibrary {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long people_id;
	private String people_name;
	@Lob
	private String bio;
//	private String profile_picture;
@OneToMany(mappedBy="people_id",cascade =CascadeType.ALL)
private List<ContentPeople>contentPeople;

	@OneToOne(cascade =CascadeType.PERSIST)
	@JoinColumn()
	private AssetLibrary people_asset;

	public PeopleLibrary(String people_name, String bio,  AssetLibrary assets) {
		super();
		this.people_name = people_name;
		this.bio = bio;
		
		this.people_asset = assets;
	}

	public PeopleLibrary(long people_id) {
		super();
		this.people_id = people_id;
	}
	

	
	
	
}

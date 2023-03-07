package com.content.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContentPeople {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long content_people_id;
    private String person_type;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "peope_id" )
  
    private PeopleLibrary people_id;
    @ManyToOne(cascade=CascadeType.ALL)

    @JoinColumn(name="content_id")
    private ContentLibrary content_id;
	public ContentPeople(String person_type, PeopleLibrary people_id) {
		super();
		this.person_type = person_type;
		this.people_id = people_id;
//		this.content_id=content_id;
		
	}
	public ContentPeople(String person_type, PeopleLibrary people_id, ContentLibrary content_id) {
		super();
		this.person_type = person_type;
		this.people_id = people_id;
		this.content_id = content_id;
	}
	
	public long getContent_people_id() {
		return content_people_id;
	}
	public void setContent_people_id(long content_people_id) {
		this.content_people_id = content_people_id;
	}
	public String getPerson_type() {
		return person_type;
	}
	public void setPerson_type(String person_type) {
		this.person_type = person_type;
	}
	public PeopleLibrary getPeople_id() {
		return people_id;
	}
	public void setPeople_id(PeopleLibrary people_id) {
		this.people_id = people_id;
	}
	public ContentLibrary getContent_id() {
		return content_id;
	}
	public void setContent_id(ContentLibrary content_id) {
		this.content_id = content_id;
	}

	

	
	
  
    

}


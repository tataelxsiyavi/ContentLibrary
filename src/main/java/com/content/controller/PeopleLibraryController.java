package com.content.controller;




import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.content.model.AssetLibrary;
import com.content.model.PeopleLibrary;
import com.content.service.FileStorageService;
import com.content.service.PeopleLibraryService;
import com.content.util.AppConstants;


@RestController
//@Controller
public class PeopleLibraryController {
	@Autowired
	PeopleLibraryService peopleservice;
	@Autowired
	FileStorageService   fileservice;
	
	@PostMapping( value = "/peoplelibrary" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)//
	public String createPeople(
			@RequestParam("people_name") String people_name ,
			@RequestParam("bio") String bio,
			@RequestParam("profile_picture") MultipartFile profilePicture) throws Exception {
		String profilePic = fileservice.storeTumbnailFile(profilePicture);
		String ProfilePicUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path(AppConstants.DOWNLOAD_PATH).path(profilePic).toUriString();
		System.out.println(ProfilePicUri);
		String asset_type=profilePicture.getContentType();
		String asset_name=profilePicture.getOriginalFilename();
		long size=profilePicture.getSize();
       String asset_size;
		
		float size_kb = size /1024; 
		float size_mb = size_kb / 1024;

	 if(size_mb > 0){
	        	asset_size = size_mb + " MB";
	        }else{
	        	asset_size = size_kb + " KB";
	        }
		System.out.println("  Type     "+asset_type+"    Name:     "+asset_name+"   Size:   "+asset_size);
		
		AssetLibrary asset=new AssetLibrary(asset_type,asset_name,asset_size,ProfilePicUri);
		PeopleLibrary people=new PeopleLibrary();
		people.setPeople_name(people_name);
		people.setBio(bio);
//		people.setProfile_picture(ProfilePicUrl);
		people.setAssets(asset);
		peopleservice.createPeople(people);
		
		return "created Successfully";
			
}
	@GetMapping("/peoplelibrary")
	public List<PeopleLibrary> getAllPeoples()
	{
		return peopleservice.getAllPeoples();
	}

}

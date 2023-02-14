package com.content.controller;

import java.io.IOException;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.content.enumclass.AssetType;
import com.content.model.AssetLibrary;
import com.content.model.PeopleLibrary;
import com.content.service.PeopleLibraryService;
import com.content.util.AppConstants;

@RestController
//@Controller
public class PeopleLibraryController {
	@Autowired
	PeopleLibraryService peopleservice;
	
	@PostMapping(value = "/peoplelibrary", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) //
	public String createPeople(@RequestParam("people_name") String people_name, @RequestParam("bio") String bio,
			@RequestParam("profile_picture") MultipartFile profilePicture) throws Exception {
		String profilePic = peopleservice.storeProfilePicture(profilePicture);
		String ProfilePicUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(AppConstants.DOWNLOAD_PATH)
				.path(profilePic).toUriString();
		System.out.println(ProfilePicUri);
		String asset_type = profilePicture.getContentType();//
		
		String asset_name = profilePicture.getOriginalFilename();
		long size = profilePicture.getSize();
		String asset_size;

		float size_kb = size / 1024;
		float size_mb = size_kb / 1024;

		if (size_mb > 0) {
			asset_size = size_mb + " MB";
		} else {
			asset_size = size_kb + " KB";
		}

		 AssetType type_asset = null ;
      String image="image/jpeg";
      String image2="image/png";
      if(image.equals(asset_type)||image2.equals(asset_type)) {
    	  type_asset=AssetType.Image;
      }
        
        System.out.println("     hey guys    type_asset :"+type_asset);
        
		AssetLibrary asset = new AssetLibrary(type_asset, asset_name, asset_size, ProfilePicUri);
		System.out.println("  Type     " +asset_type + "    Name:     " + asset_name + "   Size:   " + asset_size);
		PeopleLibrary people = new PeopleLibrary();
		people.setPeople_name(people_name);
		people.setBio(bio);
		people.setPeople_asset(asset);
		peopleservice.createPeople(people);

		return "people created Successfully";

	}

	@GetMapping("/peoplelibrary")
	public List<PeopleLibrary> getAllPeoples() {
		return peopleservice.getAllPeoples();
	}

	@GetMapping(value = AppConstants.DOWNLOAD_URI)
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		Resource resource = peopleservice.loadFileAsResource(fileName);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		if (contentType == null) {
			contentType = AppConstants.DEFAULT_CONTENT_TYPE;
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION,
						String.format(AppConstants.FILE_DOWNLOAD_HTTP_HEADER, resource.getFilename()))
				.body(resource);
	}

//	@PutMapping(value = "/peoplelibrary", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	public PeopleLibrary updateContent(
//			@RequestParam("people_id") long people_id,
//			@RequestParam("people_name") String people_name, 
//			@RequestParam("bio") String bio,
//			@RequestParam("profile_picture") MultipartFile profilePicture) throws Exception {
//		
//		String profilePic = peopleservice.storeProfilePicture(profilePicture);
//		String ProfilePicUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(AppConstants.DOWNLOAD_PATH)
//				.path(profilePic).toUriString();
//		System.out.println(ProfilePicUri);
//		String asset_type = profilePicture.getContentType();//
//		String asset_name = profilePicture.getOriginalFilename();
//		long size = profilePicture.getSize();
//		String asset_size;
////		AssetType type_asset = null;
////        if(asset_type== AssetType.Image.getValue1() ) {
////        	 type_asset=AssetType.Image;
////        }else if(asset_type==AssetType.Image.getValue2()) {
////        	type_asset=AssetType.Image;
////        }
//
//		float size_kb = size / 1024;
//		float size_mb = size_kb / 1024;
//
//		if (size_mb > 0) {
//			asset_size = size_mb + " MB";
//		} else {
//			asset_size = size_kb + " KB";
//		}
//
//		AssetLibrary asset = new AssetLibrary(asset_type , asset_name, asset_size, ProfilePicUri);
//		System.out.println("  Type     " + asset_type + "    Name:     " + asset_name + "   Size:   " + asset_size);
//		PeopleLibrary people = new PeopleLibrary();
//		people.setPeople_id(people_id);
//		people.setPeople_name(people_name);
//		people.setBio(bio);
//		people.setPeople_asset(asset);
//		return peopleservice.updatePeople(people);
//
//		
//
//	}
	
	
	@DeleteMapping("/peoplelibrary/{id}")
	public void deletePeople(@PathVariable long id) {
		peopleservice.deletePeople(id);
	}

}

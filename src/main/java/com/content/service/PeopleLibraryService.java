package com.content.service;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.content.config.ContentConfig;
import com.content.dao.PeopleLibraryRepo;
import com.content.exception.FileStorageException;
import com.content.exception.MyFileNotFoundException;
import com.content.model.ContentLibrary;
import com.content.model.PeopleLibrary;
import com.content.util.AppConstants;


@Service
public class PeopleLibraryService {
	@Autowired
	PeopleLibraryRepo peoplerepo;
	
	private final Path fileStorageLocation;

	@Autowired
	public PeopleLibraryService(ContentConfig fileStorageProperties) {
		this.fileStorageLocation = Paths.get("./uploads").toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException(AppConstants.FILE_STORAGE_EXCEPTION_PATH_NOT_FOUND, ex);
		}
	}
	
	public List<PeopleLibrary> getAllPeoples(){
		return peoplerepo.findAll();
	}
	
	public void createPeople(PeopleLibrary peoplelibrary) throws Exception {
		Optional<PeopleLibrary> st = peoplerepo.findById(peoplelibrary.getPeople_id());
		if (st.isPresent()) {
			throw new Exception(" is already present");
		}
        
		peoplerepo.save(peoplelibrary);
	}
	
	public Optional<PeopleLibrary> findPeopleById(long id){
		return peoplerepo.findById(id); 
		
	}
	
	public PeopleLibrary updatePeople(PeopleLibrary peoplelibrary) throws Exception {
		PeopleLibrary st = peoplerepo.findById(peoplelibrary.getPeople_id()).get();
		
		
		
//	        	if(peoplelibrary.getPeople_name()!=null) {
//	        		st.setPeople_name(peoplelibrary.getPeople_name());
//	        	}
//	        	if(peoplelibrary.getBio()!=null) {
//	        		st.setBio(peoplelibrary.getBio());
//	        	}
//	        	if(peoplelibrary.getPeople_asset()!=null) {
//	        		st.setPeople_asset(peoplelibrary.getPeople_asset());
//	        		
//	        	}
	        	return peoplerepo.save(st);
	       
	}
	
	//delete
	public void deletePeople(long id) {
		 peoplerepo.deleteById(null);
	}
	
	
	
	
	//file 
	

	
	// Profile picture file
	public String storeProfilePicture(MultipartFile profile_pic) throws IOException {
		if (!(profile_pic.getOriginalFilename().endsWith(AppConstants.PNG_FILE_FORMAT)
				|| profile_pic.getOriginalFilename().endsWith(AppConstants.JPEG_FILE_FORMAT)
				|| profile_pic.getOriginalFilename().endsWith(AppConstants.JPG_FILE_FORMAT)))
			throw new FileStorageException(AppConstants.INVALID_FILE_FORMAT);
		File f4 = new File( profile_pic.getOriginalFilename());
		f4.createNewFile();

		FileOutputStream fout4 = new FileOutputStream(f4);
		fout4.write(profile_pic.getBytes());
		fout4.close();

//		BufferedImage image = ImageIO.read(f4);
//		int height = image.getHeight();
//		int width = image.getWidth();
//		if (width > 288 || height > 424) { // 288X424
//			if (f4.exists())
//				f4.delete();
//			throw new FileStorageException(AppConstants.INVALID_FILE_DIMENSIONS);
//		}

		if (f4.exists())
			f4.delete();
		String profile_fileName = StringUtils.cleanPath(profile_pic.getOriginalFilename());
		try {
			if (profile_fileName.contains(AppConstants.INVALID_FILE_DELIMITER)) {
				throw new FileStorageException(AppConstants.INVALID_FILE_PATH_NAME);
			}
			String profileFileName = System.currentTimeMillis() + AppConstants.FILE_SEPERATOR + profile_fileName;
			Path targetLocation1 = this.fileStorageLocation.resolve( profileFileName);
			Files.copy(profile_pic.getInputStream(), targetLocation1, StandardCopyOption.REPLACE_EXISTING);
			return  profileFileName;
		}

		catch (IOException ex) {
			throw new FileStorageException(String.format(AppConstants.FILE_STORAGE_EXCEPTION, profile_fileName), ex);
		}

	}
	
	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException(AppConstants.FILE_NOT_FOUND + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new MyFileNotFoundException(AppConstants.FILE_NOT_FOUND + fileName, ex);
		}
	}
	

}

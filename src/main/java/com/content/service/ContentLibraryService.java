package com.content.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.content.config.ContentConfig;
import com.content.dao.ContentLibraryRepo;
import com.content.dao.PeopleLibraryRepo;
import com.content.exception.FileStorageException;
import com.content.exception.MyFileNotFoundException;
import com.content.model.Category;
import com.content.model.ContentLibrary;
import com.content.model.ContentPeople;
import com.content.model.PeopleLibrary;
import com.content.util.AppConstants;

@Service
public class ContentLibraryService {

	@Autowired
	ContentLibraryRepo contentrepo;
	@Autowired
	PeopleLibraryService peopleservice;
	@Autowired
	PeopleLibraryRepo peoplerepo;

	private final Path fileStorageLocation;

	@Autowired
	public ContentLibraryService(ContentConfig fileStorageProperties) {
		this.fileStorageLocation = Paths.get("./uploads").toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException(AppConstants.FILE_STORAGE_EXCEPTION_PATH_NOT_FOUND, ex);
		}
	}

	public List<ContentLibrary> getAllContents() {
		return contentrepo.findAll();
	}

	public ContentLibrary createContent(ContentLibrary contentlib) throws Exception {
	
	
		return contentrepo.save(contentlib);
	}

	public ContentLibrary updateContent(ContentLibrary contentlib) throws Exception {
		ContentLibrary content = contentrepo.findById(contentlib.getContent_id()).get();

		
			return contentrepo.save(content);
			

	}

	public void deleteContentById(long id) {
		contentrepo.deleteById(id);
	}

	public Optional<ContentLibrary> findContentById(long id) {

		return contentrepo.findById(id);
	}
	
	
	// primary file
	public String storePrimaryFile(MultipartFile primarymedia) throws IOException {
		File f = new File(primarymedia.getOriginalFilename());
		f.createNewFile();
		FileOutputStream fout = new FileOutputStream(f);
		fout.write(primarymedia.getBytes());
		fout.close();
		if (f.exists())
			f.delete();
		String primary_fileName = StringUtils.cleanPath(primarymedia.getOriginalFilename());
		try {
			if (primary_fileName.contains(AppConstants.INVALID_FILE_DELIMITER)) {

				throw new FileStorageException(AppConstants.INVALID_FILE_PATH_NAME);
			}
			String primaryFileName = System.currentTimeMillis() + AppConstants.FILE_SEPERATOR + primary_fileName;
			Path targetLocation = this.fileStorageLocation.resolve(primaryFileName);
			Files.copy(primarymedia.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return primaryFileName;

		} catch (IOException ex) {
			throw new FileStorageException(String.format(AppConstants.FILE_STORAGE_EXCEPTION, primary_fileName), ex);

		}

	}

	// Preview
	public String storePreviewFile(MultipartFile preview) throws IOException {
		File f1 = new File(preview.getOriginalFilename());
		f1.createNewFile();
		FileOutputStream fout1 = new FileOutputStream(f1);
		fout1.write(preview.getBytes());
		fout1.close();
		if (f1.exists())
			f1.delete();
		String preview_fileName = StringUtils.cleanPath(preview.getOriginalFilename());
		try {
			if (preview_fileName.contains(AppConstants.INVALID_FILE_DELIMITER)) {
				throw new FileStorageException(AppConstants.INVALID_FILE_PATH_NAME);
			}
			String previewFileName = System.currentTimeMillis() + AppConstants.FILE_SEPERATOR + preview_fileName;
			Path targetLocation1 = this.fileStorageLocation.resolve(previewFileName);
			Files.copy(preview.getInputStream(), targetLocation1, StandardCopyOption.REPLACE_EXISTING);
			return previewFileName;
		}

		catch (IOException ex) {
			throw new FileStorageException(String.format(AppConstants.FILE_STORAGE_EXCEPTION, preview_fileName), ex);
		}
	}

	// banner
	public String storeBannerFile(MultipartFile banner) throws IOException {

		File f2 = new File(banner.getOriginalFilename());
		f2.createNewFile();

		FileOutputStream fout2 = new FileOutputStream(f2);
		fout2.write(banner.getBytes());
		fout2.close();
		// BufferedImage image = ImageIO.read(f2);
		// int height = image.getHeight();
		// int width = image.getWidth();
		// if (width > 1600 || height > 560) {// 1600X560
		// if (f2.exists())
		// f2.delete();
		// throw new FileStorageException(AppConstants.INVALID_FILE_DIMENSIONS);
		// }
		if (f2.exists())
			f2.delete();
		String banner_fileName = StringUtils.cleanPath(banner.getOriginalFilename());
		try {
			if (banner_fileName.contains(AppConstants.INVALID_FILE_DELIMITER)) {
				throw new FileStorageException(AppConstants.INVALID_FILE_PATH_NAME);
			}
			String bannerFileName = System.currentTimeMillis() + AppConstants.FILE_SEPERATOR + banner_fileName;
			Path targetLocation1 = this.fileStorageLocation.resolve(bannerFileName);
			Files.copy(banner.getInputStream(), targetLocation1, StandardCopyOption.REPLACE_EXISTING);
			return bannerFileName;
		}

		catch (IOException ex) {
			throw new FileStorageException(String.format(AppConstants.FILE_STORAGE_EXCEPTION, banner_fileName), ex);
		}
	}

	// additional file
	public String storeAdditionalFile(MultipartFile additionalfile) throws IOException {
		File f3 = new File(additionalfile.getOriginalFilename());
		f3.createNewFile();

		FileOutputStream fout3 = new FileOutputStream(f3);
		fout3.write(additionalfile.getBytes());
		fout3.close();

		if (f3.exists())
			f3.delete();
		String additional_fileName = StringUtils.cleanPath(additionalfile.getOriginalFilename());
		try {
			if (additional_fileName.contains(AppConstants.INVALID_FILE_DELIMITER)) {
				throw new FileStorageException(AppConstants.INVALID_FILE_PATH_NAME);
			}
			String additionalFileName = System.currentTimeMillis() + AppConstants.FILE_SEPERATOR + additional_fileName;
			Path targetLocation1 = this.fileStorageLocation.resolve(additionalFileName);
			Files.copy(additionalfile.getInputStream(), targetLocation1, StandardCopyOption.REPLACE_EXISTING);
			return additionalFileName;
		}

		catch (IOException ex) {
			throw new FileStorageException(String.format(AppConstants.FILE_STORAGE_EXCEPTION, additional_fileName), ex);
		}
	}

	// Thumb nail file
	public String storeTumbnailFile(MultipartFile tumbnail) throws IOException {
		File f4 = new File(tumbnail.getOriginalFilename());
		f4.createNewFile();

		FileOutputStream fout4 = new FileOutputStream(f4);
		fout4.write(tumbnail.getBytes());
		fout4.close();

		// BufferedImage image = ImageIO.read(f4);
		// int height = image.getHeight();
		// int width = image.getWidth();
		// if (width > 288 || height > 424) { // 288X424
		// if (f4.exists())
		// f4.delete();
		// throw new FileStorageException(AppConstants.INVALID_FILE_DIMENSIONS);
		// }

		if (f4.exists())
			f4.delete();
		String tumbnail_fileName = StringUtils.cleanPath(tumbnail.getOriginalFilename());
		try {
			if (tumbnail_fileName.contains(AppConstants.INVALID_FILE_DELIMITER)) {
				throw new FileStorageException(AppConstants.INVALID_FILE_PATH_NAME);
			}
			String tumbnailFileName = System.currentTimeMillis() + AppConstants.FILE_SEPERATOR + tumbnail_fileName;
			Path targetLocation1 = this.fileStorageLocation.resolve(tumbnailFileName);
			Files.copy(tumbnail.getInputStream(), targetLocation1, StandardCopyOption.REPLACE_EXISTING);
			return tumbnailFileName;
		}

		catch (IOException ex) {
			throw new FileStorageException(String.format(AppConstants.FILE_STORAGE_EXCEPTION, tumbnail_fileName), ex);
		}
	}

	// file download
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

package com.content.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.content.config.ContentConfig;
import com.content.exception.FileStorageException;
import com.content.util.AppConstants;


@Service
public class FileStorageService {
	private final Path fileStorageLocation;

	@Autowired
	public FileStorageService(ContentConfig fileStorageProperties) {
		this.fileStorageLocation = Paths.get("./uploads").toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException(AppConstants.FILE_STORAGE_EXCEPTION_PATH_NOT_FOUND, ex);
		}
	}

	
	// Tumbnail file
	public String storeTumbnailFile(MultipartFile tumbnail) throws IOException {
		if (!(tumbnail.getOriginalFilename().endsWith(AppConstants.PNG_FILE_FORMAT)
				|| tumbnail.getOriginalFilename().endsWith(AppConstants.JPEG_FILE_FORMAT)
				|| tumbnail.getOriginalFilename().endsWith(AppConstants.JPG_FILE_FORMAT)))
			throw new FileStorageException(AppConstants.INVALID_FILE_FORMAT);
		File f4 = new File( tumbnail.getOriginalFilename());
		f4.createNewFile();

		FileOutputStream fout4 = new FileOutputStream(f4);
		fout4.write(tumbnail.getBytes());
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

}

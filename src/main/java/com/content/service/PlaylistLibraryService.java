package com.content.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.content.config.ContentConfig;
import com.content.dao.PlaylistLibraryRepo;
import com.content.exception.FileStorageException;
import com.content.exception.MyFileNotFoundException;
import com.content.model.PlaylistLibrary;
import com.content.util.AppConstants;

@Service
public class PlaylistLibraryService {
	@Autowired
	PlaylistLibraryRepo playlistrepo;
	
	private final Path fileStorageLocation;
	
	@Autowired
	public PlaylistLibraryService(ContentConfig fileStorageProperties) {
		this.fileStorageLocation = Paths.get("./uploads").toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException(AppConstants.FILE_STORAGE_EXCEPTION_PATH_NOT_FOUND, ex);
		}
	}

	public List<PlaylistLibrary> getAllPlaylist() {
		return playlistrepo.findAll();
	}

	//Create Playlist
	public PlaylistLibrary createPlaylist(PlaylistLibrary playlistlibrary) throws Exception {
		Optional<PlaylistLibrary> pl = playlistrepo.findById(playlistlibrary.getPlaylist_id());
		if (pl.isPresent()) {
			throw new Exception("playlist is already present");
		}
		return playlistrepo.save(playlistlibrary);
	}

	//Category Id
	public Optional<PlaylistLibrary> findCategoryById(long id) {
		return playlistrepo.findById(id);
	}

	public void deletePlaylist(long id) {
		playlistrepo.deleteById(id);}
	
    //Delete
//	public boolean deleteplaylist(long id) {
//		PlaylistLibrary playlist=playlistrepo.findById(id).orElseThrow(()->{throw new MyFileNotFoundException("Id not present");});
//		playlistrepo.delete(playlist);
//		return true;
//	}

	
	
	public PlaylistLibrary updatePlaylistLibrary(PlaylistLibrary playlist) {
		PlaylistLibrary set = playlistrepo.findById(playlist.getPlaylist_id())
		 .orElseThrow(() -> new MyFileNotFoundException("playlist not found for this id" ));
		 set.setPlaylist_id(playlist.getPlaylist_id());
		 set.setPlaylist_type(playlist.getPlaylist_type());
		 set.setTitle(playlist.getTitle());
		 set.setPermalink(playlist.getPermalink());
		 set.setDescription(playlist.getDescription());
		 set.setSearch_tags(playlist.getSearch_tags());
		 set.setCategories(playlist.getCategories());
		 return playlistrepo.save(set);
	}

	public String storeTumbnailFile(MultipartFile tumbnail) throws IOException {
		 if (!(tumbnail.getOriginalFilename().endsWith(AppConstants.PNG_FILE_FORMAT)
		 || tumbnail.getOriginalFilename().endsWith(AppConstants.JPEG_FILE_FORMAT)
		 || tumbnail.getOriginalFilename().endsWith(AppConstants.JPG_FILE_FORMAT)))
		 throw new FileStorageException(AppConstants.INVALID_FILE_FORMAT);
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
	
	public String storeBannerFile(MultipartFile banner) throws IOException {
		 if (!(banner.getOriginalFilename().endsWith(AppConstants.PNG_FILE_FORMAT)
		 || banner.getOriginalFilename().endsWith(AppConstants.JPEG_FILE_FORMAT)
		 || banner.getOriginalFilename().endsWith(AppConstants.JPG_FILE_FORMAT)))
		 throw new FileStorageException(AppConstants.INVALID_FILE_FORMAT);
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
}
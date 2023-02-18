package com.content.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.content.dao.CategoryRepo;
import com.content.dao.ContentLibraryRepo;
import com.content.dao.PeopleLibraryRepo;
import com.content.enumclass.AssetType;
import com.content.model.AssetLibrary;
import com.content.model.Category;
import com.content.model.ContentLibrary;
import com.content.model.PeopleLibrary;
import com.content.service.CategoryService;
import com.content.service.ContentLibraryService;
import com.content.service.PeopleLibraryService;
import com.content.util.AppConstants;

//@RestController
@Controller

public class ContentLibraryController {
	@Autowired
	ContentLibraryService contentservice;
	@Autowired
	CategoryService categoryservice;
	@Autowired
	CategoryRepo categoryrepo;
	@Autowired
	PeopleLibraryRepo peoplerepo;
	@Autowired
	ContentLibraryRepo contentrepo;

	@Autowired
	PeopleLibraryService peopleservice;

	@GetMapping("/")
	public String indexPage() {
		return "index";
	}

	@GetMapping("/contentlibrary")
	public String contentLibraryPage(Model model) {
		List<ContentLibrary> contentlist=contentrepo.findAll();
		model.addAttribute("contentlist", contentlist);
		return "contentlibrary";
	}

	@GetMapping("/addcontent")
	public String addContentPage(Model model) {
		List<Category> categories = categoryrepo.findAll();
		List<PeopleLibrary> peoplelist = peoplerepo.findAll();
		
		model.addAttribute("peoplelist", peoplelist);
		
		
		model.addAttribute("categories", categories);
		return "addcontent";
	}

	@GetMapping("/assetlibrary")
	public String assetLibPage() {
		return "assetlibrary";
	}

	@GetMapping("/playlistlibrary")
	public String playlsiLibPage() {
		return "playlistlibrary";
	}

	@GetMapping("/contentlibraryRest")
	public List<ContentLibrary> getAllContent() {
		return contentservice.getAllContents();
	}

	@PostMapping(value = "/addcontent1", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String createContent(
			@RequestParam("content_type") String content_type,
			@RequestParam("content_format") String content_format,
			@RequestParam("content_group") String content_group,
			@RequestParam("content_name") String content_name,
			@RequestParam("permalink") String permalink,
			@RequestParam("story") String story,
			@RequestParam("search_tags") String search_tags,
			@RequestParam("person_type") String person_type,
			@RequestParam(value="media_assets") MultipartFile media_assets,
			@RequestParam(required=false,value="preview_assets" ) MultipartFile preview_assets,
			@RequestParam(value="additional_assets") MultipartFile additional_assets,
			@RequestParam(value="additional_asset_type") String additional_asset_type,
			@RequestParam(value="thumbnail_assets") MultipartFile thumbnail_assets,
			@RequestParam(value="banner_assets") MultipartFile banner_assets,
			@RequestParam("category_id") long category_id,
			@RequestParam("peoples") List<Long>peoples) throws Exception {

		String media_file = contentservice.storePrimaryFile(media_assets);
	
		AssetLibrary preview_asset=null;
		if(!preview_assets.isEmpty()) {			
			
			String video = "video/mp4";
			String video2 = "video/mkv";
		String preview_file = contentservice.storePreviewFile(preview_assets);
		String preview_file_Uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(AppConstants.DOWNLOAD_PATH)
				.path(preview_file).toUriString();
		// preview file
				String preview_asset_type = preview_assets.getContentType();
				String preview_asset_name = preview_assets.getOriginalFilename();
				long preview_size = preview_assets.getSize();
				String preview_asset_size;

				float preview_size_kb = preview_size / 1024;
				float preview_size_mb = preview_size_kb / 1024;

				if (preview_size_mb > 0) {
					preview_asset_size = preview_size_mb + " MB";
				} else {
					preview_asset_size = preview_size_kb + " KB";
				}
				System.out.println("  Type     " + preview_asset_type + "    Name:     " + preview_asset_name + "   Size:   "
						+ preview_asset_size);

				AssetType preview_type_asset = null;

				if (video.equals(preview_asset_type) || video2.equals(preview_asset_type)) {
					preview_type_asset = AssetType.Video;
				}
				
		 preview_asset = new AssetLibrary(preview_type_asset, preview_asset_name, preview_asset_size,
				preview_file_Uri);
		}
		String additional_file = contentservice.storeAdditionalFile(additional_assets);
		String thumbnail_file = contentservice.storeTumbnailFile(thumbnail_assets);
		String banner_file = contentservice.storeBannerFile(banner_assets);
		String media_file_Uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(AppConstants.DOWNLOAD_PATH)
				.path(media_file).toUriString();
		
		String additional_file_Uri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path(AppConstants.DOWNLOAD_PATH).path(additional_file).toUriString();
		String thumbnail_file_Uri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path(AppConstants.DOWNLOAD_PATH).path(thumbnail_file).toUriString();
		String banner_file_Uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(AppConstants.DOWNLOAD_PATH)
				.path(banner_file).toUriString();

		String image = "image/jpeg";
		String image2 = "image/png";
		String audio = "audio/mp3";
		String audio2 = "audio/mpeg";
		String video = "video/mp4";
		String video2 = "video/mkv";
		String pdf = "application/pdf";
		// media file
		String media_asset_type = media_assets.getContentType();
		String media_asset_name = media_assets.getOriginalFilename();
		long media_size = media_assets.getSize();
		String media_asset_size;

		float size_kb = media_size / 1024;
		float size_mb = size_kb / 1024;

		if (size_mb > 0) {
			media_asset_size = size_mb + " MB";
		} else {
			media_asset_size = size_kb + " KB";
		}

		AssetType media_type_asset = null;

		if (audio.equals(media_asset_type) || audio2.equals(media_asset_type)) {
			media_type_asset = AssetType.Audio;
		} else if (video.equals(media_asset_type) || video2.equals(media_asset_type)) {
			media_type_asset = AssetType.Video;
		}

		System.out.println("  Type     " + media_asset_type + "    Name:     " + media_asset_name + "   Size:   "
				+ media_asset_size);

		AssetLibrary media_asset = new AssetLibrary(media_type_asset, media_asset_name, media_asset_size,
				media_file_Uri);

		

		// Additional file

		String additional__type = additional_assets.getContentType();
		String additional_asset_name = additional_assets.getOriginalFilename();
		long additional_size = additional_assets.getSize();
		String additional_asset_size;

		float additional_size_kb = additional_size / 1024;
		float additional_size_mb = additional_size_kb / 1024;

		if (additional_size_mb > 0) {
			additional_asset_size = additional_size_mb + " MB";
		} else {
			additional_asset_size = additional_size_kb + " KB";
		}

		AssetType additional_type_asset = null;

		if (audio.equals(additional__type) || audio2.equals(additional__type)) {
			additional_type_asset = AssetType.Audio;
		} else if (video.equals(additional__type) || video2.equals(additional__type)) {
			additional_type_asset = AssetType.Video;
		} else if (pdf.equals(additional__type)) {
			additional_type_asset = AssetType.File;

		}

		AssetLibrary additional_asset = new AssetLibrary(additional_type_asset, additional_asset_name,
				additional_asset_size, additional_file_Uri);

		// Thumb nail file
		String thumbnail_asset_type = thumbnail_assets.getContentType();
		String thumbnail_asset_name = thumbnail_assets.getOriginalFilename();
		long thumbnail_size = thumbnail_assets.getSize();
		String thumbnail_asset_size;

		float thumbnail_size_kb = thumbnail_size / 1024;
		float thumbnail_size_mb = thumbnail_size_kb / 1024;

		if (thumbnail_size_mb > 0) {
			thumbnail_asset_size = thumbnail_size_mb + " MB";
		} else {
			thumbnail_asset_size = thumbnail_size_kb + " KB";
		}
		System.out.println("  Type     " + thumbnail_asset_type + "    Name:     " + thumbnail_asset_name
				+ "   Size:   " + thumbnail_asset_size);

		AssetType thumnail_type_asset = null;

		if (image.equals(thumbnail_asset_type) || image2.equals(thumbnail_asset_type)) {
			thumnail_type_asset = AssetType.Image;
		}
		AssetLibrary thumbnail_asset = new AssetLibrary(thumnail_type_asset, thumbnail_asset_name,
				thumbnail_asset_size, thumbnail_file_Uri);

		// banner file
		String banner_asset_type = banner_assets.getContentType();
		String banner_asset_name = banner_assets.getOriginalFilename();
		long banner_size = banner_assets.getSize();
		String banner_asset_size;

		float banner_size_kb = banner_size / 1024;
		float banner_size_mb = banner_size_kb / 1024;

		if (banner_size_mb > 0) {
			banner_asset_size = banner_size_mb + " MB";
		} else {
			banner_asset_size = banner_size_kb + " KB";
		}
		System.out.println("  Type     " + banner_asset_type + "    Name:     " + banner_asset_name + "   Size:   "
				+ banner_asset_size);
		AssetType banner_type_asset = null;

		if (image.equals(thumbnail_asset_type) || image2.equals(thumbnail_asset_type)) {
			banner_type_asset = AssetType.Image;
		}

		AssetLibrary banner_asset = new AssetLibrary(banner_type_asset, banner_asset_name, banner_asset_size,
				banner_file_Uri);

		// category id
		try {
			Optional<Category> categoryId = categoryservice.getCategoryById(category_id);
			if (categoryId.isEmpty())
				throw new Exception("Category id is empty");
			Category cate = new Category(category_id);

			List<PeopleLibrary> peopleids = new ArrayList<>();
			 for (int i = 0; i < peoples.size(); i++) {
			Optional<PeopleLibrary> peopleId = peopleservice.findPeopleById(peoples.get(i));// peoples.get(i)
			if (peopleId.isEmpty())
				throw new Exception("people id is empty");
			peopleids.add(new PeopleLibrary(peoples.get(i)));// peoples.get(i)

			 }
			ContentLibrary contentlibrary = new ContentLibrary();

			contentlibrary.setContent_type(content_type);
			contentlibrary.setContent_format(content_format);
			contentlibrary.setContent_name(content_name);
			contentlibrary.setContent_group(content_group);
			contentlibrary.setPermalink(permalink);
			contentlibrary.setStory(story);
			contentlibrary.setSearch_tags(search_tags);
			contentlibrary.setPerson_type(person_type);
			contentlibrary.setMedia_assets(media_asset);
			contentlibrary.setPreview_assets(preview_asset);
			contentlibrary.setAdditional_asset_type(additional_asset_type);
			contentlibrary.setAdditional_assets(additional_asset);
			contentlibrary.setThumbnail_assets(thumbnail_asset);
			contentlibrary.setBanner_assets(banner_asset);
			contentlibrary.setCategories(cate);
			contentlibrary.setPeople_library(peopleids);

			contentservice.createContent(contentlibrary);

		} catch (Exception ex) {
			System.out.println(ex);
		}

		return "redirect:/contentlibrary";
	}

	@PutMapping(value = "/updatecontent/{content_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String updateContent(@PathVariable("content_id") long content_id,
			// @RequestParam("content_type") String content_type,
			// @RequestParam("content_format") String content_format,
			// @RequestParam("content_group") String content_group,
			// @RequestParam("content_id") long
			@RequestParam("content_name") String content_name,
			@RequestParam("permalink") String permalink,
			@RequestParam("story") String story, @RequestParam("search_tags") String search_tags,
			@RequestParam("person_type") String person_type,
			@RequestParam("media_assets") MultipartFile media_assets,
			@RequestParam("preview_assets") MultipartFile preview_assets,
			@RequestParam("additional_assets") MultipartFile additional_assets,
			@RequestParam("thumbnail_assets") MultipartFile thumbnail_assets,
			@RequestParam("banner_assets") MultipartFile banner_assets,
			@RequestParam("category_id") long category_id,
			// @RequestParam("peoples") long peoples
			@RequestParam("peoples") List<Long> peoples) throws Exception {
		String image = "image/jpeg";
		String image2 = "image/png";
		String audio = "audio/mp3";
		String audio2 = "audio/mpeg";
		String video = "video/mp4";
		String video2 = "video/mkv";
		String pdf = "application/pdf";
		String media_file = contentservice.storePrimaryFile(media_assets);
		String preview_file = contentservice.storePreviewFile(preview_assets);
		String additional_file = contentservice.storeAdditionalFile(additional_assets);
		String thumbnail_file = contentservice.storeTumbnailFile(thumbnail_assets);
		String banner_file = contentservice.storeBannerFile(banner_assets);
		String media_file_Uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(AppConstants.DOWNLOAD_PATH)
				.path(media_file).toUriString();
		String preview_file_Uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(AppConstants.DOWNLOAD_PATH)
				.path(preview_file).toUriString();
		String additional_file_Uri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path(AppConstants.DOWNLOAD_PATH).path(additional_file).toUriString();
		String thumbnail_file_Uri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path(AppConstants.DOWNLOAD_PATH).path(thumbnail_file).toUriString();
		String banner_file_Uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(AppConstants.DOWNLOAD_PATH)
				.path(banner_file).toUriString();

		// media file
		String media_asset_type = media_assets.getContentType();
		String media_asset_name = media_assets.getOriginalFilename();
		long media_size = media_assets.getSize();
		String media_asset_size;

		float size_kb = media_size / 1024;
		float size_mb = size_kb / 1024;

		if (size_mb > 0) {
			media_asset_size = size_mb + " MB";
		} else {
			media_asset_size = size_kb + " KB";
		}
		AssetType media_type_asset = null;

		if (audio.equals(media_asset_type) || audio2.equals(media_asset_type)) {
			media_type_asset = AssetType.Audio;
		} else if (video.equals(media_asset_type) || video2.equals(media_asset_type)) {
			media_type_asset = AssetType.Video;
		}

		System.out.println("  Type     " + media_asset_type + "    Name:     " + media_asset_name + "   Size:   "
				+ media_asset_size);

		AssetLibrary media_asset = new AssetLibrary(media_type_asset, media_asset_name, media_asset_size,
				media_file_Uri);

		// preview file
		String preview_asset_type = preview_assets.getContentType();
		String preview_asset_name = preview_assets.getOriginalFilename();
		long preview_size = preview_assets.getSize();
		String preview_asset_size;

		float preview_size_kb = preview_size / 1024;
		float preview_size_mb = preview_size_kb / 1024;

		if (preview_size_mb > 0) {
			preview_asset_size = preview_size_mb + " MB";
		} else {
			preview_asset_size = preview_size_kb + " KB";
		}
		System.out.println("  Type     " + preview_asset_type + "    Name:     " + preview_asset_name + "   Size:   "
				+ preview_asset_size);

		AssetType preview_type_asset = null;

		if (video.equals(preview_asset_type) || video2.equals(preview_asset_type)) {
			preview_type_asset = AssetType.Video;
		}
		AssetLibrary preview_asset = new AssetLibrary(preview_type_asset, preview_asset_name, preview_asset_size,
				preview_file_Uri);

		// Additional file

		String additional_asset_type = additional_assets.getContentType();
		String additional_asset_name = additional_assets.getOriginalFilename();
		long additional_size = additional_assets.getSize();
		String additional_asset_size;

		float additional_size_kb = additional_size / 1024;
		float additional_size_mb = additional_size_kb / 1024;

		if (additional_size_mb > 0) {
			additional_asset_size = additional_size_mb + " MB";
		} else {
			additional_asset_size = additional_size_kb + " KB";
		}
		System.out.println("  Type     " + preview_asset_type + "    Name:     " + preview_asset_name + "   Size:   "
				+ preview_asset_size);
		AssetType additional_type_asset = null;

		if (audio.equals(additional_asset_type) || audio2.equals(additional_asset_type)) {
			additional_type_asset = AssetType.Audio;
		} else if (video.equals(additional_asset_type) || video2.equals(additional_asset_type)) {
			additional_type_asset = AssetType.Video;
		} else if (pdf.equals(additional_asset_type)) {
			additional_type_asset = AssetType.File;

		}

		AssetLibrary additional_asset = new AssetLibrary(additional_type_asset, additional_asset_name,
				additional_asset_size, additional_file_Uri);

		// Thumb nail file
		String thumbnail_asset_type = thumbnail_assets.getContentType();
		String thumbnail_asset_name = thumbnail_assets.getOriginalFilename();
		long thumbnail_size = thumbnail_assets.getSize();
		String thumbnail_asset_size;

		float thumbnail_size_kb = thumbnail_size / 1024;
		float thumbnail_size_mb = thumbnail_size_kb / 1024;

		if (thumbnail_size_mb > 0) {
			thumbnail_asset_size = thumbnail_size_mb + " MB";
		} else {
			thumbnail_asset_size = thumbnail_size_kb + " KB";
		}
		System.out.println("  Type     " + thumbnail_asset_type + "    Name:     " + thumbnail_asset_name
				+ "   Size:   " + thumbnail_asset_size);
		AssetType thumnail_type_asset = null;

		if (image.equals(thumbnail_asset_type) || image2.equals(thumbnail_asset_type)) {
			thumnail_type_asset = AssetType.Image;
		}
		AssetLibrary thumbnail_asset = new AssetLibrary(thumnail_type_asset, thumbnail_asset_name,
				thumbnail_asset_size, thumbnail_file_Uri);

		// banner file
		String banner_asset_type = banner_assets.getContentType();
		String banner_asset_name = banner_assets.getOriginalFilename();
		long banner_size = banner_assets.getSize();
		String banner_asset_size;

		float banner_size_kb = banner_size / 1024;
		float banner_size_mb = banner_size_kb / 1024;

		if (banner_size_mb > 0) {
			banner_asset_size = banner_size_mb + " MB";
		} else {
			banner_asset_size = banner_size_kb + " KB";
		}
		System.out.println("  Type     " + banner_asset_type + "    Name:     " + banner_asset_name + "   Size:   "
				+ banner_asset_size);
		AssetType banner_type_asset = null;

		if (image.equals(thumbnail_asset_type) || image2.equals(thumbnail_asset_type)) {
			banner_type_asset = AssetType.Image;
		}

		AssetLibrary banner_asset = new AssetLibrary(banner_type_asset, banner_asset_name, banner_asset_size,
				banner_file_Uri);

		// category id
		try {
			Optional<Category> categoryId = categoryservice.getCategoryById(category_id);
			if (categoryId.isEmpty())
				throw new Exception("Category id is empty");
			Category cate = new Category(category_id);

			List<PeopleLibrary> peopleids = new ArrayList<>();
			for (int i = 0; i < peoples.size(); i++) {
				Optional<PeopleLibrary> peopleId = peopleservice.findPeopleById(peoples.get(i));// peoples.get(i)
				if (peopleId.isEmpty())
					throw new Exception("people id is empty");

				peopleids.add(peopleservice.updatePeople(new PeopleLibrary(peoples.get(i))));// peoples.get(i)

			}

			ContentLibrary contentlibrary = new ContentLibrary();
			// contentlibrary.setContent_type(content_type);
			// contentlibrary.setContent_format(content_format);
			// contentlibrary.setContent_group(content_group);
			contentlibrary.setContent_name(content_name);
			contentlibrary.setContent_id(content_id);
			contentlibrary.setPermalink(permalink);
			contentlibrary.setStory(story);
			contentlibrary.setSearch_tags(search_tags);
			contentlibrary.setPerson_type(person_type);
			contentlibrary.setMedia_assets(media_asset);
			contentlibrary.setPreview_assets(preview_asset);
			contentlibrary.setAdditional_assets(additional_asset);
			contentlibrary.setThumbnail_assets(thumbnail_asset);
			contentlibrary.setBanner_assets(banner_asset);
			contentlibrary.setCategories(categoryservice.updateCategory(cate));
			contentlibrary.setPeople_library(peopleids);

			contentservice.updateContent(contentlibrary);

		} catch (Exception ex) {
			System.out.println(ex);
		}

		return "contentlibrary";
	}

	@DeleteMapping("/deleteContent/{id}")
	public void deleteContentById(@PathVariable long id) {
		contentservice.deleteContentById(id);
	}

}

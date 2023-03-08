package com.content.controller;



import java.text.DecimalFormat;
import java.util.*;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.content.dao.CategoryRepo;
import com.content.dao.ContentLibraryRepo;
import com.content.dao.ContentPeopleRepo;
import com.content.dao.PeopleLibraryRepo;
import com.content.enumclass.AssetType;
import com.content.model.AssetLibrary;
import com.content.model.Category;
import com.content.model.ContentLibrary;
import com.content.model.ContentPeople;
import com.content.model.PeopleLibrary;
import com.content.service.CategoryService;
import com.content.service.ContentLibraryService;
import com.content.service.ContentPeopleService;
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
	@Autowired
	ContentPeopleService contentpeopleservice;
	@Autowired
	ContentPeopleRepo contentpeoplerepo;
	DecimalFormat df = new DecimalFormat("#.##");
	@GetMapping("/")
	public String indexPage() {
		return "index";
	}

	@GetMapping("/contentlibrary")
	public String contentLibraryPage(Model model) {
		List<ContentLibrary> contentlist = contentrepo.findAll();
		model.addAttribute("contentlist", contentlist);
		return "contentlibrary";
	}

	@GetMapping("/addcontent")
	public String addContentPage(Model model) {
		List<Category> categories = categoryrepo.findAll();
		List<PeopleLibrary> peoplelist = peoplerepo.findAll();

		model.addAttribute("peoplelist", peoplelist);
		model.addAttribute("contentlibrary", new ContentLibrary());

		model.addAttribute("categories", categories);
		return "addcontent";
	}

	@GetMapping("/contnentsettings")
	public String playlsiLibPage() {
		return "Contentsettings";
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
			@RequestParam(required = false, value ="story") String story,
			@RequestParam(required = false, value ="search_tags") String search_tags,
			
			@RequestParam(required = false,value = "media_assets") MultipartFile media_assets,
			@RequestParam(required = false, value = "preview_assets") MultipartFile preview_assets,
			@RequestParam(required = false,value = "additional_assets") MultipartFile additional_assets,
			@RequestParam(required = false,value = "additional_asset_type") String additional_asset_type,
			@RequestParam(required = false,value = "thumbnail_assets") MultipartFile thumbnail_assets,
			@RequestParam(required = false,value = "banner_assets") MultipartFile banner_assets,
			@RequestParam("category_id") long category_id,
			@RequestParam(required = false, value ="person_type") List<String>person_type,
	        @RequestParam(required = false, value ="people") List<Long> people,RedirectAttributes redirAttrs
	) throws Exception {

		
		
		
		String image = "image/jpeg";
		String image2 = "image/png";
		String audio = "audio/mp3";
		String audio2 = "audio/mpeg";
		String video = "video/mp4";
		String video2 = "video/mkv";
		String pdf = "application/pdf";
	
		AssetLibrary preview_asset = null;
		if (!preview_assets.isEmpty()) {

			String preview_file = contentservice.storePreviewFile(preview_assets);
			String preview_file_Uri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path(AppConstants.DOWNLOAD_PATH)
					.path(preview_file).toUriString();
			// preview file
			String preview_asset_type = preview_assets.getContentType();
			String preview_asset_name = preview_assets.getOriginalFilename();
			long preview_size = preview_assets.getSize();
			String preview_asset_size;

			float preview_size_kb = preview_size / 1024;
			float preview_size_mb = preview_size_kb / 1024;

			if (preview_size_mb > 0) {
				preview_asset_size = df.format(preview_size_mb) + " MB";
			} else {
				preview_asset_size = df.format(preview_size_kb) + " KB";
			}
			System.out
					.println("  Type     " + preview_asset_type + "    Name:     " + preview_asset_name + "   Size:   "
							+ preview_asset_size);

			AssetType preview_type_asset = null;

			if (video.equals(preview_asset_type) || video2.equals(preview_asset_type)) {
				preview_type_asset = AssetType.Video;
				
				
			}
			
			preview_asset = new AssetLibrary(preview_type_asset, preview_asset_name, preview_asset_size,
					preview_file_Uri);
			
		}
		
		
		// media file
		AssetLibrary media_asset=null;
		
		if(!media_assets.isEmpty()) {
			String media_file = contentservice.storePrimaryFile(media_assets);
			String media_file_Uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(AppConstants.DOWNLOAD_PATH)
					.path(media_file).toUriString();
		String media_asset_type = media_assets.getContentType();
		String media_asset_name = media_assets.getOriginalFilename();
		long media_size = media_assets.getSize();
		String media_asset_size;

		float size_kb =  media_size/ 1024;
		float size_mb = size_kb / 1024;

		if (size_mb > 0) {
			media_asset_size = df.format(size_mb) + " MB";
		} else {
			media_asset_size =df.format( size_kb) + " KB";
		}

		AssetType media_type_asset = null;

		if (audio.equals(media_asset_type) || audio2.equals(media_asset_type)) {
			media_type_asset = AssetType.Audio;
		} else if (video.equals(media_asset_type) || video2.equals(media_asset_type)) {
			media_type_asset = AssetType.Video;
		}

		
		 media_asset = new AssetLibrary(media_type_asset, media_asset_name, media_asset_size,
				media_file_Uri);
		 }

		// Additional file
		AssetLibrary additional_asset=null;
		if(!additional_assets.isEmpty()) {
			String additional_file = contentservice.storeAdditionalFile(additional_assets);
			String additional_file_Uri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path(AppConstants.DOWNLOAD_PATH).path(additional_file).toUriString();
		String additional__type = additional_assets.getContentType();
		String additional_asset_name = additional_assets.getOriginalFilename();
		long additional_size = additional_assets.getSize();
		String additional_asset_size;

		float additional_size_kb = additional_size / 1024;
		float additional_size_mb = additional_size_kb / 1024;

		if (additional_size_mb > 0) {
			additional_asset_size = df.format(additional_size_mb) + " MB";
		} else {
			additional_asset_size = df.format(additional_size_kb) + " KB";
		}

		AssetType additional_type_asset = null;

		if (audio.equals(additional__type) || audio2.equals(additional__type)) {
			additional_type_asset = AssetType.Audio;
		} else if (video.equals(additional__type) || video2.equals(additional__type)) {
			additional_type_asset = AssetType.Video;
		} else if (pdf.equals(additional__type)) {
			additional_type_asset = AssetType.File;

		}

		 additional_asset = new AssetLibrary(additional_type_asset, additional_asset_name,
				additional_asset_size, additional_file_Uri);
		}

		// Thumb nail file
		AssetLibrary thumbnail_asset = null;
		if(!thumbnail_assets.isEmpty()) {
			String thumbnail_file = contentservice.storeTumbnailFile(thumbnail_assets);
			String thumbnail_file_Uri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path(AppConstants.DOWNLOAD_PATH).path(thumbnail_file).toUriString();
		String thumbnail_asset_type = thumbnail_assets.getContentType();
		String thumbnail_asset_name = thumbnail_assets.getOriginalFilename();
		long thumbnail_size = thumbnail_assets.getSize();
		String thumbnail_asset_size;

		float thumbnail_size_kb = thumbnail_size / 1024;
		float thumbnail_size_mb = thumbnail_size_kb / 1024;

		if (thumbnail_size_mb > 0) {
			thumbnail_asset_size = df.format(thumbnail_size_mb) + " MB";
		} else {
			thumbnail_asset_size = df.format(thumbnail_size_kb) + " KB";
		}
		
		AssetType thumnail_type_asset = null;

		if (image.equals(thumbnail_asset_type) || image2.equals(thumbnail_asset_type)) {
			thumnail_type_asset = AssetType.Image;
		}
	 thumbnail_asset = new AssetLibrary(thumnail_type_asset, thumbnail_asset_name,
				thumbnail_asset_size, thumbnail_file_Uri);
	 }

		// banner file
		AssetLibrary banner_asset=null;
		if(!banner_assets.isEmpty()) {
			String banner_file = contentservice.storeBannerFile(banner_assets);
			String banner_file_Uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(AppConstants.DOWNLOAD_PATH)
					.path(banner_file).toUriString();
		String banner_asset_type = banner_assets.getContentType();
		String banner_asset_name = banner_assets.getOriginalFilename();
		long banner_size = banner_assets.getSize();
		String banner_asset_size;

		float banner_size_kb = banner_size / 1024;
		float banner_size_mb = banner_size_kb / 1024;

		if (banner_size_mb > 0) {
			banner_asset_size = df.format(banner_size_mb) + " MB";
		} else {
			banner_asset_size = df.format(banner_size_kb) + " KB";
		}
		
		AssetType banner_type_asset = null;

		if (image.equals(banner_asset_type) || image2.equals(banner_asset_type)) {
			banner_type_asset = AssetType.Image;
		}

		 banner_asset = new AssetLibrary(banner_type_asset, banner_asset_name, banner_asset_size,
				banner_file_Uri);
		 }

		// category id
		try {
			Optional<Category> categoryId = categoryservice.getCategoryById(category_id);
			if (categoryId.isEmpty()) {
				throw new Exception("Category id is empty");
			}
			Category cate = new Category(category_id);
			
			ContentLibrary contentlibrary = new ContentLibrary();

			contentlibrary.setContent_type(content_type);
			contentlibrary.setContent_format(content_format);
			contentlibrary.setContent_name(content_name);
			contentlibrary.setContent_group(content_group);
			contentlibrary.setPermalink(permalink);
			contentlibrary.setStory(story);
			contentlibrary.setSearch_tags(search_tags);
			contentlibrary.setMedia_assets(media_asset);
			contentlibrary.setPreview_assets(preview_asset);
			contentlibrary.setAdditional_asset_type(additional_asset_type);
			contentlibrary.setAdditional_assets(additional_asset);
			contentlibrary.setThumbnail_assets(thumbnail_asset);
			contentlibrary.setBanner_assets(banner_asset);
			contentlibrary.setCategories(cate);
			List<ContentPeople> contentpeople=new ArrayList<>();
			for(int i=0;i<people.size();i++) {
				Optional<PeopleLibrary> peopleId = peopleservice.findPeopleById(people.get(i));
				if(peopleId.isEmpty()) {
					 throw new Exception("People id is empty");
					 
				}
	
				contentpeople.add(new ContentPeople(person_type.get(i),new PeopleLibrary(people.get(i)),contentlibrary));
				}
			contentlibrary.setContenpeople(contentpeople);
			 
 	
			 contentservice.createContent(contentlibrary);
		
			
			 
		} catch (Exception ex) {
			System.out.println(ex);
		}

		return "redirect:/contentlibrary";
	}

	@GetMapping("/updatecontent/{id}")
	public String updatePage(Model model, @PathVariable long id) {
		List<Category> categories = categoryrepo.findAll();
		List<PeopleLibrary> peoplelist = peoplerepo.findAll();
		ContentLibrary content = contentrepo.findById(id).get();
		Category cat=content.getCategories();
		String value=content.getContent_format();
		AssetLibrary prev=null;
		if(value.equals("video")) {
			 prev=content.getPreview_assets();
			 Long previd=null;
			 if(prev!=null) {
				previd=prev.getAsset_id();
			 }
			
			model.addAttribute("previd", previd);
		}
	
		
		model.addAttribute("cat", cat);
		
		model.addAttribute("peoplelist", peoplelist);
		model.addAttribute("contentlib", content);

		model.addAttribute("allcategories", categories);
		return "updatecontent";
	}


	@PostMapping(value = "/updatecontent1", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Transactional
	public String updateContent(
			@RequestParam("content_id") long content_id,
			Model model,
//			 @RequestParam("content_type") String content_type,
//			 @RequestParam("content_format") String content_format,
//			 @RequestParam("content_group") String content_group,
			
			@RequestParam("content_name") String content_name,
			@RequestParam("permalink") String permalink,
			@RequestParam(required=false,value="story") String story, 
			@RequestParam(required=false,value="search_tags") String search_tags,
			
			@RequestParam(required=false,value="media_assets") MultipartFile media_assets,
			@RequestParam(required=false,value="preview_assets") MultipartFile preview_assets,
			@RequestParam(required=false,value="additional_assets") MultipartFile additional_assets,
			@RequestParam(required=false,value="thumbnail_assets") MultipartFile thumbnail_assets,
			@RequestParam(required=false,value="banner_assets") MultipartFile banner_assets,
			@RequestParam(required=false,value="category_id") long category_id,
			@RequestParam(required=false,value="additional_asset_type") String additional_asset_type,
//			@RequestParam("content_people_id")List<Long>content_people_id,
			@RequestParam(required=false,value="person_type") List<String> person_type,
		@RequestParam(required=false,value="people") List<Long> people
			) throws Exception {
	
	
		String image = "image/jpeg";
		String image2 = "image/png";
		String audio = "audio/mp3";
		String audio2 = "audio/mpeg";
		String video = "video/mp4";
		String video2 = "video/mkv";
		String pdf = "application/pdf";
		
		AssetLibrary preview_asset=null;

		if(!preview_assets.isEmpty() ) {	
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
					preview_asset_size = df.format(preview_size_mb) + " MB";
				} else {
					preview_asset_size = df.format(preview_size_kb) + " KB";
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
		
		
//	}
		
		
		// media file
		
		AssetLibrary media_asset=null;
		if(!media_assets.isEmpty()) {
			String media_file = contentservice.storePrimaryFile(media_assets);
			String media_file_Uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(AppConstants.DOWNLOAD_PATH)
					.path(media_file).toUriString();
			String media_asset_type = media_assets.getContentType();
			String media_asset_name = media_assets.getOriginalFilename();
			long media_size = media_assets.getSize();
			String media_asset_size;

			float size_kb = media_size / 1024;
			float size_mb = size_kb / 1024;

			if (size_mb > 0) {
				media_asset_size = df.format(size_mb) + " MB";
			} else {
				media_asset_size = df.format(size_kb) + " KB";
			}
			AssetType media_type_asset = null;

			if (audio.equals(media_asset_type) || audio2.equals(media_asset_type)) {
				media_type_asset = AssetType.Audio;
			} else if (video.equals(media_asset_type) || video2.equals(media_asset_type)) {
				media_type_asset = AssetType.Video;
			}

			
			 media_asset = new AssetLibrary(media_type_asset, media_asset_name, media_asset_size,
					media_file_Uri);
		}
			// Additional file
		AssetLibrary additional_asset=null;
		
        if(!additional_assets.isEmpty()) {
        	String additional_file = contentservice.storeAdditionalFile(additional_assets);
        	String additional_file_Uri = ServletUriComponentsBuilder.fromCurrentContextPath()
    				.path(AppConstants.DOWNLOAD_PATH).path(additional_file).toUriString();
        	String additional__type = additional_assets.getContentType();
    		String additional_asset_name = additional_assets.getOriginalFilename();
    		long additional_size = additional_assets.getSize();
    		String additional_asset_size;

    		float additional_size_kb = additional_size / 1024;
    		float additional_size_mb = additional_size_kb / 1024;

    		if (additional_size_mb > 0) {
    			additional_asset_size = df.format(additional_size_mb) + " MB";
    		} else {
    			additional_asset_size = df.format(additional_size_kb) + " KB";
    		}
    		
    		AssetType additional_type_asset = null;

    		if (audio.equals(additional__type) || audio2.equals(additional__type)) {
    			additional_type_asset = AssetType.Audio;
    		} else if (video.equals(additional__type) || video2.equals(additional__type)) {
    			additional_type_asset = AssetType.Video;
    		} else if (pdf.equals(additional__type)) {
    			additional_type_asset = AssetType.File;

    		}

    		 additional_asset = new AssetLibrary(additional_type_asset, additional_asset_name,
    				additional_asset_size, additional_file_Uri);
              }
		

		// Thumb nail file
        AssetLibrary thumbnail_asset =null;
        
        if(!thumbnail_assets.isEmpty()) {
        	String thumbnail_file = contentservice.storeTumbnailFile(thumbnail_assets);
    		String thumbnail_file_Uri = ServletUriComponentsBuilder.fromCurrentContextPath()
    				.path(AppConstants.DOWNLOAD_PATH).path(thumbnail_file).toUriString();
            
    		String thumbnail_asset_type = thumbnail_assets.getContentType();
    		String thumbnail_asset_name = thumbnail_assets.getOriginalFilename();
    		long thumbnail_size = thumbnail_assets.getSize();
    		String thumbnail_asset_size;

    		float thumbnail_size_kb = thumbnail_size / 1024;
    		float thumbnail_size_mb = thumbnail_size_kb / 1024;

    		if (thumbnail_size_mb > 0) {
    			thumbnail_asset_size = df.format(thumbnail_size_mb) + " MB";
    		} else {
    			thumbnail_asset_size = df.format(thumbnail_size_kb) + " KB";
    		}
    		System.out.println("  Type     " + thumbnail_asset_type + "    Name:     " + thumbnail_asset_name
    				+ "   Size:   " + thumbnail_asset_size);
    		AssetType thumnail_type_asset = null;

    		if (image.equals(thumbnail_asset_type) || image2.equals(thumbnail_asset_type)) {
    			thumnail_type_asset = AssetType.Image;
    		}
    		 thumbnail_asset = new AssetLibrary(thumnail_type_asset, thumbnail_asset_name,
    				thumbnail_asset_size, thumbnail_file_Uri);
        	
        }
        

		// banner file
        
        AssetLibrary banner_asset=null;
        if(!banner_assets.isEmpty()) {
        	String banner_file = contentservice.storeBannerFile(banner_assets);
    		String banner_file_Uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(AppConstants.DOWNLOAD_PATH)
    				.path(banner_file).toUriString();
    		String banner_asset_type = banner_assets.getContentType();
    		String banner_asset_name = banner_assets.getOriginalFilename();
    		long banner_size = banner_assets.getSize();
    		String banner_asset_size;

    		float banner_size_kb = banner_size / 1024;
    		float banner_size_mb = banner_size_kb / 1024;

    		if (banner_size_mb > 0) {
    			banner_asset_size = df.format(banner_size_mb) + " MB";
    		} else {
    			banner_asset_size = df.format(banner_size_kb) + " KB";
    		}
    		System.out.println("  Type     " + banner_asset_type + "    Name:     " + banner_asset_name + "   Size:   "
    				+ banner_asset_size);
    		AssetType banner_type_asset = null;

    		if (image.equals(banner_asset_type) || image2.equals(banner_asset_type)) {
    			banner_type_asset = AssetType.Image;
    		}

    	     banner_asset = new AssetLibrary(banner_type_asset, banner_asset_name, banner_asset_size,
    				banner_file_Uri);
        	
        }
		

		// category id
		
			Optional<Category> categoryId = categoryservice.getCategoryById(category_id);
			if (categoryId.isEmpty())
				throw new Exception("Category id is empty");
			Category cate = new Category(category_id);
	
			
			ContentLibrary contentlibrary = contentrepo.findById(content_id).get();
//			contentlibrary.setContent_id(content_id);
//			 contentlibrary.setContent_type(content_type);
//			 contentlibrary.setContent_format(content_format);
//			 contentlibrary.setContent_group(content_group);
			if(content_name!=null) {
				contentlibrary.setContent_name(content_name);
			}
			if(permalink!=null) {
				contentlibrary.setPermalink(permalink);
			}
			if(story!=null) {
				contentlibrary.setStory(story);
			}
			
			if(search_tags!=null) {
				contentlibrary.setSearch_tags(search_tags);
			}
//			
			if(media_asset!=null) {
				contentlibrary.setMedia_assets(media_asset);
			}
			if(preview_asset!=null) {
				contentlibrary.setPreview_assets(preview_asset);}
			
			if(additional_asset!=null) {
				contentlibrary.setAdditional_assets(additional_asset);
			}
			if(additional_asset_type!=null) {
				contentlibrary.setAdditional_asset_type(additional_asset_type);
			}
			
			if(thumbnail_asset!=null) {
				contentlibrary.setThumbnail_assets(thumbnail_asset);
			}
			if(banner_asset!=null) {
				contentlibrary.setBanner_assets(banner_asset);
			}
			if(categoryservice.updateCategory(cate)!=null) {
				contentlibrary.setCategories(categoryservice.updateCategory(cate));
			}
			List<ContentPeople> contentpeople=new ArrayList<>();
			
			List<ContentPeople> conpeople=contentlibrary.getContenpeople();
			for(int i=0;i<people.size();i++) {
				Optional<PeopleLibrary> peopleId = peopleservice.findPeopleById(people.get(i));
				if(peopleId.isEmpty()) {
					 throw new Exception("People id is empty");
					 
				}
				ContentPeople contentId = contentpeoplerepo.findById(conpeople.get(i).getContent_people_id()).get();
				
				List<PeopleLibrary> peop=new ArrayList<>();
			PeopleLibrary peoples=peoplerepo.findById(people.get(i)).get();
		
				if(people.get(i)!=null) {
				contentId.setPeople_id(peoples);}
				if(contentlibrary!=null) {
				contentId.setContent_id(contentlibrary);}
				if(person_type.get(i)!=null) {
				contentId.setPerson_type(person_type.get(i));
				}	

				contentpeople.add(contentId);
			}

				
//				contentpeople.add(new ContentPeople(person_type.get(i),peoples,contentlibrary));
			
	
			if(contentpeople!=null) {
				
			contentlibrary.setContenpeople(contentpeople);
			}

			contentservice.updateContent(contentlibrary);
			return "redirect:/contentlibrary";

		} 

		
	
	

	
	
	@GetMapping(value = "/updatecontent12", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String updateContent(@RequestParam("id") long id,Model model)
	{
		
		ContentLibrary content=contentrepo.findById(id).get();
		model.addAttribute("content", content);
	
		return "updatecontent";
	}
	@GetMapping("/deleteContent/{id}")
	public String deleteContentById(@PathVariable long id) {
		contentservice.deleteContentById(id);
		return "redirect:/contentlibrary";
	}

}

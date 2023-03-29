package com.content.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.content.dao.AssetLibraryRepo;
import com.content.dao.CategoryRepo;
import com.content.dao.ContentLibraryRepo;
import com.content.dao.PlaylistLibraryRepo;
import com.content.enumclass.AssetType;
import com.content.model.AssetLibrary;
import com.content.model.Category;
import com.content.model.ContentLibrary;
import com.content.model.PlaylistLibrary;
import com.content.service.CategoryService;
import com.content.service.ContentLibraryService;
import com.content.service.FileStorageService;
import com.content.service.PlaylistLibraryService;
import com.content.util.AppConstants;

@Controller
public class PlaylistLibraryController {
	@Autowired
	PlaylistLibraryService playlistservice;

	@Autowired
	FileStorageService fileservice;

	@Autowired
	CategoryService categoryservice;

	@Autowired
	ContentLibraryRepo contentrepo;

	@Autowired
	ContentLibraryService contentlibservice;

	@Autowired
	PlaylistLibraryRepo playlistrepo;

	@Autowired
	CategoryRepo categoryrepo;
	@Autowired
	AssetLibraryRepo assetrepo;
DecimalFormat df=new DecimalFormat();


	@GetMapping("/playlistlibrary")
	public String contentLibraryPage(Model model) {
		List<PlaylistLibrary> playlist = playlistrepo.findAll();
		model.addAttribute("playlist", playlist);
		return "playlistlibrary";
	}
	@GetMapping("/UserPlaylist")
	public String UserplaylistPage(Model model) {
		List<PlaylistLibrary> playlist = playlistrepo.findAll();
		model.addAttribute("playlist", playlist);
		return "UserPlaylist";
	}
	@GetMapping("/UserAbout")
	public String UserAboutPage() {
		return "about";
	}
	@GetMapping("/PlaylistDescription/{id}")
	public String updatePage(Model model, @PathVariable long id) {
		PlaylistLibrary playlist = playlistrepo.findById(id).get();
		List<ContentLibrary> con = playlist.getContentLibrary();
		model.addAttribute("playlist", playlist);
		model.addAttribute("con", con);
		return "PlaylistDescription";
	}
	
	@GetMapping("/Addplaylist")
	public String addPlaylistPage(Model model) {
		List<Category> categories = categoryrepo.findAll();
		List<ContentLibrary> contentlist = contentrepo.findAll();
		List<ContentLibrary> contentvideolist=new ArrayList<>();
		List<ContentLibrary> contentaudiolist=new ArrayList<>();
		for(int i=0;i<contentlist.size();i++) {
		if(contentlist.get(i).getContent_type().equals("video")) {
			 contentvideolist.add(contentlist.get(i));
		}else {		
			
			 contentaudiolist.add(contentlist.get(i));
			
		}
		}
		List<AssetLibrary> asset = assetrepo.findAll();
		model.addAttribute("asset", asset);
		List<AssetLibrary> assetVideo = new ArrayList<>();
		List<AssetLibrary> assetAudio = new ArrayList<>();
		List<AssetLibrary> assetImage = new ArrayList<>();
		List<AssetLibrary> assetFile = new ArrayList<>();
		for (int i = 0; i < asset.size(); i++) {
			if (asset.get(i).getAsset_type().equals(AssetType.Video)) {
         
			
				assetVideo.add(asset.get(i));
			}

			else if (asset.get(i).getAsset_type().equals(AssetType.Audio)) {
				assetAudio.add(asset.get(i));
			}
			else if(asset.get(i).getAsset_type().equals(AssetType.Image)) {
				assetImage.add(asset.get(i));
			}
			else if(asset.get(i).getAsset_type().equals(AssetType.File)) {
				assetFile.add(asset.get(i));

			}
		}
		model.addAttribute("videoasset", assetVideo);
		model.addAttribute("audioasset", assetAudio);
		model.addAttribute("imageasset", assetImage);
		model.addAttribute("fileasset", assetFile);
		model.addAttribute("contentvideolist",contentvideolist );
		model.addAttribute("contentaudiolist",contentaudiolist );
		model.addAttribute("contentlist", contentlist);
		model.addAttribute("playlist", new PlaylistLibrary());
		model.addAttribute("categories", categories);
		return "Addplaylist";
	}



	@PostMapping(value = "/playlistlibrary", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String createPlaylist(@RequestParam("playlist_type") String playlist_type,
			@RequestParam("title") String title, @RequestParam("permalink") String permalink,
			@RequestParam("category_id") Long category_id, @Nullable@RequestParam(required=false,value="description") String description,
			@Nullable@RequestParam(required=false,value="search_tags") String search_tags,
			@Nullable@RequestParam(required=false,value="thumbnail_assets") MultipartFile thumbnail_assets,
			@Nullable@RequestParam(required=false,value="banner_assets") MultipartFile banner_assets,
			@RequestParam(required = false, value = "bannerasset_id") Long bannerasset_id,
			@RequestParam(required = false, value = "thumbnailasset_id") Long thumbnailasset_id,
			@Nullable@RequestParam(required=false,value="content_Id") List<Long> content_Id,Model model, RedirectAttributes redirAttrs) throws Exception {

		String image = "image/jpeg";
		String image2 = "image/png";
		
	
		AssetLibrary thumbnail_asset = null;
		if(thumbnail_assets.isEmpty()&& thumbnailasset_id!=null) {
			thumbnail_asset=assetrepo.findById(thumbnailasset_id).get();
		}
		if(!thumbnail_assets.isEmpty()) {
			
		String thumbnail_img = playlistservice.storeTumbnailFile(thumbnail_assets);
		String thumbnail_img_Uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(AppConstants.DOWNLOAD_PATH)
				.path(thumbnail_img).toUriString();
		
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
		System.out.println("  Type     " + thumbnail_asset_type + "    Name:     " + thumbnail_asset_name + "   Size:   "
				+ thumbnail_asset_size);
		
		
		AssetType thumbnail_type_asset = null;
		if (image.equals(thumbnail_asset_type) || image2.equals(thumbnail_asset_type)) {
			thumbnail_type_asset = AssetType.Image;
		}

		thumbnail_asset = new AssetLibrary(thumbnail_type_asset, thumbnail_asset_name,
					thumbnail_asset_size, thumbnail_img_Uri);
			
		}
		

		
		AssetLibrary banner_asset=null;
		if(banner_assets.isEmpty()&& bannerasset_id!=null) {
			banner_asset=assetrepo.findById(bannerasset_id).get();
		}
		if(!banner_assets.isEmpty()) {
		
		String banner_img = playlistservice.storeBannerFile(banner_assets);
		String banner_img_Uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(AppConstants.DOWNLOAD_PATH)
				.path(banner_img).toUriString();
		
		String banner_asset_type = banner_assets.getContentType();
		String banner_asset_name = banner_assets.getOriginalFilename();
		long banner_size = banner_assets.getSize();
		String banner_asset_size;

		float banner_size_kb = banner_size / 1024;
		float banner_size_mb = banner_size_kb / 1024;

		if (banner_size_mb > 0) {
			banner_asset_size =df.format( banner_size_mb) + " MB";
		} else {
			banner_asset_size = df.format(banner_size_kb) + " KB";
		}
		AssetType banner_type_asset = null;
		if (image.equals(banner_asset_type) || image2.equals(banner_asset_type)) {
			banner_type_asset = AssetType.Image;
		}
			
        banner_asset = new AssetLibrary(banner_type_asset, banner_asset_name, banner_asset_size,
					banner_img_Uri);
		}

//Category
		
		try {
			Category cate =null;
			if(category_id != null) {
			Optional<Category> categoryId = categoryservice.getCategoryById(category_id);
			if (categoryId.isEmpty()) {
				throw new Exception("Category id is empty");
			}
			cate = new Category(category_id);
			}

		List<ContentLibrary> contentids = null;
		if (content_Id != null) {
			contentids = new ArrayList<>();
			for (int i = 0; i < content_Id.size(); i++) {
				ContentLibrary content_id = contentrepo.findById(content_Id.get(i)).get();
				
				contentids.add(content_id);
			}
		} else {
			contentids = null;
		}
			
				
				
		PlaylistLibrary playlist = new PlaylistLibrary();
		playlist.setPlaylist_type(playlist_type);
		playlist.setTitle(title);
		playlist.setPermalink(permalink);
		playlist.setCategories(cate);
		playlist.setDescription(description);
		playlist.setSearch_tags(search_tags);
		playlist.setContentLibrary(contentids);	
		playlist.setThumbnail_assets(thumbnail_asset);
		playlist.setBanner_assets(banner_asset);
		
	    playlistservice.createPlaylist(playlist);
		
		} catch (Exception ex) {
			System.out.println(ex);
		}

	          return "redirect:/playlistlibrary";
	          
	}

	@GetMapping("/updateplaylist/{id}")
	public ModelAndView ShowUpdatePage(Model model, @PathVariable long id) {
		ModelAndView editView = new ModelAndView("updateplaylist");
		PlaylistLibrary playlist = playlistrepo.findById(id).get();
		List<ContentLibrary> con = playlist.getContentLibrary();
		List<Category> categories = categoryrepo.findAll();
		List<ContentLibrary> contentlist = contentrepo.findAll();
		List<ContentLibrary> contentvideolist1=new ArrayList<>();
		List<ContentLibrary> contentaudiolist1=new ArrayList<>();
		for(int i=0;i<contentlist.size();i++) {
		if(contentlist.get(i).getContent_type().equals("video")) {
			 contentvideolist1.add(contentlist.get(i));	
		}
		else {		
			 contentaudiolist1.add(contentlist.get(i));	
			}
			
		}
		List<ContentLibrary> contentvideolist=new ArrayList<>(contentvideolist1);
		List<ContentLibrary> contentaudiolist=new ArrayList<>(contentaudiolist1);
		contentvideolist.removeAll(con);
		contentaudiolist.removeAll(con);
		
		
		List<AssetLibrary> asset = assetrepo.findAll();
		model.addAttribute("asset", asset);
		List<AssetLibrary> assetVideo = new ArrayList<>();
		List<AssetLibrary> assetAudio = new ArrayList<>();
		List<AssetLibrary> assetImage = new ArrayList<>();
		List<AssetLibrary> assetFile = new ArrayList<>();
		for (int i = 0; i < asset.size(); i++) {
			if (asset.get(i).getAsset_type().equals(AssetType.Video)) {
				assetVideo.add(asset.get(i));
			}

			else if (asset.get(i).getAsset_type().equals(AssetType.Audio)) {
				assetAudio.add(asset.get(i));
			}
			else if(asset.get(i).getAsset_type().equals(AssetType.Image)) {
				assetImage.add(asset.get(i));
			}
			else if(asset.get(i).getAsset_type().equals(AssetType.File)) {
				assetFile.add(asset.get(i));

			}
		}
		model.addAttribute("videoasset", assetVideo);
		model.addAttribute("audioasset", assetAudio);
		model.addAttribute("imageasset", assetImage);
		model.addAttribute("fileasset", assetFile);
		model.addAttribute("contentvideolist",contentvideolist );
		model.addAttribute("contentaudiolist",contentaudiolist );
		AssetLibrary thumbnail = playlist.getThumbnail_assets();
		AssetLibrary banner = playlist.getBanner_assets();
		model.addAttribute("contentlist", contentlist);
		model.addAttribute("con", con);
		model.addAttribute("categories", categories);
		model.addAttribute("thumbnail", thumbnail);
		model.addAttribute("banner", banner);
		editView.addObject("playlist", playlist);
		return editView;
	}

	@PostMapping("/updateplaylist1")
	public String updatePlaylistLibrary(@RequestParam("playlist_id") long playlist_id,
			@RequestParam("playlist_type") String playlist_type, @RequestParam("title") String title,
			@RequestParam("permalink") String permalink, @RequestParam("category_id") long category_id,
			@RequestParam(required=false,value="description") String description,
			@RequestParam(required=false,value="search_tags") String search_tags,
			@RequestParam(required=false,value="thumbnail_assets") MultipartFile thumbnail_assets,
			@RequestParam(required=false,value="banner_assets") MultipartFile banner_assets,
			@RequestParam(required = false, value = "bannerasset_id") Long bannerasset_id,
			@RequestParam(required = false, value = "thumbnailasset_id") Long thumbnailasset_id,
			@RequestParam(required=false,value="content_Id") List<Long> content_Id) throws Exception {
		String image = "image/jpeg";
		String image2 = "image/png";
		
		// thumbnail-------------------------------------------------------------------------------------------------------------
		AssetLibrary thumbnail_asset = null;
		if(thumbnail_assets.isEmpty()&& thumbnailasset_id!=null) {
			thumbnail_asset=assetrepo.findById(thumbnailasset_id).get();
		}
		if(!thumbnail_assets.isEmpty()) {
			
		String thumbnail_img = playlistservice.storeTumbnailFile(thumbnail_assets);
		String thumbnail_img_Uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(AppConstants.DOWNLOAD_PATH)
				.path(thumbnail_img).toUriString();
		
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
		System.out.println("  Type     " + thumbnail_asset_type + "    Name:     " + thumbnail_asset_name + "   Size:   "
				+ thumbnail_asset_size);
		
		
		AssetType thumbnail_type_asset = null;
		if (image.equals(thumbnail_asset_type) || image2.equals(thumbnail_asset_type)) {
			thumbnail_type_asset = AssetType.Image;
		}

		thumbnail_asset = new AssetLibrary(thumbnail_type_asset, thumbnail_asset_name,
					thumbnail_asset_size, thumbnail_img_Uri);
			
		}
		

		// banner-------------------------------------------------------------------------------------------------------------
		AssetLibrary banner_asset=null;
		if(banner_assets.isEmpty()&& bannerasset_id!=null) {
			
			banner_asset=assetrepo.findById(bannerasset_id).get();
		}
		if(!banner_assets.isEmpty()) {
		
		String banner_img = playlistservice.storeBannerFile(banner_assets);
		String banner_img_Uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(AppConstants.DOWNLOAD_PATH)
				.path(banner_img).toUriString();
		
		String banner_asset_type = banner_assets.getContentType();
		String banner_asset_name = banner_assets.getOriginalFilename();
		long banner_size = banner_assets.getSize();
		String banner_asset_size;

		float banner_size_kb = banner_size / 1024;
		float banner_size_mb = banner_size_kb / 1024;

		if (banner_size_mb > 0) {
			banner_asset_size =df.format( banner_size_mb) + " MB";
		} else {
			banner_asset_size = df.format(banner_size_kb) + " KB";
		}
		AssetType banner_type_asset = null;
		if (image.equals(banner_asset_type) || image2.equals(banner_asset_type)) {
			banner_type_asset = AssetType.Image;
		}
			
        banner_asset = new AssetLibrary(banner_type_asset, banner_asset_name, banner_asset_size,
					banner_img_Uri);
		}
		// Category id
		try {
			Optional<Category> categoryId = categoryservice.getCategoryById(category_id);
			if (categoryId.isEmpty())
				throw new Exception("Is empty categoryID");

			Category cat = categoryrepo.findById(category_id).get();

			List<ContentLibrary> contentids = null;
			if (content_Id != null) {
				contentids = new ArrayList<>();
				for (int i = 0; i < content_Id.size(); i++) {
					ContentLibrary content_id = contentrepo.findById(content_Id.get(i)).get();
					
					contentids.add(content_id);
				}
			} 

			PlaylistLibrary playlist = playlistrepo.findById(playlist_id).get();

			if (cat != null) {
				playlist.setCategories(cat);
			}
			if (contentids != null) {
				playlist.setContentLibrary(contentids);
			}
			if (playlist_type != null) {
				playlist.setPlaylist_type(playlist_type);
			}
			if (title != null) {
				playlist.setTitle(title);
			}
			if (permalink != null) {
				playlist.setPermalink(permalink);
			}
			if (description != null) {
				playlist.setDescription(description);
			}
			if (search_tags != null) {
				playlist.setSearch_tags(search_tags);
			}
			if (thumbnail_asset != null) {
				playlist.setThumbnail_assets(thumbnail_asset);
			}
			if (banner_asset != null) {
				playlist.setBanner_assets(banner_asset);
			}
			playlistservice.updatePlaylistLibrary(playlist);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return "redirect:/playlistlibrary";
	}

	@GetMapping("/deleteplist/{id}")
	public String deletePlaylist(@PathVariable long id) {
		PlaylistLibrary playlist=playlistrepo.findById(id).get();
	playlist.getContentLibrary();
		playlistservice.deletePlaylist(id);
		return "redirect:/playlistlibrary";
	}

}
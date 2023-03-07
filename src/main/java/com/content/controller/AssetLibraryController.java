package com.content.controller;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.content.model.AssetLibrary;
import com.content.enumclass.AssetType;
import com.content.service.AssetLibraryService;
import com.content.service.FileStorageService;
import com.content.util.AppConstants;
import com.content.dao.AssetLibraryRepo;


@Controller
public class AssetLibraryController {
	

	@Autowired
	AssetLibraryService assetservice;
	@Autowired
	FileStorageService   fileservice;
	@Autowired
	AssetLibraryRepo assetrepo;
	
	DecimalFormat df=new DecimalFormat();
	
	@GetMapping("/assetlibrary")
	public String assetLibPage(Model model) {
		List<AssetLibrary> assetlist = assetrepo.findAll();
		model.addAttribute("assetlist",assetlist);
		return "assetlibrary";
		
	}
	
	@GetMapping("/audio")
	public String assetLibAudioPage(Model model) {
		List<AssetLibrary> assetlist = assetrepo.findAll();
		model.addAttribute("assetlist",assetlist);
		return "audio";
	}
	@GetMapping("/images")
	public String assetLibImagesPage(Model model) {
		List<AssetLibrary> assetlist = assetrepo.findAll();
		model.addAttribute("assetlist",assetlist);
		return "images";
	}
	@GetMapping("/file")
	public String assetLibFilePage(Model model) {
		List<AssetLibrary> assetlist = assetrepo.findAll();
		model.addAttribute("assetlist",assetlist);
		return "file";
	}
	@GetMapping("/bulkUpload1")
	public String assetLibBulkUpload1Page() {
		return "bulkUpload1";
	}
	
	
	@PostMapping( value = "/assetlibrary1" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)//
	public String assetUpload(
			
			@RequestParam("assetLib") MultipartFile Asset,Model model, RedirectAttributes redirAttrs) throws Exception {
		String assetFile = fileservice.storeTumbnailFile(Asset);
		String asset_filepath = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path(AppConstants.DOWNLOAD_PATH).path(assetFile).toUriString();
		System.out.println(asset_filepath);
		
		
		AssetType asset_type= null;
		
		long size=Asset.getSize();
       String asset_size;
       
		float size_kb = size /1024; 
		float size_mb = size_kb / 1024;

	 if(size_mb > 0.99){
	        	asset_size = df.format(size_mb) + " MB";
	        }else{
	        	asset_size = df.format(size_kb) + " KB";
	        }
	 String asset_name=Asset.getOriginalFilename();
	 String value_type=Asset.getContentType();
	 System.out.println(" value type :      "+value_type);
		if(value_type.equals(AssetType.Video.getValue1())) {
			asset_type = AssetType.Video;
			System.out.println(asset_type);
			System.out.println("  Type:     "+asset_type+"    Name:     "+asset_name+"   Size:   "+asset_size);
			
			
			AssetLibrary asset=new AssetLibrary(asset_type, asset_name, asset_size, asset_filepath);
			
			
			asset.setAsset_type(asset_type);
			asset.setAsset_name(asset_name);
			asset.setAsset_size(asset_size);
			asset.setAsset_filepath(asset_filepath);
		
			assetservice.createAssets(asset);
			redirAttrs.addFlashAttribute("success", "Asset Uploaded Successfully..");
			return "redirect:/assetlibrary";
		}
		
		  
		
	 
		
		redirAttrs.addFlashAttribute("error", "Invalid File Format. Please Upload Correct Format.");
		return "redirect:/assetlibrary";
			
}
	
	@PostMapping( value = "/assetlibrary2" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)//
	public String assetAudioUpload(
			
			@RequestParam("assetLib") MultipartFile Asset,Model model, RedirectAttributes redirAttrs) throws Exception {
		String assetFile = fileservice.storeTumbnailFile(Asset);
		String asset_filepath = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path(AppConstants.DOWNLOAD_PATH).path(assetFile).toUriString();
		System.out.println(asset_filepath);
		String value_type=Asset.getContentType();
		System.out.println(" value type :      "+value_type);
		
		long size=Asset.getSize();
       String asset_size;
       
		float size_kb = size /1024; 
		float size_mb = size_kb / 1024;

	    if(size_mb > 0.99){
	      	asset_size = df.format(size_mb) + " MB";
        }else{
        	asset_size = df.format(size_kb) + " KB";
	    }
	    AssetType asset_type= null;
		String asset_name=Asset.getOriginalFilename();
	
	
		 if(value_type.equals(AssetType.Audio.getValue1())) {
			asset_type = AssetType.Audio;
			System.out.println(asset_type);
			System.out.println("  Type     "+asset_type+"    Name:     "+asset_name+"   Size:   "+asset_size);
			
			
			AssetLibrary asset=new AssetLibrary(asset_type, asset_name, asset_size, asset_filepath);
			
			
			asset.setAsset_type(asset_type);
			asset.setAsset_name(asset_name);
			asset.setAsset_size(asset_size);
			asset.setAsset_filepath(asset_filepath);
		
			assetservice.createAssets(asset);
			redirAttrs.addFlashAttribute("success", "Asset Uploaded Successfully..");
			return "redirect:/audio";
		}
	  
		
		
		 redirAttrs.addFlashAttribute("error", "Invalid File Format. Please Upload Correct Format.");
		
		return "redirect:/audio";
			
}
	
	@PostMapping( value = "/assetlibrary3" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)//
	public String assetImageUpload(
			
			@RequestParam("assetLib") MultipartFile Asset,Model model, RedirectAttributes redirAttrs) throws Exception {
		String assetFile = fileservice.storeTumbnailFile(Asset);
		String asset_filepath = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path(AppConstants.DOWNLOAD_PATH).path(assetFile).toUriString();
		System.out.println(asset_filepath);
		String value_type=Asset.getContentType();
		System.out.println(" value type :      "+value_type);
		
		
		long size=Asset.getSize();
       String asset_size;
       
		float size_kb = size /1024; 
		float size_mb = size_kb / 1024;

	 if(size_mb > 0.99){
		  	asset_size = df.format(size_mb) + " MB";
     }else{
     	asset_size = df.format(size_kb) + " KB";
	        }
	 String asset_name=Asset.getOriginalFilename();
		AssetType asset_type= null;
	
		if(value_type.equals(AssetType.Image.getValue1()) || value_type.equals(AssetType.Image.getValue2())) {
			asset_type = AssetType.Image;
			System.out.println(asset_type);
			System.out.println("Type     "+asset_type+"    Name:     "+asset_name+"   Size:   "+asset_size);
			
			
			AssetLibrary asset=new AssetLibrary(asset_type, asset_name, asset_size, asset_filepath);
			
			
			asset.setAsset_type(asset_type);
			asset.setAsset_name(asset_name);
			asset.setAsset_size(asset_size);
			asset.setAsset_filepath(asset_filepath);
		
			assetservice.createAssets(asset);
			redirAttrs.addFlashAttribute("success", "Asset Uploaded Successfully..");
			return "redirect:/images";
		}
		  
		
		
		
		
		redirAttrs.addFlashAttribute("error", "Invalid File Format. Please Upload Correct Format.");
		return "redirect:/images";
			
}
	
	@PostMapping( value = "/assetlibrary4" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)//
	public String assetFileUpload(
			
			@RequestParam("assetLib") MultipartFile Asset,Model model, RedirectAttributes redirAttrs) throws Exception {
		String assetFile = fileservice.storeTumbnailFile(Asset);
		String asset_filepath = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path(AppConstants.DOWNLOAD_PATH).path(assetFile).toUriString();
		System.out.println(asset_filepath);
		String value_type=Asset.getContentType();
		System.out.println(" value type :      "+value_type);
		
		
		long size=Asset.getSize();
       String asset_size;
       
		float size_kb = size /1024; 
		float size_mb = size_kb / 1024;

	 if(size_mb > 0.99){
		  	asset_size = df.format(size_mb) + " MB";
     }else{
     	asset_size = df.format(size_kb) + " KB";
	        }
	 
		AssetType asset_type= null;
		String asset_name=Asset.getOriginalFilename();
		
		
		if(value_type.equals(AssetType.File.getValue1())) {
			asset_type = AssetType.File;
			System.out.println(asset_type);

			System.out.println("  Type     "+asset_type+"    Name:     "+asset_name+"   Size:   "+asset_size);
			
			
			AssetLibrary asset=new AssetLibrary(asset_type, asset_name, asset_size, asset_filepath);
			
			
			asset.setAsset_type(asset_type);
			asset.setAsset_name(asset_name);
			asset.setAsset_size(asset_size);
			asset.setAsset_filepath(asset_filepath);
		
			assetservice.createAssets(asset);
			redirAttrs.addFlashAttribute("success", "Asset Uploaded Successfully..");
			return "redirect:/file";
		}

		
		redirAttrs.addFlashAttribute("error", "Invalid File Format. Please Upload Correct Format.");
		return "redirect:/file";
			
}

	@GetMapping("/assetlibraryDelete1")
	public String deleteAsset(@RequestParam(name="asset_id") long asset_id)
	{
		assetservice.deleteAssets(asset_id);
		
		return "redirect:/assetlibrary";
	}
	
	@GetMapping("/assetlibraryDelete2")
	public String deleteAudioAsset(@RequestParam(name="asset_id") long asset_id)
	{
		assetservice.deleteAssets(asset_id);
		
		return "redirect:/audio";
	}
	
	@GetMapping("/assetlibraryDelete3")
	public String deleteImageAsset(@RequestParam(name="asset_id") long asset_id)
	{
		assetservice.deleteAssets(asset_id);
		
		return "redirect:/images";
	}
	
	@GetMapping("/assetlibraryDelete4")
	public String deleteFileAsset(@RequestParam(name="asset_id") long asset_id)
	{
		assetservice.deleteAssets(asset_id);
		
		return "redirect:/file";
	}
}

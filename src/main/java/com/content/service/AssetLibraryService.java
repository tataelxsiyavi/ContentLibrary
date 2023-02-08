package com.content.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.content.dao.AssetLibraryRepo;
import com.content.model.AssetLibrary;


@Service
public class AssetLibraryService {
	@Autowired
	AssetLibraryRepo assetrepo;
	
	public List<AssetLibrary> getAllAssets(){
		return assetrepo.findAll();
		
	}
	public void createAssets(AssetLibrary asset) throws Exception {
		
			Optional<AssetLibrary> el = assetrepo.findById(asset.getAsset_id());
			if (el.isPresent()) {
				throw new Exception("ContentId is already present");
				
			}
			assetrepo.save(asset);
			
		}
	}
	



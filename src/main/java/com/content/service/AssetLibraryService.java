package com.content.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.content.dao.AssetLibraryRepo;

import com.content.model.AssetLibrary;




@Service
public class AssetLibraryService {
	@Autowired
	AssetLibraryRepo assetrepo;
	

	

	public void createAssets(AssetLibrary asset) throws Exception {
		
			
			assetrepo.save(asset);
			
		}
	
    public void deleteAssets(long asset_id) {
		
		assetrepo.deleteById(asset_id);
	}
    
    
    
   
}


	



package com.content.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import com.content.model.AssetLibrary;
//import com.content.service.AssetLibraryService;

public interface AssetLibraryRepo extends JpaRepository<AssetLibrary, Long>{

//	@Query("from AssetLibrary where asset_type =:asset_type")
//    public  List<AssetLibrary> findByAssetType( AssetType assetType);//@Param("assetType")


//	@Query("from AssetLibrary where asset_type ='Video'")
//	public List<AssetLibrary> findAllByVideo(AssetType asset_type);

//	public List<AssetLibrary> findAllByAudio(AssetType asset_type);
//	
//	public List<AssetLibrary> findAllByImage(AssetType asset_type);
//	
//	public List<AssetLibrary> findAllByFile(AssetType asset_type);

	

}

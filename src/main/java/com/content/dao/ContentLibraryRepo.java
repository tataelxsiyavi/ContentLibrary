package com.content.dao;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.content.model.ContentLibrary;
@Repository
public interface ContentLibraryRepo extends JpaRepository<ContentLibrary, Long>{
	@Query("SELECT e FROM ContentLibrary  e WHERE e.categories.category_id = :id")
	List<ContentLibrary> findByCategoryIdInContent(@Param("id") long cate_id); 
	
	
	@Query("SELECT e FROM ContentLibrary  e WHERE e.media_assets.asset_id = :id")
	List<ContentLibrary> findContentByMediaAssetId(@Param("id") long cate_id);
	
	@Query("SELECT e FROM ContentLibrary  e WHERE e.preview_assets.asset_id = :id")
	List<ContentLibrary> findContentByPreviewAssetId(@Param("id") long cate_id);
	
	@Query("SELECT e FROM ContentLibrary  e WHERE e.banner_assets.asset_id = :id")
	List<ContentLibrary> findContentByBannerAssetId(@Param("id") long cate_id);
	
	@Query("SELECT e FROM ContentLibrary  e WHERE e.additional_assets.asset_id = :id")
	List<ContentLibrary> findContentByFileAssetId(@Param("id") long cate_id);
	
	@Query("SELECT e FROM ContentLibrary  e WHERE e.thumbnail_assets.asset_id = :id")
	List<ContentLibrary> findContentByThumbnailAssetId(@Param("id") long cate_id);
}

package com.content.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.content.model.PlaylistLibrary;
@Repository
public interface PlaylistLibraryRepo extends JpaRepository<PlaylistLibrary, Long> {
	@Query("SELECT e FROM PlaylistLibrary  e WHERE e.categories.category_id = :id")
	List<PlaylistLibrary> findCategoryByIdInPlaylist(@Param("id") long cate_id);
}

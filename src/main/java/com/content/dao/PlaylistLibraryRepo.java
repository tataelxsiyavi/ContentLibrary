package com.content.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.content.model.PlaylistLibrary;

public interface PlaylistLibraryRepo extends JpaRepository<PlaylistLibrary, Long> {

}

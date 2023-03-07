package com.content.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.content.dao.PlaylistLibraryRepo;
import com.content.exception.MyFileNotFoundException;
import com.content.model.PlaylistLibrary;

@Service
public class PlaylistLibraryService {
	@Autowired
	PlaylistLibraryRepo playlistrepo;

	public List<PlaylistLibrary> getAllPlaylist() {
		return playlistrepo.findAll();
	}

	//Create Playlist
	public PlaylistLibrary createPlaylist(PlaylistLibrary playlistlibrary) throws Exception {
		Optional<PlaylistLibrary> pl = playlistrepo.findById(playlistlibrary.getPlaylist_id());
		if (pl.isPresent()) {
			throw new Exception("playlist is already present");
		}
		return playlistrepo.save(playlistlibrary);
	}

	//Category Id
	public Optional<PlaylistLibrary> findCategoryById(long id) {
		return playlistrepo.findById(id);
	}

	public void deletePlaylist(long id) {
		playlistrepo.deleteById(id);}
	
    //Delete
//	public boolean deleteplaylist(long id) {
//		PlaylistLibrary playlist=playlistrepo.findById(id).orElseThrow(()->{throw new MyFileNotFoundException("Id not present");});
//		playlistrepo.delete(playlist);
//		return true;
//	}

	
	
	public PlaylistLibrary updatePlaylistLibrary(PlaylistLibrary playlist) {
		PlaylistLibrary set = playlistrepo.findById(playlist.getPlaylist_id())
		 .orElseThrow(() -> new MyFileNotFoundException("playlist not found for this id" ));
		 set.setPlaylist_id(playlist.getPlaylist_id());
		 set.setPlaylist_type(playlist.getPlaylist_type());
		 set.setTitle(playlist.getTitle());
		 set.setPermalink(playlist.getPermalink());
		 set.setDescription(playlist.getDescription());
		 set.setSearch_tags(playlist.getSearch_tags());
		 set.setCategories(playlist.getCategories());
		 return playlistrepo.save(set);
	}

	public PlaylistLibrary findPlaylistById(long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
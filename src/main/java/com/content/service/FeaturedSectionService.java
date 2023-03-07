package com.content.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.content.dao.FeaturedSectionRepo;
import com.content.model.FeaturedSection;



@Service
public class FeaturedSectionService {
	
@Autowired
FeaturedSectionRepo sectionRepo;
	

public List<FeaturedSection> getAllsection(){
	return sectionRepo.findAll();
}


public FeaturedSection createSection(FeaturedSection featuredsection) throws Exception {
	
	
	return sectionRepo.save(featuredsection);
}



public void deleteSection(long id) {
	sectionRepo.deleteById(id);
}


public void updateFeaturedSection(FeaturedSection featuredSection) {
	FeaturedSection fee=sectionRepo.findById(featuredSection.getFeaturedSection_Id()).get();
	
	sectionRepo.save(featuredSection);
	
}





	
}

package com.content.enumclass;

public enum AssetType {
	
	Video("video/mp4"),
    Audio("audio/mpeg","audio/mp3"),
    Image("image/jpeg","image/png"), 
    File("application/pdf");
  
	String value1,value2;

	

	private AssetType(String value1, String value2) {
		this.value1 = value1;
		this.value2 = value2;
	}

	
	private AssetType(String value1) {
		this.value1 = value1;
	}

	public String getValue1() {
		return value1;
	}
	
	public String getValue2() {
		return value2;
	}

	
	


	
	


	

	
	}

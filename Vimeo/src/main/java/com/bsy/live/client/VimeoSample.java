package com.bsy.live.client;

import java.io.File;
import java.io.IOException;

public class VimeoSample {

	public static void main(String[] args) throws IOException, VimeoException {
		
	    Vimeo vimeo = new Vimeo("[token]"); 
	    
	    //add a video
	    boolean upgradeTo1080 = true;
	    String videoEndPoint = vimeo.addVideo(new File("/Users/tmendici/Downloads/Video.AVI"), upgradeTo1080);
	    
	    //get video info
	    VimeoResponse info = vimeo.getVideoInfo(videoEndPoint);
	    System.out.println(info);
	    
	    //edit video
	    String name = "Name";
	    String desc = "Description";
	    String license = ""; //see Vimeo API Documentation
	    String privacyView = "disable"; //see Vimeo API Documentation
	    String privacyEmbed = "whitelist"; //see Vimeo API Documentation
	    boolean reviewLink = false;
	    vimeo.updateVideoMetadata(videoEndPoint, name, desc, license, privacyView, privacyEmbed, reviewLink);
	    
	    //add video privacy domain
	    vimeo.addVideoPrivacyDomain(videoEndPoint, "clickntap.com");
	   
	    //delete video
	    vimeo.removeVideo(videoEndPoint);

	}

}

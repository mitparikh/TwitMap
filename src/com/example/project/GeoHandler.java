package com.example.project;

import java.util.Scanner; 
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.net.URL; 

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class GeoHandler {

	public String[] getLatLong(String givenLoc) throws IOException{
		givenLoc = givenLoc.replaceAll("[^A-Za-z0-9%,]", "");
		String mapUrl = "http://maps.googleapis.com/maps/api/geocode/json?address="+givenLoc+"&sensor=false";
//		System.out.println("Url: "+mapUrl);
		
		URL url = new URL(mapUrl); 
		Scanner scan = new Scanner(url.openStream()); 
		StringBuffer output = new StringBuffer();
		while (scan.hasNextLine()){
			output.append(scan.nextLine());
		}
		scan.close();	

		Pattern p = Pattern.compile("(location\"\\s:\\s\\{.*?\\})");
		Matcher m = p.matcher(output);

		String result = "";
		while (m.find()) {
			result = m.group(1);
		}
		String[] temp = result.split("location\" : ");
		if(temp.length>1){
			String[] loc = new String[2];
			JSONObject obj;
			try {
				obj = new JSONObject(temp[1]);
				loc[0] = obj.getString("lat");
				loc[1] = obj.getString("lng");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return loc; 
		}
		else return null;
	}
}

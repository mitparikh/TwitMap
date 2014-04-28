package com.example.project;

import java.util.ArrayList;
import java.util.logging.Level;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import twitter4j.TwitterException;

public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static ArrayList<String> latitudes = new ArrayList<String>(); 
	public static ArrayList<String> longitudes = new ArrayList<String>();
	public static ArrayList<String> loc = new ArrayList<String>();  
	public static String keyword;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		keyword = request.getParameter("keyword");
		String submit = request.getParameter("submit");

		if(submit.equals("ReIndex")){	
			reindexAction(keyword);
			response.sendRedirect("../TwitMap.jsp");
		}
		else if(submit.equals("Search")){
			searchAction(keyword);
			response.sendRedirect("../TwitMap.jsp");
		}
		else if(submit.equals("HeatMap")){
			searchAction(keyword);
			updateGeo();
			response.sendRedirect("../HeatMap.jsp");
		}
		
	}	
	
	private void searchAction(String keyword){		
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService(); 
		syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		
		@SuppressWarnings("unchecked")
		ArrayList<String> locations = (ArrayList<String>) syncCache.get(keyword); 
		if(locations==null){
			DataStoreHandler dsh = new DataStoreHandler();
			locations = dsh.fetchData(keyword);
			syncCache.put(keyword, locations);
			System.out.println("Getting from Store");
		}else
			System.out.println("Getting from Cache");
		
		loc = locations;
	}

	private void updateGeo(){
		GeoHandler g = new GeoHandler();	
		for(String location : loc){
			try {
				String[] latlong = g.getLatLong(location);
				if(latlong == null){
				}
				else{
					latitudes.add(latlong[0]);
					longitudes.add(latlong[1]);
				}				
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}
	}
	
	private void reindexAction(String keyword){
		TwitterHandler th = new TwitterHandler();		
		try {
			th.storeTwits(keyword);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}


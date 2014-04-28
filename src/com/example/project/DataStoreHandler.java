package com.example.project;

import java.util.ArrayList;

import twitter4j.Status;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class DataStoreHandler {
	final String kind = "Twits";
	final String name = "aashay";
	final String storeName = "TweetData1";
	
	public void reindexData(ArrayList<Status> tweets){
	    Key twitKey = KeyFactory.createKey(kind, name);
		
	    for (Status tweet : tweets){
			if(tweet.getUser().getLocation().length() > 0) {
            	System.out.println("Saving: "+tweet.getUser().getLocation());
				Entity tweetInfo = new Entity(storeName, twitKey);
				tweetInfo.setProperty("text", tweet.getText());
				tweetInfo.setProperty("date", tweet.getCreatedAt());
				tweetInfo.setProperty("location", tweet.getUser().getLocation());
				DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			    datastore.put(tweetInfo);
			}
		}		
	}
	
	public ArrayList<String> fetchData(String str){
		ArrayList<String> loc = new ArrayList<String>();		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(storeName);
		PreparedQuery pq = datastore.prepare(q);

		for (Entity result : pq.asIterable()) {
			String text = (String) result.getProperty("text");
			if(text.contains(str)){
				String location = (String) result.getProperty("location");
				loc.add(location);
//				System.out.println("Location: "+location);
			}
		}
		return loc;
	}
}

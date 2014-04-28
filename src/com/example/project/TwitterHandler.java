package com.example.project;

import java.util.ArrayList;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TwitterHandler {
	final String accessToken = "133994859-g8Y3NjNajLsMS3yLQLqfccc88vcvYfGLIIHgQbe3";
	final String accessSecret = "8UJLokxygVeRbFd0Vd5HJ0hMzDcQUHDmaee4Jn6BN8fYB";
	final String consumerKey = "UglYrFMD9lgGd9ADhyjYL0npe";
	final String consumerSecret = "vABNfafvL1gRGQwJqTKDjqBrgobr8vzs7f1WP7Znn9wXeoKetU";
	
	Twitter twitter;
	
	public TwitterHandler() {		
		TwitterFactory factory = new TwitterFactory();
		twitter = factory.getInstance();
		AccessToken accestoken = new AccessToken(accessToken, accessSecret);

		twitter.setOAuthConsumer(consumerKey, consumerSecret);
		twitter.setOAuthAccessToken(accestoken);
	}
	
	public void storeTwits(String keyword) throws TwitterException{
		DataStoreHandler dsh = new DataStoreHandler();
		Query query = new Query(keyword);
		QueryResult qr;
		
	    do {
			qr = twitter.search(query);
			ArrayList<Status> tweets= (ArrayList<Status>) qr.getTweets();
			dsh.reindexData(tweets);
		} while ((query = qr.nextQuery()) != null);	
	}
}

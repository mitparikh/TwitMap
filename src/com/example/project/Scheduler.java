package com.example.project;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.TwitterException;

public class Scheduler extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TwitterHandler th = new TwitterHandler();
		String keyword = "usa"; // Hard coded value as we can't fetch without a keyword
		
		try {
			th.storeTwits(keyword);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}

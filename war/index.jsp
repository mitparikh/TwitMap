<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
   <%@ page import="java.util.*"%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TwitMap</title>
</head>
<body>
	<div style="margin:0 20px">
		<h1>TwitMap</h1>
		<form action="/googleapp/MyServlet" method="post">
			<input placeholder="Enter Keyword" name="keyword">
			<input type="submit" value="Search" name="submit">
			<input type="submit" value="ReIndex" name="submit">
			<input type="submit" value="HeatMap" name="submit">
		</form>
	</div>
  	<br>
  
</body>
</html>
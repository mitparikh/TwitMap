<%@page import="com.example.project.MyServlet"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Map</title>
</head>
<body>
<h1>Mappp</h1>
<script>
  <% ArrayList<String> listLat,listLng = new ArrayList<String>();
	listLat = MyServlet.latitudes;
	listLng = MyServlet.longitudes;
	
	%>

    var lat = [], lng = [];
    <%for(int i=0;i<listLat.size();i++){%>
	    lat.push("<%= listLat.get(i)%>");
	    lng.push("<%= listLng.get(i)%>");  
	    
    <%}%>

    alert(lat[0]+" "+lng[0]);


</script>
</body>
</html>
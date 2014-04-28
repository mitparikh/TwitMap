<%@page import="com.example.project.MyServlet"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TwitMap</title>

    <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script> 

</head>
<body>
	<div style="margin:0 20px">
		<h1>TwitMap</h1>
		<form action="/googleapp/MyServlet" method="post">
			<input placeholder="Enter Keyword" name="keyword" value=<%= MyServlet.keyword %>>
			<input type="submit" value="Search" name="submit">
			<input type="submit" value="ReIndex" name="submit">
			<input type="submit" value="HeatMap" name="submit">
		</form>
	</div>
  	<br>
    
    <div id="map" style="width:1000px;height:500px;"></div>
    <div id="messages"></div>

    <script type="text/javascript">
    //<![CDATA[
    
    // delay between geocode requests - at the time of writing, 100 miliseconds seems to work well
    var delay = 100;


	// ====== Create map objects ======
	var infowindow = new google.maps.InfoWindow();
	var latlng = new google.maps.LatLng(0,0);
	var mapOptions = {
	  zoom: 2,
	  center: latlng,
	  mapTypeId: google.maps.MapTypeId.ROADMAP
	
	}
	var geo = new google.maps.Geocoder(); 
	var map = new google.maps.Map(document.getElementById("map"), mapOptions);
	var bounds = new google.maps.LatLngBounds();

      // ====== Geocoding ======
      function getAddress(search, next, arrLat, arrLong) {
        geo.geocode({address:search}, function (results,status)
          { 
            // If that was successful
            if (status == google.maps.GeocoderStatus.OK) {
              // Lets assume that the first marker is the one we want
              var p = results[0].geometry.location;
              var lat=p.lat();
              var lng=p.lng();
              // Output the data
                //var msg = 'address="' + search + '" lat=' +lat+ ' lng=' +lng+ '(delay='+delay+'ms)<br>';
                //document.getElementById("messages").innerHTML += msg;
              // Create a marker
              createMarker(search,lat,lng);
            }
            // ====== Decode the error status ======
            else {
              // === if we were sending the requests to fast, try this one again and increase the delay
              if (status == google.maps.GeocoderStatus.OVER_QUERY_LIMIT) {
                nextAddress--;
                delay++;
              } else {
                var reason="Code "+status;
                //var msg = 'address="' + search + '" error=' +reason+ '(delay='+delay+'ms)<br>';
                //document.getElementById("messages").innerHTML += msg;
              }   
            }
            next();
          }
        );
      }

     // ======= Function to create a marker
     function createMarker(add,lat,lng) {
       var contentString = add;
       var marker = new google.maps.Marker({
         position: new google.maps.LatLng(lat,lng),
         map: map,
         icon:'http://wiki.alumni.net/wiki/images/thumb/5/55/Wikimap-blue-dot.png/50px-Wikimap-blue-dot.png',
         zIndex: Math.round(latlng.lat()*-100000)<<5
       });

      google.maps.event.addListener(marker, 'click', function() {
         infowindow.setContent(contentString); 
         infowindow.open(map,marker);
       });

       bounds.extend(marker.position);

     }

      // ======= Global variable to remind us what to do next
      var nextAddress = 0;

      // ======= Function to call the next Geocode operation when the reply comes back

      function theNext() {
    	  
    	  <% ArrayList<String> list = new ArrayList<String>();
        	//  list = (ArrayList<String>) request.getAttribute("list");
     			list = MyServlet.loc;%>
     			 var jsArray = [], longLatArr = [];
     	          <%for(int i=0;i<list.size();i++){%>
     	              jsArray.push("<%= list.get(i)%>");
     	          <%}%>
        if (nextAddress < jsArray.length) {
          setTimeout('getAddress("'+jsArray[nextAddress]+'",theNext)', delay);
          nextAddress++;
        } else {
          // We're done. Show map bounds
          map.fitBounds(bounds);
        }
      }

      // ======= Call that function for the first time =======
      theNext();

    //]]>
    </script>
  
        

</body>
</html>
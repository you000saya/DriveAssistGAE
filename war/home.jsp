<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.appspot.drivenotification.controller.Datastore" %>

<% final String ATTRIBUTE_STATUS = "status"; %>
<html><body>
	<head>
		<title>GCM Demo</title>
		<link rel='icon' href='favicon.png'/>
	</head>
	
	<% 
	String status = (String) request.getAttribute(ATTRIBUTE_STATUS);
	if (status != null) {
	     out.print(status);
    } %>
    
	<h2>This is Yuka's page</h2>
	<%
	  int total = Datastore.getTotalDevices();
		if (total == 0) {
			out.print("<h2>No devices registered!</h2>");
		} else {
			 out.print("<h2>" + total + " device(s) registered!</h2>");
			 out.print("<form name='form' method='POST' action='sendAll'>");
// 			 out.print("latitude: <input type='text' name='latitude' >");
// 			 out.print("longitude: <input type='text' name='longitude' >");
			 out.print("<input type='text' name='message'>");
			 out.print("<input type='submit' value='Send Message' />");
			 out.print("</form>");
		}
		 response.setStatus(HttpServletResponse.SC_OK);
	%>

  </body>
</html>
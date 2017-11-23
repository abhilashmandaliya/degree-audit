<%
	if (session.getAttribute("user") == null || session.getAttribute("userCategory") == null) {
		response.sendRedirect("../index.html");
		return;
	}
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.1/Chart.bundle.js"></script>
<script type="text/javascript" src="../js/chart.js"></script>
<title>Chart</title>
</head>
<body>
<nav class="navbar navbar-default">
  <ul class="nav navbar-nav">
    <li><a href="getCourseWisePieChart.jsp">Course Wise Pie Chart</a></li>
    <li><a href="getCreditWisePieChart.jsp">Credit Wise Pie Chart</a></li>
    <li><a href="getSPIWiseProgressLineChart.jsp">SPI Wise Line Chart</a></li>
    <li><a href="semesterWiseCoursePrediction.jsp">Semester Course Prediction</a></li>
    <li><a href="semesterWiseCourseChoice.jsp">Semestser Wise Course Choice</a></li>
  </ul>
</nav>
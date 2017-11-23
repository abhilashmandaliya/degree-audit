
<%
	if (session.getAttribute("user") == null || session.getAttribute("userCategory") == null) {
		response.sendRedirect("../index.html");
		return;
	}
%>
<!DOCTYPE html>
<%@page import="controller.action.GetSemesterWiseCourseChoice"%>
<%@page import="crud.GradeCardCRUD"%>
<%@page import="crud.StudentCRUD"%>
<%@page import="util.Response"%>
<%@page import="java.util.List"%>
<%@page import="pojo.StudentPOJO"%>
<%@page import="pojo.UserPOJO"%>
<html lang="en">
<head>
<title>Semester Wise Course Choice</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
<script src="http://www.chartjs.org/dist/2.7.1/Chart.bundle.js"></script>
<script src="http://www.chartjs.org/samples/latest/utils.js"></script>
<style>
/* Make the image fully responsive */
.carousel-inner img {
	width: 100%;
	height: 100%;
}

canvas {
	-moz-user-select: none;
	-webkit-user-select: none;
	-ms-user-select: none;
}
</style>
</head>
<body>
	<nav class="navbar navbar-default">
		<ul class="nav navbar-nav">
			<li><a href="getCourseWisePieChart.jsp">Course Wise Pie
					Chart</a></li>
			<li><a href="getCreditWisePieChart.jsp">Credit Wise Pie
					Chart</a></li>
			<li><a href="getSPIWiseProgressLineChart.jsp">SPI Wise Line
					Chart</a></li>
			<li><a href="semesterWiseCoursePrediction.jsp">Semester
					Course Prediction</a></li>
			<li><a href="semesterWiseCourseChoice.jsp">Semestser Wise
					Course Choice</a></li>
		</ul>
	</nav>
	<div class="container">
		<div class="page-header">
			<h1>
				<center>Semester Wise Course Choice</center>
			</h1>
		</div>
		<hr />
		<script>
			$(document)
					.ready(
							function() {
								var data = $('#data').attr('value');
								if (data) {
									data = JSON.parse(data);
									data = data['data'];
									console.log(data);
									for (i = 1; i <= data.length; i++) {
										var barChartData = {
											labels : data[i - 1].data.labels,
											datasets : [
													{
														label : 'Core',
														backgroundColor : window.chartColors.blue,
														data : data[i - 1].data.data[0]
													},
													{
														label : 'Can\'t say',
														backgroundColor : window.chartColors.orange,
														data : data[i - 1].data.data[1]
													},
													{
														label : 'Not recomended',
														backgroundColor : window.chartColors.red,
														data : data[i - 1].data.data[2]
													},
													{
														label : 'Highly recomended',
														backgroundColor : window.chartColors.green,
														data : data[i - 1].data.data[3]
													} ]
										};
										var ctx = document.getElementById(
												"canvas" + i).getContext("2d");
										window.myBar = new Chart(
												ctx,
												{
													type : 'bar',
													data : barChartData,
													options : {
														title : {
															display : false,
															text : "Predicted course performance"
														},
														tooltips : {
															mode : 'index',
															intersect : false
														},
														responsive : true,
														scales : {
															xAxes : [ {
																stacked : true,
															} ],
															yAxes : [ {
																stacked : true
															} ]
														}
													}
												});
										for (j = 0; j < data[i - 1].data.fullName.length; j++) {
											$('#data_table' + i + " tr:last")
													.after(
															"<tr style='text-align:center;'><td>"
																	+ data[i - 1].data.labels[j]
																	+ "</td><td>"
																	+ data[i - 1].data.fullName[j]
																	+ "</td><td>"
																	+ data[i - 1].data.category[j]
																	+ "</td></tr>");
										}
										$('#course_table' + i + ' tr:last')
												.after(
														"<tr style='text-align:center;'><td>"
																+ data[i - 1].data.core
																+ "</td><td>"
																+ data[i - 1].data.tech
																+ "</td><td>"
																+ data[i - 1].data.open
																+ "</td></tr>");
									}
								} else {
									alert("Couldn't generate report.");
								}
							});
		</script>

		<%
			String action = "getsemesterwisecoursechoice";
			String mode = "api";
			String search = "all_semester_courses";
			int student_id = ((UserPOJO) session.getAttribute("user")).getId();
			request.setAttribute("student_id", student_id);
			int program_id = ((List<StudentPOJO>) ((Response) new StudentCRUD().retrive(request)).getData()).get(0)
					.getProgram_id().getId();
			short semester_id = 1;
			Object semester = ((Response) new GradeCardCRUD().retrive(request)).getData();
			if (semester != null) {
				semester_id = ((Short) semester);
				semester_id++;
			}
			request.setAttribute("action", action);
			request.setAttribute("mode", mode);
			request.setAttribute("search", search);
			request.setAttribute("program_id", program_id);
			request.setAttribute("semester_id", semester_id);
			String data = new GetSemesterWiseCourseChoice().perform(request, response);
			out.write("<input id='data' type='hidden' name='data' value='" + data + "'/>");
			Integer valid_combinations = (Integer) request.getAttribute("valid_combinations");
			for (int i = 1; i <= valid_combinations; i++) {
				out.write("<center><h3>Choice " + i + "/" + valid_combinations
						+ "</h3></center><div class='row'><div class='col-sm-7'><canvas id='canvas" + i
						+ "'></canvas><table id='course_table" + i
						+ "' class='table table-bordered'><thead><tr style='text-align:center;'><th>Core Courses</th><th>Technical Elective Courses</th><th>Open Elective Courses</th></tr></thead><tbody></tbody></table></div><div class='col-sm-5'><table id='data_table"
						+ i
						+ "' class='table table-bordered'><thead><tr style='text-align:center;'><th>Course Code</th><th>Course Name</th><th>Course Category</th></tr></thead><tbody></tbody></table></div></div><hr />");
			}
		%>
	</div>
</body>
</html>
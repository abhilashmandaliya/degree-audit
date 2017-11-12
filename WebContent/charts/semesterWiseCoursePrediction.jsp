<%@page import="controller.action.GetSemesterCoursesPrediction"%>
<%@page import="util.GeneralUtility"%>
<%@page import="crud.GradeCardCRUD"%>
<%@page import="java.util.List"%>
<%@page import="pojo.StudentPOJO"%>
<%@page import="util.Response"%>
<%@page import="crud.StudentCRUD"%>
<%@page import="pojo.UserPOJO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Bar Chart</title>
<script src="https://code.jquery.com/jquery-3.2.1.slim.js"></script>
<script src="http://www.chartjs.org/dist/2.7.1/Chart.bundle.js"></script>
<script src="http://www.chartjs.org/samples/latest/utils.js"></script>
<style>
canvas {
	-moz-user-select: none;
	-webkit-user-select: none;
	-ms-user-select: none;
}
</style>
</head>

<body>
	<div style="width: 75%">
		<canvas id="canvas"></canvas>
	</div>
	<script>
		$(document).ready(function() {
			var data = $('#data').attr('value');
			if (data) {
				console.log(data);
				data = JSON.parse(data);				
				data = data['data'];
				var barChartData = {
					labels : data.labels,
					datasets : [ {
						label : 'Core',
						backgroundColor : window.chartColors.blue,
						data : data.data[0]
					}, {
						label : 'Can\'t say',
						backgroundColor : window.chartColors.orange,
						data : data.data[1]
					}, {
						label : 'Not recomended',
						backgroundColor : window.chartColors.red,
						data : data.data[2]
					}, {
						label : 'Highly recomended',
						backgroundColor : window.chartColors.green,
						data : data.data[3]
					} ]
				};
				var ctx = document.getElementById("canvas").getContext("2d");
				window.myBar = new Chart(ctx, {
					type : 'bar',
					data : barChartData,
					options : {
						title : {
							display : true,
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
			} else {
				alert("Couldn't generate report.");
			}
		});
	</script>
	<%
		String action = "getsemestercoursesprediction";
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
		String data = new GetSemesterCoursesPrediction().perform(request, response);
		//data = "{\"data\":{\"data\":[[100,0],[0,0],[0,50],[0,0]],\"labels\":[\"SEN\", \"CC\"]}}";
		out.write("<input id='data' type='hidden' name='data' value='" + data + "'/>");
	%>
</body>

</html>
<!doctype html>
<%@page import="java.util.List"%>
<%@page import="util.Response"%>
<%@page import="crud.StudentCRUD"%>
<%@page import="pojo.StudentPOJO"%>
<%@page import="pojo.UserPOJO"%>
<%@page import="controller.action.GetSPIBaseProgressLineChart"%>
<html>

<head>
<title>Line Chart</title>
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
	<div style="width: 75%;">
		<canvas id="canvas"></canvas>
	</div>
	<script>
		$(document).ready(function() {
			var data = $("#data").attr('value');
			data = JSON.parse(data);
			data = data['data'];
			keys = data.keys;
			var config = {
				type : 'line',
				data : {
					labels : keys,
					datasets : [ {
						label : "Actual SPI",
						backgroundColor : window.chartColors.red,
						borderColor : window.chartColors.red,
						data : data.actual,
						fill : false,
					}, {
						label : "Desired SPI",
						fill : false,
						backgroundColor : window.chartColors.blue,
						borderColor : window.chartColors.blue,
						data : data.desired,
					} ]
				},
				options : {
					responsive : true,
					title : {
						display : true,
						text : 'SPI Wise Progress'
					},
					tooltips : {
						mode : 'index',
						intersect : false,
					},
					hover : {
						mode : 'nearest',
						intersect : true
					},
					scales : {
						xAxes : [ {
							display : true,
							scaleLabel : {
								display : true,
								labelString : 'Semester'
							}
						} ],
						yAxes : [ {
							display : true,
							scaleLabel : {
								display : true,
								labelString : 'SPI'
							}
						} ]
					}
				}
			};
			var ctx = document.getElementById("canvas").getContext("2d");
			window.myLine = new Chart(ctx, config);
		});
	</script>
	<%
		String action = "getspibaseprogresslinechart";
		String mode = "api";
		String search = "get_all_grades_of_student";
		int student_id = ((UserPOJO) session.getAttribute("user")).getId();
		request.setAttribute("student_id", student_id);
		int program_id = ((List<StudentPOJO>) ((Response) new StudentCRUD().retrive(request)).getData()).get(0)
				.getProgram_id().getId();
		request.setAttribute("action", action);
		request.setAttribute("mode", mode);
		request.setAttribute("search", search);
		request.setAttribute("program_id", program_id);
		String data = new GetSPIBaseProgressLineChart().perform(request, response);
		out.write("<input id='data' type='hidden' name='data' value='" + data + "'/>");
	%>
</body>

</html>
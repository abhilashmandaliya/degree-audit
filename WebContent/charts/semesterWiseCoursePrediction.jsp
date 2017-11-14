<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Bar Chart</title>
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
	<button id="randomizeData">Randomize Data</button>
	<script>
		$(document).ready(function () {
			$.ajax({
				url : '',
				type : 'post',
				data : {}
			});
		});
		var barChartData = {
			labels : [ "SEN", "CC", "HCI", "POM" ],
			datasets : [ {
				label : 'Core',
				backgroundColor : window.chartColors.blue,
				data : [ 100, 0, 0, 0 ]
			}, {
				label : 'Can\'t say',
				backgroundColor : window.chartColors.orange,
				data : [ 0, 50, 0, 0 ]
			}, {
				label : 'Not recomended',
				backgroundColor : window.chartColors.red,
				data : [ 0, 0, 25, 0 ]
			}, {
				label : 'Highly recomended',
				backgroundColor : window.chartColors.green,
				data : [ 0, 0, 0, 75 ]
			} ]
		};
		window.onload = function() {
			var ctx = document.getElementById("canvas").getContext("2d");
			window.myBar = new Chart(ctx, {
				type : 'bar',
				data : barChartData,
				options : {
					title : {
						display : true,
						text : "Chart.js Bar Chart - Stacked"
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
		};
	</script>
</body>

</html>
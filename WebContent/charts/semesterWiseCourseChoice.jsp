<!DOCTYPE html>
<html lang="en">
<head>
<title>Bootstrap Example</title>
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

	<div id="demo" class="carousel slide" data-ride="carousel">
		<ul class="carousel-indicators">
			<li data-target="#demo" data-slide-to="0" class="active"></li>
			<li data-target="#demo" data-slide-to="1"></li>
			<li data-target="#demo" data-slide-to="2"></li>
		</ul>
		<div class="carousel-inner">
			<div class="carousel-item active">
				<div style="width: 75%">
					<canvas id="canvas1"></canvas>
				</div>
			</div>
			<div class="carousel-item">
				<div style="width: 75%">
					<canvas id="canvas2"></canvas>
				</div>
			</div>
			<div class="carousel-item">
				<div style="width: 75%">
					<canvas id="canvas3"></canvas>
				</div>
			</div>
		</div>
		<a class="carousel-control-prev" href="#demo" data-slide="prev"> <span
			class="carousel-control-prev-icon"></span>
		</a> <a class="carousel-control-next" href="#demo" data-slide="next">
			<span class="carousel-control-next-icon"></span>
		</a>
	</div>
	<script>
		$(document).ready(function() {
			var data = $('#data').attr('value');
			if (data) {
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
				var ctx = document.getElementById("canvas1").getContext("2d");
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
				var ctx2 = document.getElementById("canvas2").getContext("2d");
				window.myBar = new Chart(ctx2, {
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
				var ctx3 = document.getElementById("canvas3").getContext("2d");
				window.myBar = new Chart(ctx3, {
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
	<input id='data' type='hidden' name='data'
		value='{"data":{"data":[[0,0],[50,50],[0,0],[0,0]],"labels":["Combinatorial Algorithms","Remote Sensing and GIS"]}}' />
</body>
</html>
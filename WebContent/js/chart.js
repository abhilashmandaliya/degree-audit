$(document).ready(function () {
	$("[name='chart']").each(function () {
		var data = {};
		$("input[type='hidden']").each( function () {
			data[$(this).attr('name')] = $(this).attr('value');
		});
		$.ajax({
			url : '../controller',
			type : 'post',
			contentType : 'application/x-www-form-urlencoded',
			data : data,
			success : function (data) {
				if(data != null) {
					data = JSON.parse(data);
					data = data['data'];
					var chartData = {
						labels : data['labels'],
						datasets : [ {
							data : data['data'],
							backgroundColor : data['backgroundColors']
						} ]
					};
					var ctx = document.getElementById("drawChart").getContext('2d');
					var drawChart = new Chart(ctx, {
						type : $('#chartType').attr('value'),
						data : chartData
					});
				}
			}
		})
	});
});
<%@include file="header.jsp"%>

<div class="container">
	<div class="page-header">
		<h1>
			<center>Credit Wise Pie Chart</center>
		</h1>
	</div>
	<div class="row">
		<div class="col-sm-4"></div>
		<div class="col-sm-2 alert alert-danger">
			<strong>Required : </strong><span id="_required"></span>
		</div>
		<div class="col-sm-2 alert alert-success">
			<strong>Earned : </strong><span id="_taken"></span>
		</div>
		<div class="col-sm-4"></div>
	</div>
	<div style="height: 50px">
		<canvas id="drawChart" height="50px" />
	</div>

</div>

<input type="hidden" name="chart" value="true" />
<input type="hidden" name="action" value="getcreditwisepiechart" />
<input type="hidden" id="chartType" value="pie" />
<%@include file="footer.jsp"%>
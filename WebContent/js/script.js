function getProgramTableData() {
  $.ajax({
    url:'http://localhost:8080/DegreeAudit/controller?action=getprogram',
    dataType: 'json',
    success: function(result) {
    	if(result.statusCode!=401){
	    	str="";
	    	for(i=0;i<result.data.length;i++){
	    		str+="<tr>";
	    		str+="<td>"+(i+1)+"</td>";
	    		str+="<td>"+result.data[i].name+"</td>";
	    		str+="<td>"+result.data[i].min_duration+"</td>";
	    		str+="<td>"+result.data[i].max_duration+"</td>";
	    		str+="<td>"+result.data[i].min_credits+"</td>";
	    		str+="<td>"+result.data[i].max_credits+"</td>";
	    		str+="<td>"+parseInt(result.data[i].min_grade_points)+"</td>";
	    		str+="<td>"+result.data[i].max_grade_points+"</td>";
	    		str+="<td>"+result.data[i].min_foundation_course+"</td>";
	    		str+="<td>"+result.data[i].max_foundation_course+"</td>";
	    		str+="<td>"+result.data[i].min_course+"</td>";
	    		str+="<td>"+result.data[i].max_course+"</td>";
	    		str+="<td>"+result.data[i].min_cpi+"</td>";
	    		str+="</tr>";
	    	}
	    	$(".programTableData").html(str);
    	}
    	else{
    		$(location).attr("href","index.html");
    	}
    }
  });
}

function addProgramme() {
	url = "http://localhost:8080/DegreeAudit/controller?action=addprogram&name="+$("#courseName").val()+"&min_duration="+$("#minDuration").val()+"&max_duration="+$("#maxDuration").val()+"&min_credits="+$("#minCredits").val()+"&max_credits="+$("#maxCredits").val()+"&min_grade_points="+$("#minGradePts").val()+"&min_cpi="+$("#minCPI").val()+"&min_foundation_course="+$("#foundationCount").val()+"&min_courses="+$("#minCourse").val()+"&max_courses="+$("#maxCourse").val()+"&mode=api";
	$.ajax({
        url: url,
        dataType: 'json',
        success: function(result) {
        	if(result.statusCode==200){
        		$(location).attr("href","programme_list.html");
        	}
        	else{
        		alert("Error: "+result.message);
        	}
        }
	});
}

function login(){
	$.ajax({
        url: "http://localhost:8080/DegreeAudit/controller?action=login&username="+$("#username").val()+"&password="+$("#password").val(),
        dataType: 'json',
        success: function(result){
        	localStorage.setItem("loginUser", JSON.stringify(result.data));
        	if(result.statusCode==200){
        		if(result.data.userCategory.category=="admin"){
        			$(location).attr("href","programme_list.html");
        		}
        		else if(result.data.userCategory.category=="coordinator"){
        			$(location).attr("href","list_of_programme_courses.html");
            	}
        		else if(result.data.userCategory.category=="student"){
        			$(location).attr("href","student_profile.html");
            	}
        	}
      	}
   });
}

function getCourseTableData() {
	  $.ajax({
	    url:'http://localhost:8080/DegreeAudit/controller?action=getcourses',
	    dataType: 'json',
	    success: function(result) {
	    	if(result.statusCode!=401){
		    	str="";
		    	for(i=0;i<result.data.length;i++){
		    		str+="<tr>";
		    		str+="<td>"+(i+1)+"</td>";
		    		str+="<td>"+result.data[i].course_id+"</td>";
		    		str+="<td>"+result.data[i].course_name+"</td>";
		    		str+="<td>"+result.data[i].earn_grade+"</td>";
		    		str+="</tr>";
		    	}
		    	$(".courseTableData").html(str);
	    	}
	    	else{
	    		$(location).attr("href","index.html");
	    	}
	    }
	  });
	}
function getCourseCategoryData() {
	$.ajax({
		url:'http://localhost:8080/DegreeAudit/controller?action=getCourseCategory',
	    dataType: 'json',
	    success: function(result) {
	    	if(result.statusCode!=401){
	    		console.log(result);
	    	}
	    	else{
	    		$(location).attr("href","index.html");
	    	}
	    }
	});
}

function getAuditOf(audit_id) {
	$.ajax({
		url:'http://localhost:8080/DegreeAudit/controller?action=getauditdata2&id='+audit_id,
	    dataType: 'json',
	    success: function(result) {
	    	if(result.statusCode!=401){
	    		var client = new XMLHttpRequest();
	    		client.open('GET', 'audit_report.txt');
	    		client.onreadystatechange = function() {
	    		  if(this.readyState == 4 && this.status == 200){
	    			var res = client.responseText;
	    			res = res.replace("{date}",result.data[0].date_generated);
	    			res = res.replace("{grade_obtain}",result.data[0].obtained_credit);
	    			res = res.replace("{degree_completed}",result.data[0].percentage_of_degree_finish);
	    			res = res.replace("{present_cpi}",result.data[0].present_CPI);
	    			res = res.replace("{course_completed}",result.data[0].present_cource);
	    			res = res.replace("{cpi_required}",result.data[0].require_CPI);
	    			res = res.replace("{credits_required}",result.data[0].require__credit);
	    			res = res.replace("{no_courses_required}",result.data[0].require_courcce);
	    			res = res.replace("{time_left_to_graduate}",result.data[0].time_left_finish_degree);
	    			$(".pageContent").html(res);
	    		  }
	    		}
	    		client.send();
	    	}
	    }
	});
}

function showAllAuditDates() {
	$.ajax({
		url:'http://localhost:8080/DegreeAudit/controller?action=getauditdata&id=2',
	    dataType: 'json',
	    success: function(result) {
	    	var str="";
	    	var i;
	    	str+='<table class="table table-hover" style="margin-top:3%">';
	    	str+='<thead>';
	    	str+='<tr>';
	    	str+='<th style="text-align:center">Sr.</th>';
	    	str+='<th style="text-align:center">Audit Date</th>';
	    	str+='</tr>';
	    	str+='</thead>';
	    	str+='<tbody>';
	    	var j=1;
	    	for(i = result.data.length-1;i>=0;i--){
	    		str+='<tr>';
	    		str+='<td>'+j+'</td>';
	    		str+='<td>';
	    		str+='<a onclick="getAuditOf('+result.data[i][1]+')">'+result.data[i][0]+'</a><br>';
	    		str+='</td>';
	    		str+='</tr>';
	    		j++;
	    	}
	    	str+='</tbody></table>';
	    	console.log(str);
	    	$(".pageContent").html(str);
	    }
	});
}

function searchCourseNameID() {
	  // Declare variables 
	  var input, filter, table, tr, id, i,name;
	  input = document.getElementById("myInput");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("myTable");
	  tr = table.getElementsByTagName("tr");

	  // Loop through all table rows, and hide those who don't match the search query
	  for (i = 0; i < tr.length; i++) {
	    id = tr[i].getElementsByTagName("td")[0];
	    name = tr[i].getElementsByTagName("td")[1];
	    if (id || name) {
	      if (id.innerHTML.toUpperCase().indexOf(filter) > -1) {
	        tr[i].style.display = "";
	      }
	      else if(name.innerHTML.toUpperCase().indexOf(filter) > -1){
	        tr[i].style.display = "";
	      }
	      else {
	        tr[i].style.display = "none";
	      }
	    }
	  }
	}

function edit_details() {
	
	var str="";
	var userData = JSON.parse(localStorage.getItem("loginUser"));
	str+='<div id="editprofile" class="col-md-8">';
	str+='<table>';
	str+='<tr style="margin-top:2%">';
	str+='<td>First Name: </td>';
	str+='<td width="25%"></td>';
	str+='<td><input type="text" class="form-control" id="fname" value="'+userData.first_name+'"></td>';
	str+='</tr>';
	str+='<tr style="margin-top:2%">';
	str+='<td>Last Name: </td>';
	str+='<td width="25%"></td>';
	str+='<td><input type="text" class="form-control" id="lname" value="'+userData.last_name+'"></td>';
	str+='</tr>';
	str+='<tr style="margin-top:2%">';
	str+='<td>Email Address: </td>';
	str+='<td width="25%"></td>';
	str+='<td><input type="text" class="form-control" id="email" value="'+userData.email+'"></td>';
	str+='</tr>';
	str+='</table>';

	
    str+='</div>';
    $(".pageContent").html(str);
}

function getGradeCard(sem) {
	var client = new XMLHttpRequest();
	client.open('GET', 'gradeCard.txt');
	client.onreadystatechange = function() {
	  if(this.readyState == 4 && this.status == 200){
		var res = client.responseText;
		var userData = JSON.parse(localStorage.getItem("loginUser"));
		$.ajax({
			url: 'http://localhost:8080/DegreeAudit/controller?action=getstudentdetails&student_id='+userData.id,
		    dataType: 'json',
		    success: function(result) {
		    	if(result.statusCode!=401){
		    		var temp = result.data.semester;
		    		var course_grades="";
		    		for(var sem_no in temp){
		    			res = res.replace("{sem}",sem_no);
		    			res = res.replace("{sem}",sem_no);
		    			for(var course_opt in temp[sem_no]){
			    			course_grades+="<tr>";
			    			course_grades+="<td>"+temp[sem_no][course_opt].course_id+"</td>";
			    			course_grades+="<td>"+temp[sem_no][course_opt].course_name+"</td>";
			    			course_grades+="<td>"+temp[sem_no][course_opt].earn_grade+"</td>";
			    			course_grades+="<tr>";
		    			}
		    		}
		    		res = res.replace("{courses_data}",course_grades);
		    		res = res.replace("{id}",userData.id);
		    		res = res.replace("{name}",userData.first_name+" "+userData.last_name);
		    	}
		    	$(".pageContent").html(res);
		    }
		});
	  }
	}
	client.send();
}

/* Program Course */

$("tr").on("click", "#remove", function(){
    $(this).text('Add');
    $(this).addClass('btn-primary').removeClass('btn-danger').attr('id','add-btn');
    var tr = $(this).parent().parent();
    $('#masterList').append(tr);
});

$("tr").on("click", "#add-btn", function(){
    $(this).text('Remove');
    $(this).removeClass('btn-primary').addClass('btn-danger').attr('id','remove');
    var tr = $(this).parent().parent();
    $('#semesterTable').append(tr);
});
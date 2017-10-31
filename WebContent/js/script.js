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
        	console.log(result);
        	if(result.statusCode==200){
        		if(result.data.userCategory.category=="admin"){
        			$(location).attr("href","programme_list.html");
        		}
        		else if(result.data.userCategory.category=="coordinator"){
        			$(location).attr("href","programme_courses.html");
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
		    		str+="<td>"+result.data[i].course_credits+"</td>";
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

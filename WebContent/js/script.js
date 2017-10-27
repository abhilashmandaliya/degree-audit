function getProgramTableData() {
  $.ajax({
    url:'http://localhost:8080/DegreeAudit/controller?action=login&username=abhilash&password=abc123',
    success: function (result) {
      $.ajax({
        url:'http://localhost:8080/DegreeAudit/controller?action=getprogram',
        dataType: 'json',
        success: function(result) {
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
      });
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

//$("#courses").click(function(e){
//  e.preventDefault();
//  window.location.href='course_list.html';
//});
//
//
//function getPage(a){
//  if(a=='prgstr'){
//    $.ajax({
//      url:'programme_str.html',
//      success:function(data){
//        $("#dataarea").html(data);
//      }
//    });
//  }else if (a=='programme') {
//    $.ajax({
//      url:'programme.html',
//      success:function(data){
//        $("#dataarea").html(data);
//      }
//    });
//  }
//}

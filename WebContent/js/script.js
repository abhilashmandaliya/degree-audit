function getProgramTableData() {
	$.ajax({
		url : 'http://localhost:8080/DegreeAudit/controller?action=getprogram',
		dataType : 'json',
		success : function(result) {
			if (result.statusCode != 401) {
				str = "";
				for (i = 0; i < result.data.length; i++) {
					str += "<tr>";
					str += "<td>" + (i + 1) + "</td>";
					str += "<td>" + result.data[i].name + "</td>";
					str += "<td>" + result.data[i].min_duration + "</td>";
					str += "<td>" + result.data[i].max_duration + "</td>";
					str += "<td>" + result.data[i].min_credits + "</td>";
					str += "<td>" + result.data[i].max_credits + "</td>";
					str += "<td>" + parseInt(result.data[i].min_grade_points)
							+ "</td>";
					str += "<td>" + result.data[i].max_grade_points + "</td>";
					str += "<td>" + result.data[i].min_foundation_course
							+ "</td>";
					str += "<td>" + result.data[i].max_foundation_course
							+ "</td>";
					str += "<td>" + result.data[i].min_course + "</td>";
					str += "<td>" + result.data[i].max_course + "</td>";
					str += "<td>" + result.data[i].min_cpi + "</td>";
					str += "</tr>";
				}
				$(".programTableData").html(str);
			} else {
				$(location).attr("href", "index.html");
			}
		}
	});
}

function addProgramme() {
	url = "http://localhost:8080/DegreeAudit/controller?action=addprogram&name="
			+ $("#courseName").val()
			+ "&min_duration="
			+ $("#minDuration").val()
			+ "&max_duration="
			+ $("#maxDuration").val()
			+ "&min_credits="
			+ $("#minCredits").val()
			+ "&max_credits="
			+ $("#maxCredits").val()
			+ "&min_grade_points="
			+ $("#minGradePts").val()
			+ "&min_cpi="
			+ $("#minCPI").val()
			+ "&min_foundation_course="
			+ $("#foundationCount").val()
			+ "&min_courses="
			+ $("#minCourse").val()
			+ "&max_courses="
			+ $("#maxCourse").val()
			+ "&mode=api";
	$.ajax({
		url : url,
		dataType : 'json',
		success : function(result) {
			if (result.statusCode == 200) {
				$(location).attr("href", "programme_list.html");
			} else {
				alert("Error: " + result.message);
			}
		}
	});
}

function login() {
	$.ajax({
		url : "http://localhost:8080/DegreeAudit/controller?action=login&username="
				+ $("#username").val()
				+ "&password="
				+ $("#password").val(),
		dataType : 'json',
		success : function(result) {
			localStorage.setItem("loginUser", JSON
					.stringify(result.data));
			if (result.statusCode == 200) {
				if (result.data.userCategory.category == "admin") {
					$(location).attr("href", "programme_list.html");
				} else if (result.data.userCategory.category == "coordinator") {
					$(location).attr("href", "pc_profile.html");
				} else if (result.data.userCategory.category == "student") {
					$(location).attr("href", "student_profile.html");
				}
			}
		}
	});
}

function getProgramByCordinator(pc_id) {
	$.ajax({
		url : "http://localhost:8080/DegreeAudit/controller?action=getprogrambypc&search=by_pc&pc_id="
				+ pc_id,
		success : function(result) {
			if (result.statusCode != 401) {
				var res = JSON.parse(result);
	
				localStorage.setItem("program", result);
				var str = "";
				for (var i = 1; i <= res.data[0].program.min_duration * 2; i++) {
					str += '<a class="dropdown-item" style="cursor: pointer;" onclick="navigateToSemCourses('
							+ i + ')">Sem-' + i + '</a>';
				}
	
				$("#sem_nos").html(str);
			}
			
			else {
				$(location).attr("href", "index.html");
			}
		},
		error : function(error_res) {
			console.log(error_res);
		}
	});
}

function navigateToSemCourses(sem_name) {
	localStorage.setItem("sem_name", sem_name);
	$(location).attr("href", "programme_courses.html");

}

function getSemCourse(sem_name) {
	$.ajax({
		url : "http://localhost:8080/DegreeAudit/controller?action=getsemesteridfromname&search=semester_id_from_name&semester_name="
				+ sem_name,
		success : function(result) {
			var res = JSON.parse(result);
			var programData = JSON.parse(localStorage
					.getItem("program")).data[0].program;
			$.ajax({
				url : "http://localhost:8080/DegreeAudit/controller?action=getsemestercourses&search=all_semester_courses&semester_id="
						+ res.data[0].id
						+ "&program_id="
						+ programData.id,
				success : function(result) {
					var res = JSON.parse(result);
					var str = "";
					for (var i = 0; i < res.data.length; i++) {
						str += '<tr id="default">';
						str += '<td hidden>'
							+ res.data[i].course.id
							+ '</td>';
						str += '<td>'
								+ res.data[i].course.course_id
								+ '</td>';
						str += '<td>'
								+ res.data[i].course.course_name
								+ '</td>';
						str += '<td>'
								+ res.data[i].course.course_credits
								+ '</td>';
						str += '<td><button class=" btn btn-danger remove" type="button">Remove</button></td>';
						str += '</tr>';
					}
					$(".sem_courses").html(str);
					$.ajax({
						url : "http://localhost:8080/DegreeAudit/controller?action=getcourses",
						success : function(result) {
							var res = JSON
									.parse(result);
							var str = "";
							console.log(res);
							for (var i = 0; i < res.data.length; i++) {
								str += '<tr id="add">';
								str += '<td hidden>'
									+ res.data[i].id
									+ '</td>';
								str += '<td>'
										+ res.data[i].course_id
										+ '</td>';
								str += '<td>'
										+ res.data[i].course_name
										+ '</td>';
								str += '<td>'
										+ res.data[i].course_credits
										+ '</td>';
								str += '<td><button class=" btn btn-primary add" type="button">Add</button></td>';
								str += '</tr>';
							}
							$(".master_courses").html(str);
							$(".heading_with_sem").html("Sem - "+sem_name+ " Courses");
						}
					});

				}
			});
		}
	});
}

function logout() {
	$.ajax({
		url:"http://localhost:8080/DegreeAudit/controller?action=logout",
		success: function() {
			localStorage.setItem("loginUser",null);
			$(location).attr("href", "index.html");
		}
	})
}
function updateSemCourses(sem_name) {
	$.ajax({
		url : "http://localhost:8080/DegreeAudit/controller?action=getsemesteridfromname&search=semester_id_from_name&semester_name="
				+ sem_name,
		success : function(result) {
			var res = JSON.parse(result);
			var programData = JSON.parse(localStorage
					.getItem("program")).data[0].program;
			var course_ids="";
			for(var i = 0;i<$(".sem_courses")[0].childNodes.length-1; i++) {
				course_ids+=($(".sem_courses")[0].childNodes[i].childNodes[0].textContent)+",";
			}
			course_ids+=($(".sem_courses")[0].childNodes[$(".sem_courses")[0].childNodes.length-1].childNodes[0].textContent);
			$.ajax({
				url : "http://localhost:8080/DegreeAudit/controller?action=UpdateSemesterCourses&sem_id="
						+ res.data[0].id
						+ "&program_id="
						+ programData.id
						+ "&courses="+course_ids,
				success : function(result) {
					alert("Sem Course Updated!");
				}
			});
		}
	});
}

function getCourseTableData() {
	$.ajax({
		url : 'http://localhost:8080/DegreeAudit/controller?action=getcourses',
		dataType : 'json',
		success : function(result) {
			if (result.statusCode != 401) {
				str = "";
				for (i = 0; i < result.data.length; i++) {
					str += "<tr>";
					str += "<td>" + (i + 1) + "</td>";
					str += "<td>" + result.data[i].course_id + "</td>";
					str += "<td>" + result.data[i].course_name + "</td>";
					str += "<td>" + result.data[i].earn_grade + "</td>";
					str += "</tr>";
				}
				$(".courseTableData").html(str);
			} else {
				$(location).attr("href", "index.html");
			}
		}
	});
}
function getCourseCategoryData() {
	$.ajax({
		url : 'http://localhost:8080/DegreeAudit/controller?action=getCourseCategory',
		dataType : 'json',
		success : function(result) {
			if (result.statusCode != 401) {
				console.log(result);
			} else {
				$(location).attr("href", "index.html");
			}
		}
	});
}

function getAuditOf(audit_id) {
	var userData = JSON.parse(localStorage.getItem("loginUser"));
	$.ajax({
		url : 'http://localhost:8080/DegreeAudit/controller?action=getauditbystudentid&student_id='
				+ userData.id+"&audit_id="+audit_id+"&search=by_audit_id",
		dataType : 'json',
		success : function(result) {
			if (result.statusCode != 401) {
				console.log(result);
				var client = new XMLHttpRequest();
				client.open('GET', 'audit_report.txt');
				client.onreadystatechange = function() {
					if (this.readyState == 4 && this.status == 200) {
						var res = client.responseText;
						res = res.replace("{date}",
								result.data[0].date_generated);
						res = res.replace("{grade_obtain}",
								result.data[0].obtained_credits);
						res = res
								.replace(
										"{degree_completed}",
										result.data[0].degree_completed_percent);
						res = res.replace("{present_cpi}",
								result.data[0].current_cpi.toFixed(2));
						res = res.replace("{course_completed}",
								result.data[0].present_courses);
						res = res.replace("{cpi_required}",
								result.data[0].required_cpi);
						res = res.replace("{credits_required}",
								result.data[0].required_credits);
						res = res.replace("{no_courses_required}",
								result.data[0].required_courses);
						res = res.replace("{time_left_to_graduate}",
								result.data[0].time_left);
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
			url:'http://localhost:8080/DegreeAudit/controller?action=getauditbystudentid&student_id=81',
			dataType : 'json',
			success : function(result) {
				var str = "";
				var i;
				str+='<h2>Audit Report List</h2>';
				str += '<table class="table table-hover" style="margin-top:3%">';
				str += '<thead>';
				str += '<tr>';
				str += '<th style="text-align:center">Sr.</th>';
				str += '<th style="text-align:center">Audit Date</th>';
				str += '</tr>';
				str += '</thead>';
				str += '<tbody>';
				for (i = 0; i <= result.data.length - 1; i++) {
					str += '<tr>';
					str += '<td>' + (i+1) + '</td>';
					str += '<td>';
					str += '<a onclick="getAuditOf(' + result.data[i].id
							+ ')">Sem - '+result.data[i].sem+" "+result.data[i].date_generated+'</a><br>';
					str += '</td>';
					str += '</tr>';
				}
				str += '</tbody></table>';
				$(".pageContent").html(str);
			}
		});
}

function searchCourseNameID() {
	// Declare variables
	var input, filter, table, tr, id, i, name;
	input = document.getElementById("myInput");
	filter = input.value.toUpperCase();
	table = document.getElementById("myTable");
	tr = table.getElementsByTagName("tr");

	// Loop through all table rows, and hide those who don't match the search
	// query
	for (i = 0; i < tr.length; i++) {
		id = tr[i].getElementsByTagName("td")[0];
		name = tr[i].getElementsByTagName("td")[1];
		if (id || name) {
			if (id.innerHTML.toUpperCase().indexOf(filter) > -1) {
				tr[i].style.display = "";
			} else if (name.innerHTML.toUpperCase().indexOf(filter) > -1) {
				tr[i].style.display = "";
			} else {
				tr[i].style.display = "none";
			}
		}
	}
}

function edit_details() {

	var str = "";
	var userData = JSON.parse(localStorage.getItem("loginUser"));
	str += '<div id="editprofile" class="col-md-8">';
	str += '<table class="table">';
	str += '<thead>';
	str += '<tr>';
	str += '<th scope="col"><h2>Edit Profile</h2></th>';
	str += '<th></th>';
	str += '<th></th>';
	str += '</tr>';
	str += '</thead>';
	str += '<tbody>';
	str += '<tr style="margin-top:2%">';
	str += '<td>First Name: </td>';
	str += '<td width="25%"></td>';
	str += '<td><input type="text" class="form-control" id="fname" value="'
			+ userData.first_name + '"></td>';
	str += '</tr>';
	str += '<tr style="margin-top:2%">';
	str += '<td>Last Name: </td>';
	str += '<td width="25%"></td>';
	str += '<td><input type="text" class="form-control" id="lname" value="'
			+ userData.last_name + '"></td>';
	str += '</tr>';
	str += '<tr style="margin-top:2%">';
	str += '<td>Email Address: </td>';
	str += '<td width="25%"></td>';
	str += '<td><input type="text" class="form-control" id="email" value="'
			+ userData.email + '"></td>';
	str += '</tr>';
	str += '<tr>';
	str += '<td colspan="3"><button class="btn btn-primary" onclick="update_profile()">Update</button></td>';
	str += '</tr>';
	str += '</tbody>';
	str += '</table>';

	str += '</div>';
	$(".pageContent").html(str);
}

function update_profile(){
	var userData = JSON.parse(localStorage.getItem("loginUser"));
	console.log('http://localhost:8080/DegreeAudit/controller?action=updatestudentdetails&user_id='
			+ userData.id+"&first_name="+$("#fname").val()+"&last_name="+$("#lname").val()+"&email="+$("#email").val());
	$.ajax({
		url: 'http://localhost:8080/DegreeAudit/controller?action=updatestudentdetails&user_id='
			+ userData.id+"&first_name="+$("#fname").val()+"&last_name="+$("#lname").val()+"&email="+$("#email").val(),
		dataType: 'json',
		success: function(result) {
			if (result.statusCode != 401) {
				alert("Updated Successfully!");
				var userData = JSON.parse(localStorage.getItem("loginUser"));
				userData.first_name=$("#fname").val();
				userData.last_name=$("#lname").val();
				userData.email=$("#email").val();
				localStorage.setItem("loginUser", JSON.stringify(userData));
				location.reload();
			}
			else{
				alert("Failed :(");
			}
		}
	});
}
function getGradeCard(sem) {
	var client = new XMLHttpRequest();
	client.open('GET', 'gradeCard.txt');
	client.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var res = client.responseText;
			var userData = JSON.parse(localStorage.getItem("loginUser"));
			$.ajax({
				url : 'http://localhost:8080/DegreeAudit/controller?action=getstudentdetails&student_id='
						+ userData.id,
				dataType : 'json',
				success : function(result) {
					if (result.statusCode != 401) {
						console.log(result);
						var temp = result.data.semester[sem].course;
						var course_grades = "";
						res = res.replace("{sem}", sem);
						res = res.replace("{sem}", sem);
						for (var cnt=0;cnt<temp.length;cnt++) {
							course_grades += "<tr>";
							course_grades += "<td>"
									+ temp[cnt].course_id
									+ "</td>";
							course_grades += "<td>"
									+ temp[cnt].course_name
									+ "</td>";
							course_grades += "<td>"
									+ temp[cnt].earn_grade
									+ "</td>";
							course_grades += "<tr>";
						}
						res = res.replace("{}")
						res = res.replace("{courses_data}",
								course_grades);
						res = res.replace("{earned_credits}",
								result.data.semester[sem].sem_course_credit);
						res = res.replace("{earned_credits}",
								result.data.semester[sem].sem_course_credit);
						res = res.replace("{grade_points}",
								result.data.semester[sem].sem_grade_points);
						res = res.replace("{total_credits_earned}",
								result.data.total_course_credit);
						res = res.replace("{total_credits_earned}",
								result.data.total_course_credit);
						res = res.replace("{total_grade_points}",
								result.data.toal_grade_points);
						res = res.replace("{cpi}",
								result.data.cpi.toFixed(2));
						res = res.replace("{spi}",
								result.data.semester[sem].spi.toFixed(2));
						res = res.replace("{id}", userData.id);
						res = res.replace("{name}", userData.first_name
								+ " " + userData.last_name);
						$(".pageContent").html(res);
					}
				}
			});
		}
	}
	client.send();
}

function generateAudit() {
	var userData = JSON.parse(localStorage.getItem("loginUser"));
	$.ajax({
		url: 'http://localhost:8080/DegreeAudit/controller?action=generateauditbystudentid&student_id='
			+ userData.id,
		dataType: 'json',
		success: function(result) {
			if (result.statusCode != 401) {
				getAuditOf(result.data);
				$.ajax({
					url:'http://localhost:8080/DegreeAudit/controller?action=getauditbystudentid&student_id='+userData.id,
				    dataType: 'json',
				    success: function(result) {
				    	if (result.statusCode != 401) {
					    	var str="";
					    	//console.log(result);
					    	for(var i=0; i<=2 ;i++){
								str += '<button type="button" class="btn btn-info" onclick="getAuditOf('+result.data[i].id+')" style="width:100%">Sem - '+result.data[i].sem+" "+result.data[i].date_generated+'</button>';
					    	}
					    	if(result.data.length>3){
					    		str += '<button type="button" class="btn btn-info" onclick="showAllAuditDates()" style="width:100%">More..</button>';
					    	}
					    	$("#audits").html(str);
				    	}
				    	else{
				    		$(location).attr("href", "index.html");
				    	}
				    }
				});
			}
			else{
				console.log(result);
			}
		}
	});
}

function getStudentDetails(id) {
	$.ajax({
				url : 'http://localhost:8080/DegreeAudit/controller?action=studentdetail&student_id='
						+ id,
				dataType : 'json',
				success : function(result) {
					console.log(result);
					if (result.statusCode != 401) {
						var str = "";
						str += "<tr>";
						str += '<td>' + id + '</td>';
						str += '<td>'
								+ result.data.student_details.user_id.first_name
								+ " "
								+ result.data.student_details.user_id.last_name
								+ '</td>';
						str += '<td>3</td>';
						str += '<td>'
								+ result.data.student_details.year_of_enrolment
								+ '</td>';
						str += '<td></td>';
						str += '</tr>';
						$(".stud_details_table").html(str);
					}
				}
			});
}

function getCourseAndGroup(){
	$.ajax({
		url: 'http://localhost:8080/DegreeAudit/controller?action=getcoursecoursegroupandmapping&search=getcoursecoursegroupandmapping',
		success: function (result) {
			var res = JSON.parse(result);
			console.log(res);
			var str = "";
			for(var i = 0;i<Object.keys(res.data.courseGroupCourse.data).length;i++){
				str+="<tr>";
				str+="<td>"+res.data.courseGroupCourse.data[i].course.course_id+"</td>";
				str+="<td>"+res.data.courseGroupCourse.data[i].course.course_name+"</td>";
				var select_txt="<select>";
				for(var j = 0;j<Object.keys(res.data.courseGroups.data).length;j++){
					select_txt+="<option value='"+res.data.courseGroups.data[j].id+"'";
					if(res.data.courseGroupCourse.data[i].course_group.id==res.data.courseGroups.data[j].id)
						select_txt+=" selected";
					select_txt+=">"+res.data.courseGroups.data[j].group_name+"</option>";
				}
				select_txt+="</select>";
				str+="<td id='course"+i+"'>"+select_txt+"</td>";
				str+="</tr>";
			}
			$(".table_data").html(str);
			
		}
	});
}


$(document).ready(()=>{
	$(document).on("click", '.remove', function(event) { 
		$(this).text('Add');
		console.log($(this));
		$(this).addClass('btn-primary add').removeClass('remove').removeClass('btn-danger').attr('id','add-btn');
		var tr = $(this).parent().parent();
		$('#masterList').append(tr);
	});
	$(document).on("click", '.add', function(event) { 
		$(this).text('Remove');
		$(this).removeClass('btn-primary').removeClass('add').addClass('btn-danger remove').attr(
				'id', 'remove');
		var tr = $(this).parent().parent();
		$('#semesterTable').append(tr);
	});
});
package controller.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import crud.CourseCRUD;
import crud.CourseGroupCRUD;
import crud.CourseGroupCourseCRUD;
import crud.SemesterCourseCRUD;
import util.GeneralUtility;
import util.Response;

public class GetCourseCourseGroupAndMapping implements Action {

	@Override
	public String perform(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		Response allCourseGroups = (Response) new CourseGroupCRUD().retrive(request);
		Response allCourseGroupCourse = (Response) new CourseGroupCourseCRUD().retrive(request);
		request.setAttribute("search", "all_program_courses");
		Response allCourses = (Response) new SemesterCourseCRUD().retrive(request);
		Map<String, Response> _response = new HashMap<>();
		_response.put("courses", allCourses);
		_response.put("courseGroups", allCourseGroups);
		_response.put("courseGroupCourse", allCourseGroupCourse);

		Response finalResponse = GeneralUtility.generateSuccessResponse(null, _response);
		return finalResponse.toString();
	}

}

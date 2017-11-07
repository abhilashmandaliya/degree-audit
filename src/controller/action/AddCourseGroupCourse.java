package controller.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import crud.CourseGroupCourseCRUD;

public class AddCourseGroupCourse implements Action {

	@Override
	public String perform(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return new CourseGroupCourseCRUD().create(request).toString();
	}

}

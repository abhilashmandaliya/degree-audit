package controller.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import crud.SemesterCourseCRUD;
import pojo.SemesterCoursePOJO;

public class UpdateSemesterCourses implements Action {

	@Override
	public String perform(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return new SemesterCourseCRUD().update(request).toString();
	}
	
}

package controller.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import crud.SemesterCourseCRUD;
import util.Response;

public class GetSemesterCourses implements Action {

	@Override
	public String perform(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return ((Response) (new SemesterCourseCRUD().retrive(request))).toString();
	}

}

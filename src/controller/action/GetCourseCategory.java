package controller.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import crud.CourseCategoryCRUD;

public class GetCourseCategory implements Action {

	@Override
	public String perform(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return new CourseCategoryCRUD().retrive(request).toString();
	}
	
}

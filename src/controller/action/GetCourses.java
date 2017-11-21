package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import crud.CourseCRUD;
import util.GeneralUtility;

public class GetCourses implements Action {

	@Override
	public String perform(HttpServletRequest request, HttpServletResponse response) {
		if (!GeneralUtility.isAutheticatedUser(request)) {
			return GeneralUtility.generateUnauthorizedResponse().toString();
		}
		return new CourseCRUD().retrive(request).toString();
	}
	
}

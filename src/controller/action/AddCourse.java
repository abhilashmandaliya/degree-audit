package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import crud.CourseCRUD;

public class AddCourse implements Action {

	@Override
	public String perform(HttpServletRequest request, HttpServletResponse response) {
		Integer id = new CourseCRUD().create(request);
		return String.valueOf(id);
	}
	
}

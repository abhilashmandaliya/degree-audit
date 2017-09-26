package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import crud.UserCategoryCRUD;

public class AddUserCategory implements Action {

	@Override
	public String perform(HttpServletRequest request, HttpServletResponse response) {
		Integer id = new UserCategoryCRUD().create(request);
		return String.valueOf(id);
	}

}

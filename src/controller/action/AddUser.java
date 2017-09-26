package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import crud.UserCRUD;

public class AddUser implements Action {

	@Override
	public String perform(HttpServletRequest request, HttpServletResponse response) {
		Integer id = new UserCRUD().create(request);
		return String.valueOf(id);
	}

}

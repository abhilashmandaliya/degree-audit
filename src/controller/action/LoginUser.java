package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import crud.UserCRUD;

public class LoginUser implements Action {

	@Override
	public String perform(HttpServletRequest request, HttpServletResponse response) {
		return (String) new UserCRUD().retrive(request);
	}

}
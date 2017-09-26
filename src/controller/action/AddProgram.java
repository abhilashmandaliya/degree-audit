package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import crud.ProgramCRUD;

public class AddProgram implements Action {

	@Override
	public String perform(HttpServletRequest request, HttpServletResponse response) {
		Integer id = new ProgramCRUD().create(request);
		return String.valueOf(id);
	}

}

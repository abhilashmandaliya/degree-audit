package controller.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import crud.ProgramCRUD;
import util.GeneralUtility;

public class GetProgram implements Action {

	@Override
	public String perform(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (!GeneralUtility.isAutheticatedUser(request)) {
			return GeneralUtility.generateUnauthorizedResponse().toString();
		}
		return (String) new ProgramCRUD().retrive(request);
	}

}

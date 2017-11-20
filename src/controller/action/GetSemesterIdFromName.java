package controller.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import crud.SemesterCRUD;
import util.Response;

public class GetSemesterIdFromName implements Action {

	@Override
	public String perform(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return ((Response) new SemesterCRUD().retrive(request)).toString();
	}

}

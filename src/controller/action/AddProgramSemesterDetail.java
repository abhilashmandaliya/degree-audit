package controller.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import crud.ProgramSemesterDetailCRUD;

public class AddProgramSemesterDetail implements Action {

	@Override
	public String perform(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return new ProgramSemesterDetailCRUD().create(request).toString();
	}

}

package controller.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import util.StudentGradeCard;

public class GetStudentDetails implements Action {

	@Override
	public String perform(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return new StudentGradeCard().studentDetails(request).toString();
	}
}

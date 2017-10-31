package controller.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import crud.GradeCardUtility;
import util.StudentGradeCard;

public class Check implements Action {

	@Override
	public String perform(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return new GradeCardUtility().getStudentGradeCardBySemester(request).toString();
		// return "";
	}

}

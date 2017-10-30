package controller.action;

import controller.Action;
import crud.GradeCardUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class getStudentGradeCard implements Action {

	@Override
	public String perform(HttpServletRequest request, HttpServletResponse response) {

		// String data = (String) new
		// GradeCardUtility().getStudentGradeCardBySemester(request);
		// System.err.println("data " + data); return data;

		return new GradeCardUtility().getStudentGradeCardBySemester(request).toString();
	}

}

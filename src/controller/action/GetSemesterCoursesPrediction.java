package controller.action;

import java.io.IOException;
import java.util.List;

import javax.print.DocFlavor.READER;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import crud.SemesterCourseCRUD;
import pojo.SemesterCoursePOJO;
import util.GeneralUtility;
import util.Response;

public class GetSemesterCoursesPrediction implements Action {

	@Override
	public String perform(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		List<SemesterCoursePOJO> semesterCourses = (List<SemesterCoursePOJO>) ((Response) new SemesterCourseCRUD()
				.retrive(request)).getData();
		String[] labels = new String[semesterCourses.size()];
		int[][] data = new int[4][semesterCourses.size()];
		int index = 0;
		int eligibility = -10;
		final int CORE = 100;
		final int HIGHLY_RECOMMENDED = 75;
		final int CAN_NOT_SAY = 50;
		final int NOT_RECOMMENDED = 25;

		for (SemesterCoursePOJO semesterCourse : semesterCourses) {
			labels[index] = semesterCourse.getCourse().getCourse_name();
			if (semesterCourse.getCourse().getCourse_category().getCourse_cat_name().toLowerCase().startsWith("core")) {
				eligibility = CORE;
			} else {
				request.setAttribute("course_id", semesterCourse.getCourse().getId());
				eligibility = (int) GeneralUtility.getCourseEligibility(request);
				if (eligibility == -1) {
					eligibility = NOT_RECOMMENDED;
				} else if (eligibility == 0) {
					eligibility = CAN_NOT_SAY;
				} else if (eligibility == 1) {
					eligibility = HIGHLY_RECOMMENDED;
				}
			}
			data[0][index] = (eligibility == CORE) ? eligibility : 0;
			data[1][index] = (eligibility == CAN_NOT_SAY) ? eligibility : 0;
			data[2][index] = (eligibility == NOT_RECOMMENDED) ? eligibility : 0;
			data[3][index] = (eligibility == HIGHLY_RECOMMENDED) ? eligibility : 0;
			index++;
		}
		Object[] _response = { labels, data };
		return new Response(GeneralUtility.getRedirect(request), _response).toString();
	}

}

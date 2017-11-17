package controller.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor.READER;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import crud.CourseProgramCRUD;
import crud.SemesterCourseCRUD;
import pojo.CourseCategoryPOJO;
import pojo.CourseProgramPOJO;
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
			String search = "program_course_wise_course_category";
			request.setAttribute("search", search);
			request.setAttribute("course_id", semesterCourse.getCourse().getId());
			String course_category = ((CourseCategoryPOJO) ((List<CourseProgramPOJO>) ((Response) new CourseProgramCRUD()
					.retrive(request)).getData()).get(0).getCourse_category()).getCourse_cat_name();
			if (course_category.toLowerCase().startsWith("core")) {
				eligibility = CORE;
			} else {
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
		Map<String, Object> _response = new HashMap<>();
		_response.put("labels", labels);
		_response.put("data", data);
		return new Response(GeneralUtility.getRedirect(request), _response).toString();
	}

}

package controller.action;

import java.io.IOException;
import java.util.List;

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
		for (SemesterCoursePOJO semesterCourse : semesterCourses) {
			request.setAttribute("course_id", semesterCourse.getCourse().getId());
			System.out.println(GeneralUtility.getCourseEligibility(request));
		}
		return null;
	}

}

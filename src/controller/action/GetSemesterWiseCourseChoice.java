package controller.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import crud.CourseCategoryCRUD;
import crud.SemesterCourseCRUD;
import pojo.CourseCategoryPOJO;
import pojo.CoursePOJO;
import pojo.SemesterCoursePOJO;
import util.GeneralUtility;
import util.Response;

public class GetSemesterWiseCourseChoice implements Action {

	@Override
	public String perform(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<CourseCategoryPOJO> course_cats = (List<CourseCategoryPOJO>) ((Response) new CourseCategoryCRUD()
				.retrive(request)).getData();
		CourseCategoryPOJO core = null;
		for (CourseCategoryPOJO category : course_cats) {
			if (category.getCourse_cat_name().toLowerCase().startsWith("core")) {
				core = category;
				break;
			}
		}
		List<SemesterCoursePOJO> semesterCourses = (List<SemesterCoursePOJO>) ((Response) new SemesterCourseCRUD()
				.retrive(request)).getData();
		ArrayList<CoursePOJO> nonCoreCourses = new ArrayList<>();
		ArrayList<CoursePOJO> coreCourses = new ArrayList<>();
		for (SemesterCoursePOJO semesterCourse : semesterCourses) {
			if (semesterCourse.getCourse().getCourse_category().getCourse_cat_name()
					.equals(core.getCourse_cat_name())) {
				coreCourses.add(semesterCourse.getCourse());
			} else {
				nonCoreCourses.add(semesterCourse.getCourse());
			}
		}
		//request.setAttribute("search", "program_id_semester_id_wise");
		String _response = (String) GeneralUtility.getSemesterCourseCombinations(request, nonCoreCourses, coreCourses);
		return _response;
	}

}

package controller.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import crud.CourseCategoryCRUD;
import crud.CourseProgramCRUD;
import crud.SemesterCourseCRUD;
import pojo.CourseCategoryPOJO;
import pojo.CoursePOJO;
import pojo.CourseProgramPOJO;
import pojo.SemesterCoursePOJO;
import util.GeneralUtility;
import util.Response;

public class GetSemesterWiseCourseChoice implements Action {

	@Override
	public String perform(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
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
			Object originalSearch = request.getAttribute("search");
			ArrayList<CoursePOJO> nonCoreCourses = new ArrayList<>();
			ArrayList<CoursePOJO> coreCourses = new ArrayList<>();
			for (SemesterCoursePOJO semesterCourse : semesterCourses) {
				String search = "program_course_wise_course_category";
				request.setAttribute("search", search);
				request.setAttribute("course_id", semesterCourse.getCourse().getId());
				String course_category = ((CourseCategoryPOJO) ((List<CourseProgramPOJO>) ((Response) new CourseProgramCRUD()
						.retrive(request)).getData()).get(0).getCourse_category()).getCourse_cat_name();
				if (course_category.equals(core.getCourse_cat_name())) {
					coreCourses.add(semesterCourse.getCourse());
				} else {
					nonCoreCourses.add(semesterCourse.getCourse());
				}
			}
			request.setAttribute("search", originalSearch);
			System.out.println("core " + coreCourses.size() + " noncore " + nonCoreCourses.size());
			String _response = (String) GeneralUtility.getSemesterCourseCombinations(request, nonCoreCourses,
					coreCourses);
			return _response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}

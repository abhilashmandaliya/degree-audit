package crud;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import pojo.CoursePOJO;
import pojo.ProgramPOJO;
import pojo.SemesterCoursePOJO;
import pojo.SemesterPOJO;
import util.GeneralUtility;
import util.Response;

public class SemesterCourseCRUD extends CRUDCore {

	@Override
	public Response create(HttpServletRequest request) throws IOException {
		Integer id = null;

		try {
			Integer semester_id = Integer.parseInt(request.getParameter("semester_id"));
			Integer course_id = Integer.parseInt(request.getParameter("course_id"));
			Integer program_id = Integer.parseInt(request.getParameter("program_id"));
			SemesterPOJO semester = session.get(SemesterPOJO.class, semester_id);
			CoursePOJO course = session.get(CoursePOJO.class, course_id);
			ProgramPOJO program = session.get(ProgramPOJO.class, program_id);
			SemesterCoursePOJO semesterCourse = new SemesterCoursePOJO(course, semester, program);
			id = (Integer) session.save(semesterCourse);
			tx.commit();
			response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), id);
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return response;
	}

	@Override
	public Object retrive(HttpServletRequest request) throws IOException {
		String search = request.getParameter("search").toLowerCase();
		if (search.equals("all_semester_courses")) {
			try {
				Integer semester_id = Integer.parseInt(request.getParameter("semester_id"));
				Integer program_id = Integer.parseInt(request.getParameter("program_id"));
				List<SemesterCoursePOJO> semesterCourses = session.createQuery(
						"FROM SemesterCoursePOJO WHERE semester = " + semester_id + " AND program_id = " + program_id)
						.list();
				response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), semesterCourses);
			} catch (HibernateException e) {
				tx.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}
		}
		return response;
	}

	@Override
	public Response update(HttpServletRequest request) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response delete(HttpServletRequest request) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}

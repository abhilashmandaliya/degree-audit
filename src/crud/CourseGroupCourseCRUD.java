package crud;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import pojo.CourseCategoryPOJO;
import pojo.CourseGroupCoursePOJO;
import pojo.CourseGroupPOJO;
import pojo.CoursePOJO;
import util.GeneralUtility;
import util.Response;

public class CourseGroupCourseCRUD extends CRUDCore {

	@Override
	public Response create(HttpServletRequest request) throws IOException {
		Integer id = null;

		try {
			Integer course_id = Integer.parseInt(request.getParameter("course_id"));
			Integer course_group_id = Integer.parseInt(request.getParameter("course_group_id"));
			CoursePOJO course = session.get(CoursePOJO.class, course_id);
			CourseGroupPOJO courseGroup = session.get(CourseGroupPOJO.class, course_group_id);
			CourseGroupCoursePOJO courseGroupCourse = new CourseGroupCoursePOJO(course, courseGroup);
			id = (Integer) session.save(courseGroupCourse);
			System.out.println(id);
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
		// TODO Auto-generated method stub
		return null;
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

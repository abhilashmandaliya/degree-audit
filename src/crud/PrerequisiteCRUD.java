package crud;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import pojo.CoursePOJO;
import pojo.GradeCard;
import pojo.PrerequisitePOJO;
import pojo.StudentPOJO;
import util.GeneralUtility;
import util.Response;

public class PrerequisiteCRUD extends CRUDCore {

	@Override
	public Response create(HttpServletRequest request) throws IOException {
		// Transaction tx = null;
		Integer id = null;
		try {
			int for_course_id = Integer.parseInt(request.getParameter("for_course_id"));
			int required_course_id = Integer.parseInt(request.getParameter("required_course_id"));
			CoursePOJO for_course = session.get(CoursePOJO.class, for_course_id);
			CoursePOJO required_course = session.get(CoursePOJO.class, required_course_id);
			PrerequisitePOJO prerequisite = new PrerequisitePOJO(for_course, required_course);
			id = (Integer) session.save(prerequisite);
			tx.commit();
			response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), id);
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session.isOpen()) {
				session.close();
			}
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

package crud;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import pojo.CourseCategoryPOJO;
import pojo.CourseGroupPOJO;
import pojo.CoursePOJO;
import util.GeneralUtility;
import util.Response;

public class CourseGroupCRUD extends CRUDCore {

	@Override
	public Response create(HttpServletRequest request) throws IOException {
		Integer id = null;

		try {
			String group_name = request.getParameter("group_name");
			float min_avg = Float.parseFloat(request.getParameter("min_avg"));
			float max_avg = Float.parseFloat(request.getParameter("max_avg"));
			CourseGroupPOJO courseGroup = new CourseGroupPOJO(group_name, min_avg, max_avg);
			id = (Integer) session.save(courseGroup);
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
		try {
			List<CourseGroupPOJO> courseGroups = session.createQuery("FROM CourseGroupPOJO").list();
			response = GeneralUtility.generateSuccessResponse(null, courseGroups);
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
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

package crud;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import pojo.CourseCategoryPOJO;
import pojo.ProgramPOJO;
import util.GeneralUtility;
import util.Response;

public class CourseCategoryCRUD extends CRUDCore {

	@Override
	public Response create(HttpServletRequest request) throws IOException {
		Integer id = null;

		try {
			String course_cat_name = request.getParameter("course_cat_name");
			String course_cat_code = request.getParameter("course_cat_code");
			CourseCategoryPOJO courseCat = new CourseCategoryPOJO(course_cat_name, course_cat_code);
			id = (Integer) session.save(courseCat);
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
		Response response = null;
		try {
			List<CourseCategoryPOJO> course_cats = session.createQuery("FROM CourseCategoryPOJO").list();
			response = GeneralUtility.generateSuccessResponse(null, course_cats);
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

		return null;
	}

	@Override
	public Response delete(HttpServletRequest request) throws IOException {

		return null;
	}

}

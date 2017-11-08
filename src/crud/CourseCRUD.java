package crud;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pojo.CourseCategoryPOJO;
import pojo.CoursePOJO;
import pojo.ProgramPOJO;
import pojo.SlotPOJO;
import util.GeneralUtility;
import util.HibernateSessionFactory;
import util.Response;

public class CourseCRUD extends CRUDCore {

	@Override
	public Response create(HttpServletRequest request) throws IOException {

		Integer id = null;

		try {
			// tx = session.beginTransaction();
			String course_name = request.getParameter("course_name");
			String course_id = request.getParameter("course_id");
			String slot_id = request.getParameter("slot_id");
			Integer course_credits = Integer.parseInt(request.getParameter("course_credits"));
			CourseCategoryPOJO course_cat_pojo = session.get(CourseCategoryPOJO.class,
					Integer.parseInt(request.getParameter("course_category")));
			SlotPOJO slot = session.get(SlotPOJO.class, Integer.parseInt(slot_id));
			CoursePOJO course_obj = new CoursePOJO(course_name, course_id, course_credits, course_cat_pojo, slot);
			id = (Integer) session.save(course_obj);
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
	public Object retrive(HttpServletRequest request) {
		Response response = null;
		try {
			List<CoursePOJO> courses = session.createQuery("FROM CoursePOJO").list();
			response = GeneralUtility.generateSuccessResponse(null, courses);
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return response.toString();
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

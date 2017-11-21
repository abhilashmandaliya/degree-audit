package crud;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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
			SlotPOJO slot = session.get(SlotPOJO.class, Integer.parseInt(slot_id));
			CoursePOJO course_obj = new CoursePOJO(course_name, course_id, course_credits, slot);
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

	public Object search(HttpServletRequest request) throws IOException {

		String search = request.getParameter("search").toLowerCase();
		try {
			if (search.equals("course_details_by_student")) {
				Integer student_id = Integer.parseInt(request.getParameter("student_id"));
				String hql = "SELECT cp.course, cp.course_category FROM GradeCard gc, CourseProgramPOJO cp, StudentPOJO s WHERE cp.course = gc.course_id AND s.id = " + student_id + " AND cp.program = s.program_id AND gc.student_id = "
						+ student_id;
				Query query = session.createQuery(hql);
				List course_list = query.list();
				response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), course_list);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return response;
	}

}

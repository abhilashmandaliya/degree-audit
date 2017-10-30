package crud;

import java.io.IOException;
import pojo.GradeCard;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.StudentPOJO;
import util.GeneralUtility;
import util.Response;

public class GradeCardCRUD extends CRUDCore {

	@Override
	public Response delete(HttpServletRequest request) throws IOException {
		// Transaction tx = null;
		Integer id = null;
		System.out.println("in crud man -----------");
		try {
			// tx = session.beginTransaction();
			long student_id = Long.valueOf(request.getParameter("student_id"));
			String course_id = String.valueOf(request.getParameter("course_id"));
			short semester = Short.valueOf(request.getParameter("semester"));
			boolean status = Boolean.valueOf(request.getParameter("status"));
			double total_credit = Double.valueOf(request.getParameter("total_credit"));
			double obtain_credit = Double.valueOf(request.getParameter("obtain_credit"));
			GradeCard gc = new GradeCard(student_id, course_id, semester, status, total_credit, obtain_credit);
			response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), id);
			try {
				session.delete(gc);
				id = 1;
			} catch (Exception e) {
				id = -1;
			}
			tx.commit();
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
	public Response update(HttpServletRequest request) throws IOException {
		// Transaction tx = null;
		Integer id = null;
		System.out.println("in crud man -----------");
		try {
			// tx = session.beginTransaction();
			long student_id = Long.valueOf(request.getParameter("student_id"));
			String course_id = String.valueOf(request.getParameter("course_id"));
			short semester = Short.valueOf(request.getParameter("semester"));
			boolean status = Boolean.valueOf(request.getParameter("status"));
			double total_credit = Double.valueOf(request.getParameter("total_credit"));
			double obtain_credit = Double.valueOf(request.getParameter("obtain_credit"));
			GradeCard gc = new GradeCard(student_id, course_id, semester, status, total_credit, obtain_credit);

			try {
				session.update(gc);
				id = 1;
			} catch (Exception e) {
				id = -1;
			}
			response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), id);
			// tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session.isOpen()) {
				tx.commit();
				session.close();
			}
		}
		return response;
	}

	@Override
	public Object retrive(HttpServletRequest request) throws IOException {
		Response response = null;
		try {
			long student_id = Long.valueOf(request.getParameter("student_id"));
			String course_id = String.valueOf(request.getParameter("course_id"));
			short semester = Short.valueOf(request.getParameter("semester"));
			try {
				Criteria criteria = session.createCriteria(GradeCard.class);
				criteria.add(Restrictions.eq("student_id", (long) student_id));
				criteria.add(Restrictions.eq("course_id", course_id));
				criteria.add(Restrictions.eq("semester", (short) semester));

				List<GradeCard> list = criteria.list();
				response = GeneralUtility.generateSuccessResponse(null, list);
			} catch (HibernateException e) {
				tx.rollback();
				e.printStackTrace();
			} finally {
				if (session.isOpen()) {
					session.close();
				}
			}
		} catch (Exception e1) {
			try {
				List<GradeCard> programs = session.createQuery("FROM GradeCard").list();
				response = GeneralUtility.generateSuccessResponse(null, programs);
			} catch (HibernateException e) {
				tx.rollback();
				e.printStackTrace();
			} finally {
				if (session.isOpen()) {
					session.close();
				}
			}
		}
		return response.toString();
	}

	@Override
	public Response create(HttpServletRequest request) throws IOException {
		// Transaction tx = null;
		Integer id = null;
		System.out.println("in crud man -----------");
		try {
			// tx = session.beginTransaction();
			long student_id = Long.valueOf(request.getParameter("student_id"));
			String course_id = String.valueOf(request.getParameter("course_id"));
			short semester = Short.valueOf(request.getParameter("semester"));
			boolean status = Boolean.valueOf(request.getParameter("status"));
			double total_credit = Double.valueOf(request.getParameter("total_credit"));
			double obtain_credit = Double.valueOf(request.getParameter("obtain_credit"));
			GradeCard gc = new GradeCard(student_id, course_id, semester, status, total_credit, obtain_credit);
			response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), id);
			try {
				id = (Integer) session.save(gc);
			} catch (Exception e) {
				System.out.println(e + " ----------------------------------------------");
				id = -1;
			}
		} catch (HibernateException e) {
			System.out.println(e + " ----------------------------------------------");
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session.isOpen()) {
				tx.commit();
				session.close();
			}
			;
		}
		return response;
	}

}

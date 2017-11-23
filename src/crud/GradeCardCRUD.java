package crud;

import java.io.IOException;

import pojo.CoursePOJO;
import pojo.CourseProgramPOJO;
import pojo.GradeCard;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import pojo.StudentPOJO;
import util.GeneralUtility;
import util.Response;

public class GradeCardCRUD extends CRUDCore {

	@Override
	public Response delete(HttpServletRequest request) throws IOException {
		Integer id = null;
		//System.out.println("in crud man -----------");
		try {
			int student_id = Integer.valueOf(request.getParameter("student_id"));
			Integer course_id = Integer.valueOf(request.getParameter("course_id"));
			short semester = Short.valueOf(request.getParameter("semester"));
			boolean status = Boolean.valueOf(request.getParameter("status"));
			double earn_grade = Double.valueOf(request.getParameter("earn_grade"));
			CoursePOJO course = session.get(CoursePOJO.class, course_id);
			StudentPOJO student = session.get(StudentPOJO.class, student_id);
			GradeCard gc = new GradeCard(student, course, semester, status, earn_grade);
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
				// session.close();
			}
		}
		return response;
	}

	@Override
	public Response update(HttpServletRequest request) throws IOException {
		Integer id = null;
		//System.out.println("in crud man -----------");
		try {
			int student_id = Integer.valueOf(request.getParameter("student_id"));
			String course_id = String.valueOf(request.getParameter("course_id"));
			short semester = Short.valueOf(request.getParameter("semester"));
			boolean status = Boolean.valueOf(request.getParameter("status"));
			double earn_grade = Double.valueOf(request.getParameter("earn_grade"));
			CoursePOJO course = session.get(CoursePOJO.class, Integer.parseInt(request.getParameter("course_id")));
			StudentPOJO student = session.get(StudentPOJO.class, student_id);
			GradeCard gc = new GradeCard(student, course, semester, status, earn_grade);

			try {
				session.update(gc);
				id = 1;
			} catch (Exception e) {
				id = -1;
			}
			response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), id);
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
		try {
			Object temp = request.getAttribute("student_id");
			Integer student_id;

			if (temp instanceof String)
				student_id = Integer.valueOf((String) temp);
			else
				student_id = (Integer) temp;
			String search = (String) request.getAttribute("search");
			//System.out.println("search " + search);
			if (search != null) {
				try {
					if (search.toLowerCase().equals("get_all_grades_of_student")) {
						Criteria criteria = session.createCriteria(GradeCard.class, "grade_card")
								.createAlias("grade_card.student_id", "student_id")
								.add(Restrictions.eq("student_id", session.get(StudentPOJO.class, student_id)));
						List<GradeCard> courses = criteria.list();
						response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), courses);
						return response;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (student_id != null) {
				try {
					String sql = "SELECT MAX(semester) FROM GradeCard WHERE student_id = " + student_id;
					Query query = session.createQuery(sql);
					Short sum = (Short) query.list().get(0);
					response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), sum);
				} catch (HibernateException e) {
					e.printStackTrace();
				} finally {
					// session.close();
				}
				return response;
			}
			Integer course_id = (Integer) request.getAttribute("course_id");
			CoursePOJO course = session.get(CoursePOJO.class, course_id);
			try {
				Criteria criteria = session.createCriteria(GradeCard.class);
				criteria.add(Restrictions.eq("course_id", course));
				List<GradeCard> list = criteria.list();
				response = GeneralUtility.generateSuccessResponse(null, list);
			} catch (Exception e) {
				tx.rollback();
				e.printStackTrace();
			} finally {
				if (session.isOpen()) {
					// session.close();
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			try {
				List<GradeCard> programs = session.createQuery("FROM GradeCard").list();
				response = GeneralUtility.generateSuccessResponse(null, programs);
			} catch (HibernateException e) {
				tx.rollback();
				e.printStackTrace();
			} finally {
				if (session.isOpen()) {
					// session.close();
				}
			}
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		return response;
	}

	@Override
	public Response create(HttpServletRequest request) throws IOException {
		Integer id = null;
		//System.out.println("in crud man -----------");
		try {
			int student_id = Integer.valueOf(request.getParameter("student_id"));
			Integer course_id = Integer.valueOf(request.getParameter("course_id"));
			short semester = Short.valueOf(request.getParameter("semester"));
			boolean status = Boolean.valueOf(request.getParameter("status"));
			double earn_grade = Double.valueOf(request.getParameter("earn_grade"));
			CoursePOJO course = session.get(CoursePOJO.class, course_id);
			StudentPOJO student = session.get(StudentPOJO.class, student_id);
			// GradeCard gc = new GradeCard(student, course, semester, status, earn_grade);
			GradeCard g = new GradeCard(student, course, semester, status, earn_grade);
			try {
				id = (Integer) session.save(g);
			} catch (Exception e) {
				System.out.println(e);
				id = -1;
			}
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), id);
			if (session.isOpen()) {
				tx.commit();
				// session.close();
			}
		}
		return response;
	}

	public Object getAllStudentCourseWise(HttpServletRequest request) throws IOException {
		Response response = null;
		try {
			int course_id = Integer.valueOf(request.getParameter("course_id"));
			CoursePOJO course = session.get(CoursePOJO.class, course_id);
			try {
				Criteria criteria = session.createCriteria(GradeCard.class);
				criteria.add(Restrictions.eq("course_id", course));
				criteria.setProjection(Projections.projectionList().add(Projections.property("student_id")));

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
}

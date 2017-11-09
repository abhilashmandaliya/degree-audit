package crud;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import pojo.ProgramPOJO;
import pojo.StudentPOJO;
import pojo.UserPOJO;
import util.GeneralUtility;
import util.HibernateSessionFactory;
import util.Response;

public class StudentCRUD extends CRUDCore {

	@Override
	public Response create(HttpServletRequest request) throws IOException {
		// Transaction tx = null;
		Integer id = null;
		System.out.println("in crud man -----------");
		try {
			// tx = session.beginTransaction();
			int user_id = Integer.parseInt(request.getParameter("user_id"));
			int program_id = Integer.parseInt(request.getParameter("program_id"));
			System.out.println("Student id " + user_id + "  program_id " + program_id);
			Integer year_of_enrolment = Integer.parseInt(request.getParameter("year_of_enrolment"));
			UserPOJO user = session.get(UserPOJO.class, user_id);
			ProgramPOJO program = session.get(ProgramPOJO.class, program_id);

			if (user == null || program == null) {
				System.out.println("Bc error che dhyan rakh");

			}

			StudentPOJO student = new StudentPOJO(user, program, year_of_enrolment);
			try {
				id = (Integer) session.save(student);
			} catch (Exception e) {
				id = 1;
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
	public Object retrive(HttpServletRequest request) {
		Response response = null;
		try {
			long user_id = Integer.valueOf(request.getParameter("user_id"));
			try {
				String hql = "FROM StudentPOJO s WHERE user_id =" + user_id;
				Query query = session.createQuery(hql);
				List<StudentPOJO> programs = query.list();

				StudentPOJO s1 = programs.get(0);
				// System.out.println(s1.getStudent_name() + " ***********************");

				response = GeneralUtility.generateSuccessResponse(null, programs);
			} catch (HibernateException e) {
				tx.rollback();
				e.printStackTrace();
			} finally {
				if (session.isOpen()) {
					session.close();
				}
			}
		} catch (Exception e1) {
			System.err.println(e1);
			System.err.println("unexpected error encounter.");
			try {
				List<StudentPOJO> programs = session.createQuery("FROM StudentPOJO").list();
				System.err.println("List size " + programs.size());
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
	public Response update(HttpServletRequest request) throws IOException {
		// Transaction tx = null;
		// Integer id = null;
		// System.out.println("in crud man -----------");
		// try {
		// // tx = session.beginTransaction();
		// String s_name = request.getParameter("student_name");
		// System.err.println("nannanananannnnne,ee " + s_name);
		// Long s_id = Long.parseLong(request.getParameter("student_id"));
		// Integer p_id = Integer.parseInt(request.getParameter("program_id"));
		// Integer year_of_enrolment =
		// Integer.parseInt(request.getParameter("year_of_enrolment"));
		// // StudentPOJO student = new StudentPOJO(s_id, s_name, p_id,
		// year_of_enrolment);
		// try {
		// session.update(null);
		// id = 1;
		// } catch (Exception e) {
		// id = -1;
		// }
		// tx.commit();
		// response =
		// GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request),
		// id);
		// } catch (HibernateException e) {
		// if (tx != null) {
		// tx.rollback();
		// }
		// e.printStackTrace();
		// id = -1;
		// } finally {
		// if (session.isOpen()) {
		// session.close();
		// }
		// }
		return response;
	}

	@Override
	public Response delete(HttpServletRequest request) throws IOException {
		// Transaction tx = null;
		// Integer id = null;
		// System.out.println("in crud man -----------");
		// try {
		// // tx = session.beginTransaction();
		// Long s_id = Long.parseLong(request.getParameter("student_id"));
		//
		// try {
		// String s_name = request.getParameter("student_name");
		// System.err.println("nannanananannnnne,ee " + s_name);
		// Integer p_id = Integer.parseInt(request.getParameter("program_id"));
		// Integer year_of_enrolment =
		// Integer.parseInt(request.getParameter("year_of_enrolment"));
		// // StudentPOJO student = new StudentPOJO(s_id, s_name, p_id,
		// year_of_enrolment);
		// session.delete(null);
		// id = 1;
		// response =
		// GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request),
		// id);
		// } catch (Exception e) {
		// id = -1;
		// }
		// tx.commit();
		// } catch (HibernateException e) {
		// if (tx != null) {
		// tx.rollback();
		// }
		// e.printStackTrace();
		// id = -1;
		// } finally {
		// if (session.isOpen()) {
		// session.close();
		// }
		// }
		return response;
	}

	public Integer getUserId(HttpServletRequest request) throws IOException{
		Integer id = null;
		System.out.println("get user id from student ID");

		int user_id = Integer.valueOf(request.getParameter("student_id"));
		try {
			String hql = "FROM StudentPOJO s WHERE user_id =" + user_id;
			Query query = session.createQuery(hql);
			List<StudentPOJO> programs = query.list();
			//System.out.println("programs size " + programs.size());
			StudentPOJO s1 = programs.get(0);
			id = s1.getStudent_id();
			// System.out.println(s1.getStudent_name() + " ***********************");
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}

		System.out.println("id return ....... " + id);
		return id;
	}
}

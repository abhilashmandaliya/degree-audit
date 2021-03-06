package crud;

import java.io.IOException;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import pojo.CourseGroupCoursePOJO;
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
		try {
			String search = ((String) request.getAttribute("search")).toLowerCase();
			if (search.equals("all_semester_courses")) {
				try {
					Object temp = request.getAttribute("semester_id");
					Short semester_id = null;
					Integer program_id = null;
					if (temp instanceof String)
						semester_id = Short.valueOf((String) temp);
					else
						semester_id = (Short) temp;
					temp = request.getAttribute("program_id");
					if (temp instanceof String)
						program_id = Integer.valueOf((String) temp);
					else
						program_id = (Integer) temp;
					List<SemesterCoursePOJO> semesterCourses = session
							.createQuery("FROM SemesterCoursePOJO WHERE semester = " + semester_id
									+ " AND program_id = " + program_id)
							.list();
					response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request),
							semesterCourses);
				} catch (HibernateException e) {
					tx.rollback();
					e.printStackTrace();
				} finally {
					session.close();
				}
			} else if (search.equals("all_program_courses")) {
				try {
					Object temp = request.getAttribute("program_id");
					Integer program_id;
					if (temp instanceof String)
						program_id = Integer.valueOf((String) temp);
					else
						program_id = (Integer) temp;
					System.out.println(program_id);
					CriteriaBuilder builder = session.getCriteriaBuilder();
					CriteriaQuery<SemesterCoursePOJO> criteria = builder.createQuery(SemesterCoursePOJO.class);
					Root<SemesterCoursePOJO> semesterCoursesPOJORoot = criteria.from(SemesterCoursePOJO.class);
					criteria.select(semesterCoursesPOJORoot);
					criteria.where(builder.equal(semesterCoursesPOJORoot.get("program"), program_id));
					List<SemesterCoursePOJO> semesterCourses = session.createQuery(criteria).getResultList();
					response = GeneralUtility.generateSuccessResponse(null, semesterCourses);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					session.close();
				}
			}
		} catch (Exception e) {
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

		try {
			Integer program_id = Integer.parseInt(request.getParameter("program_id"));
			Integer sem_id = Integer.parseInt(request.getParameter("sem_id"));
			ProgramPOJO program = session.get(ProgramPOJO.class, program_id);
			SemesterPOJO sem = session.get(SemesterPOJO.class, sem_id);

			Query query = session
					.createQuery("DELETE FROM SemesterCoursePOJO WHERE program = :program AND semester = :semester");
			query.setParameter("program", program);
			query.setParameter("semester", sem);
			query.executeUpdate();

			String courses[] = request.getParameter("courses").split(",");
			Integer resultAdded = 0;

			for (String course : courses) {
				CoursePOJO coursep = session.get(CoursePOJO.class, Integer.parseInt(course));
				SemesterCoursePOJO semCourse = new SemesterCoursePOJO(coursep, sem, program);
				session.save(semCourse);
				resultAdded++;
			}
			tx.commit();
			response = GeneralUtility.generateSuccessResponse(null, resultAdded);
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		return response;
	}

	@Override
	public Response delete(HttpServletRequest request) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}

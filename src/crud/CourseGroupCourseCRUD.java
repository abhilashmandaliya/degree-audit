package crud;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.mindrot.jbcrypt.BCrypt;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import pojo.CourseCategoryPOJO;
import pojo.CourseGroupCoursePOJO;
import pojo.CourseGroupPOJO;
import pojo.CoursePOJO;
import pojo.UserCategoryPOJO;
import pojo.UserPOJO;
import util.GeneralUtility;
import util.Response;
import util.UserRole;

public class CourseGroupCourseCRUD extends CRUDCore {

	@Override
	public Response create(HttpServletRequest request) throws IOException {
		Integer id = null;

		try {
			Integer course_id = (Integer) request.getAttribute("course_id");
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
		try {
			String search = ((String) request.getAttribute("search")).toLowerCase();
			if (search.equals("all_course_group_course")) {
				Integer course_id = (Integer) request.getAttribute("course_id");
				CriteriaBuilder builder = session.getCriteriaBuilder();
				CriteriaQuery<CourseGroupCoursePOJO> criteria = builder.createQuery(CourseGroupCoursePOJO.class);
				Root<CourseGroupCoursePOJO> courseGroupCoursePOJORoot = criteria.from(CourseGroupCoursePOJO.class);
				criteria.select(courseGroupCoursePOJORoot);
				criteria.where(builder.equal(courseGroupCoursePOJORoot.get("course_id"), course_id));
				List<CourseGroupCoursePOJO> courseGroupCourses = session.createQuery(criteria).getResultList();
				response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request),
						courseGroupCourses);
			} else if (search.equals("all_course_group_course_course_group_wise")) {
				Integer course_group_course_id = (Integer) request.getAttribute("course_group_course_id");
				CriteriaBuilder builder = session.getCriteriaBuilder();
				CriteriaQuery<CourseGroupCoursePOJO> criteria = builder.createQuery(CourseGroupCoursePOJO.class);
				Root<CourseGroupCoursePOJO> courseGroupCoursePOJORoot = criteria.from(CourseGroupCoursePOJO.class);
				criteria.select(courseGroupCoursePOJORoot);
				criteria.where(builder.equal(courseGroupCoursePOJORoot.get("course_group"), course_group_course_id));
				List<CourseGroupCoursePOJO> courseGroupCourses = session.createQuery(criteria).getResultList();
				List<CoursePOJO> courses = new LinkedList<>();
				for (CourseGroupCoursePOJO courseGroupCoursePOJO : courseGroupCourses) {
					courses.add(courseGroupCoursePOJO.getCourse());
				}
				response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), courses);
			} else if (search.equals("getcoursecoursegroupandmapping")) {
				try {
					List<CourseGroupCoursePOJO> courses = session.createQuery("FROM CourseGroupCoursePOJO").list();
					response = GeneralUtility.generateSuccessResponse(null, courses);
				} catch (HibernateException e) {
					tx.rollback();
					e.printStackTrace();
				} finally {
					session.close();
				}
				return response;
			}
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
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

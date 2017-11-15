package crud;

import java.io.IOException;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import pojo.CoursePOJO;
import pojo.CourseProgramPOJO;
import pojo.ProgramPOJO;
import util.GeneralUtility;
import util.Response;

public class CourseProgramCRUD extends CRUDCore {

	@Override
	public Response create(HttpServletRequest request) throws IOException {
		Integer id = null;
		try {
			Integer course_id = Integer.parseInt(request.getParameter("course_id"));
			Integer program_id = Integer.parseInt(request.getParameter("program_id"));
			boolean is_active = true;
			ProgramPOJO program = session.get(ProgramPOJO.class, program_id);
			CoursePOJO course = session.get(CoursePOJO.class, course_id);
			CourseProgramPOJO courseProgram = new CourseProgramPOJO(program, course, is_active);
			id = (Integer) session.save(courseProgram);
			tx.commit();
			response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), id);
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return response;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Object retrive(HttpServletRequest request) throws IOException {
		try {
			String search = ((String) request.getAttribute("search")).toLowerCase();
			if (search.equals("program_wise_course")) {
				Integer program_id = Integer.parseInt(request.getParameter("program_id"));
				CriteriaBuilder builder = session.getCriteriaBuilder();
				CriteriaQuery<CourseProgramPOJO> criteria = builder.createQuery(CourseProgramPOJO.class);
				Root<CourseProgramPOJO> courseProgramPOJORoot = criteria.from(CourseProgramPOJO.class);
				criteria.select(courseProgramPOJORoot);
				criteria.where(builder.equal(courseProgramPOJORoot.get("program").get("id"), program_id));
				List<CourseProgramPOJO> courses = session.createQuery(criteria).getResultList();
				response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), courses);
			} else if (search.equals("program_course_wise_course_category")) {
				Object temp = request.getAttribute("program_id");
				Integer program_id, course_id;
				if (temp instanceof String)
					program_id = Integer.valueOf((String) temp);
				else
					program_id = (Integer) temp;
				temp = request.getAttribute("course_id");
				if (temp instanceof String)
					course_id = Integer.valueOf((String) temp);
				else
					course_id = (Integer) temp;
				Criteria criteria = session.createCriteria(CourseProgramPOJO.class, "course_program")
						.createAlias("course_program.program", "program").createAlias("course_program.course", "course")
						.add(Restrictions.eq("course.id", course_id)).add(Restrictions.eq("program.id", program_id));
				List<CourseProgramPOJO> courses = criteria.list();
				System.out.println(course_id + " " + program_id);
				response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), courses);
			}
		} catch (HibernateException e) {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response delete(HttpServletRequest request) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}

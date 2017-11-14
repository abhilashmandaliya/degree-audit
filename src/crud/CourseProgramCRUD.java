package crud;

import java.io.IOException;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import pojo.CourseCategoryPOJO;
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
			Integer course_cat_id = Integer.parseInt(request.getParameter("course_cat"));
			boolean is_active = true;
			ProgramPOJO program = session.get(ProgramPOJO.class, program_id);
			CoursePOJO course = session.get(CoursePOJO.class, course_id);
			CourseCategoryPOJO course_cat = session.get(CourseCategoryPOJO.class, course_cat_id);
			CourseProgramPOJO courseProgram = new CourseProgramPOJO(program, course, is_active, course_cat);
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

	@Override
	public Object retrive(HttpServletRequest request) throws IOException {
		try {
			String search = request.getParameter("search").toLowerCase();
			if (search.equals("program_wise_course")) {
				Integer program_id = Integer.parseInt(request.getParameter("program_id"));
				CriteriaBuilder builder = session.getCriteriaBuilder();
				CriteriaQuery<CourseProgramPOJO> criteria = builder.createQuery(CourseProgramPOJO.class);
				Root<CourseProgramPOJO> courseProgramPOJORoot = criteria.from(CourseProgramPOJO.class);
				criteria.select(courseProgramPOJORoot);
				criteria.where(builder.equal(courseProgramPOJORoot.get("program").get("id"), program_id));
				List<CourseProgramPOJO> courses = session.createQuery(criteria).getResultList();
				response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), courses);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
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

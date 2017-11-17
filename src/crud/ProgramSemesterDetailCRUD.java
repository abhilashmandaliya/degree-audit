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
import pojo.ProgramSemesterDetailPOJO;
import pojo.SemesterCoursePOJO;
import pojo.SemesterPOJO;
import util.GeneralUtility;
import util.Response;

public class ProgramSemesterDetailCRUD extends CRUDCore {

	@Override
	public Response create(HttpServletRequest request) throws IOException {
		Integer id = null;
		try {
			Integer core = Integer.parseInt(request.getParameter("core"));
			Integer min_tech_electives = Integer.parseInt(request.getParameter("min_tech_electives"));
			Integer max_tech_electives = Integer.parseInt(request.getParameter("max_tech_electives"));
			Integer min_open_electives = Integer.parseInt(request.getParameter("min_open_electives"));
			Integer max_open_electives = Integer.parseInt(request.getParameter("max_open_electives"));
			Integer min_tech_electives_credits = Integer.parseInt(request.getParameter("min_tech_electives_credits"));
			Integer max_tech_electives_credits = Integer.parseInt(request.getParameter("max_tech_electives_credits"));
			Integer min_open_electives_credits = Integer.parseInt(request.getParameter("min_open_electives_credits"));
			Integer max_open_electives_credits = Integer.parseInt(request.getParameter("max_open_electives_credits"));
			Integer core_credits = Integer.parseInt(request.getParameter("core_credits"));
			Integer semester_id = Integer.parseInt(request.getParameter("semester_id"));
			Integer program_id = Integer.parseInt(request.getParameter("program_id"));
			Integer min_semester_credits = Integer.parseInt(request.getParameter("min_semester_credits"));
			Integer max_semester_credits = Integer.parseInt(request.getParameter("max_semester_credits"));
			Integer min_semester_cources = Integer.parseInt(request.getParameter("min_semester_courses"));
			Integer max_semester_cources = Integer.parseInt(request.getParameter("max_semester_courses"));
			Float min_spi = Float.parseFloat(request.getParameter("min_spi"));
			SemesterPOJO semester = session.get(SemesterPOJO.class, semester_id);
			ProgramPOJO program = session.get(ProgramPOJO.class, program_id);
			ProgramSemesterDetailPOJO programSemesterDetail = new ProgramSemesterDetailPOJO(semester, program, core,
					min_tech_electives, max_tech_electives, min_open_electives, max_open_electives, core_credits,
					min_tech_electives_credits, max_tech_electives_credits, min_open_electives_credits,
					max_open_electives_credits, min_semester_credits, max_semester_credits, min_semester_cources,
					max_semester_cources, min_spi);
			id = (Integer) session.save(programSemesterDetail);
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

	@SuppressWarnings("deprecation")
	@Override
	public Object retrive(HttpServletRequest request) throws IOException {
		try {
			String search = (String) request.getAttribute("search");
			if (search != null) {
				search = search.toLowerCase();
				if (search.equals("program_id_semester_id_wise")) {
					Short semester_id = null;
					Integer program_id = null;
					Object temp = request.getAttribute("semester_id");
					if (temp instanceof String)
						semester_id = Short.valueOf((String) temp);
					else
						semester_id = (Short) temp;
					temp = request.getAttribute("program_id");
					if (temp instanceof String)
						program_id = Integer.valueOf((String) temp);
					else
						program_id = (Integer) temp;

					Criteria criteria = session
							.createCriteria(ProgramSemesterDetailPOJO.class, "program_semester_detail")
							.createAlias("program_semester_detail.program", "program")
							.createAlias("program_semester_detail.semester", "semester")
							.add(Restrictions.eq("semester.id", (int) semester_id))
							.add(Restrictions.eq("program.id", program_id));
					List<ProgramSemesterDetailPOJO> programSemesterDetail = criteria.list();
					response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request),
							programSemesterDetail.get(0));
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response delete(HttpServletRequest request) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}

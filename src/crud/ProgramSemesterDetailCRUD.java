package crud;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import pojo.CoursePOJO;
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
			SemesterPOJO semester = session.get(SemesterPOJO.class, semester_id);
			ProgramPOJO program = session.get(ProgramPOJO.class, program_id);
			ProgramSemesterDetailPOJO programSemesterDetail = new ProgramSemesterDetailPOJO(semester, program, core, min_tech_electives, max_tech_electives, min_open_electives, max_open_electives, core_credits, min_tech_electives_credits, max_tech_electives_credits, min_open_electives_credits, max_open_electives_credits);			
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

	@Override
	public Object retrive(HttpServletRequest request) throws IOException {
		// TODO Auto-generated method stub
		return null;
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

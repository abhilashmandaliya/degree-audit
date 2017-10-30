package crud;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import pojo.ProgramCoordinatorPOJO;
import pojo.ProgramPOJO;
import pojo.UserPOJO;
import util.GeneralUtility;
import util.Response;

public class ProgramCoordinatorCRUD extends CRUDCore {

	@Override
	public Response create(HttpServletRequest request) throws IOException {
		Integer id;
		try {
			Integer program_id = Integer.parseInt(request.getParameter("program_id"));
			Integer coordinator_id = Integer.parseInt(request.getParameter("coordinator_id"));
			ProgramPOJO program = session.get(ProgramPOJO.class, program_id);
			UserPOJO coordinator = session.get(UserPOJO.class, coordinator_id);
			ProgramCoordinatorPOJO programCoordinator = new ProgramCoordinatorPOJO(program, coordinator);
			id = (Integer) session.save(programCoordinator);
			tx.commit();
			response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), id);
		} catch(HibernateException e) {
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

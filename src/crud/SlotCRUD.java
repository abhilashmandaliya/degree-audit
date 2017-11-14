package crud;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import pojo.CoursePOJO;
import pojo.PrerequisitePOJO;
import pojo.SlotPOJO;
import util.GeneralUtility;
import util.Response;

public class SlotCRUD extends CRUDCore {

	@Override
	public Response create(HttpServletRequest request) throws IOException {
		Integer id = null;
		try {
			String name = request.getParameter("name");
			SlotPOJO slot = new SlotPOJO(name);
			id = (Integer) session.save(slot);
			tx.commit();
			response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), id);
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

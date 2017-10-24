package crud;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pojo.UserCategoryPOJO;
import util.CRUD;
import util.GeneralUtility;
import util.HibernateSessionFactory;
import util.Response;

public class UserCategoryCRUD extends CRUDCore {

	@Override
	public Response create(HttpServletRequest request) throws IOException {
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = null;
		Integer id = null;
		try {
			tx = session.beginTransaction();
			String category = request.getParameter("category");
			UserCategoryPOJO userCategory = new UserCategoryPOJO(category);
			id = (Integer) session.save(userCategory);
			tx.commit();
			response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), id);
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
	public Object retrive(HttpServletRequest request) {
		return null;
	}

	@Override
	public Response update(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response delete(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}

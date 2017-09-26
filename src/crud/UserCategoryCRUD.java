package crud;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pojo.UserCategoryPOJO;
import util.CRUD;
import util.HibernateSessionFactory;

public class UserCategoryCRUD implements CRUD {

	@Override
	public Integer create(HttpServletRequest request) {
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = null;
		Integer id = null;
		try {
			tx = session.beginTransaction();
			String category = request.getParameter("category");
			UserCategoryPOJO userCategory = new UserCategoryPOJO(category);
			id = (Integer) session.save(userCategory);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return id;
	}

	@Override
	public Object retrive(HttpServletRequest request) {
		return null;
	}

	@Override
	public Integer update(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delete(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}

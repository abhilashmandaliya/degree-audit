package crud;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;

import pojo.UserCategoryPOJO;
import pojo.UserPOJO;
import util.CRUD;
import util.HibernateSessionFactory;

public class UserCRUD implements CRUD {

	@Override
	public Integer create(HttpServletRequest request) {
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = null;
		Integer id = null;
		try {
			tx = session.beginTransaction();
			String username = request.getParameter("username");
			String password = BCrypt.hashpw(request.getParameter("password"), BCrypt.gensalt(12));
			UserCategoryPOJO category = session.get(UserCategoryPOJO.class,
					Integer.parseInt(request.getParameter("category")));
			short is_active = 1;
			UserPOJO user = new UserPOJO(username, password, category, is_active);
			id = (Integer) session.save(user);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return id;
	}

	@Override
	public Object retrive(HttpServletRequest request) {
		// TODO Auto-generated method stub
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

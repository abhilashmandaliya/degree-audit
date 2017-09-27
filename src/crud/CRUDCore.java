package crud;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.google.gson.Gson;

import util.CRUD;
import util.HibernateSessionFactory;
import util.Response;

public abstract class CRUDCore implements CRUD {

	protected Session session;
	protected Transaction tx;
	protected Response response;
	protected Gson json;

	public CRUDCore() {
		session = HibernateSessionFactory.getSession();
		tx = session.beginTransaction();
		response = new Response();
		json = new Gson();
		//json.toJson(response);
	}

	@Override
	public Integer create(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Audit_Report;

import pojo.ProgramPOJO;
import util.GeneralUtility;
import util.HibernateSessionFactory;
import util.Response;
/**
 *
 * @author Ankit
 */
public class audi_crud  extends CRUDCore
{
    @Override
	public Integer create(HttpServletRequest request) {
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = null;
		Integer id = null;
		try {
			tx = session.beginTransaction();
			int data_generated = Integer.parseInt(request.getParameter("data_generated"));
			double credit = Double.parseDouble(request.getParameter("credit"));
			double total_credit = Double.parseDouble(request.getParameter("total_credit"));
			Integer no_of_cource = Integer.parseInt(request.getParameter("no_of_cource"));
			Integer CPI = Integer.parseInt(request.getParameter("CPI"));
			Audit_Report report = new Audit_Report(data_generated,credit,total_credit,CPI,no_of_cource);
			id = (Integer) session.save(report);
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
		Response response = null;
		try {
			List<Audit_Report> report = session.createQuery("FROM Audit_Report").list();
			response = GeneralUtility.generateSuccessResponse(null, report);
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return response.toString();
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

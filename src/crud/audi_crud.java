/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud;
import java.io.IOException;
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
	public Response create(HttpServletRequest request) throws IOException {
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = null;
		Integer id = null;
		try {
			tx = session.beginTransaction();
			//id=Integer.parseInt(request.getParameter("id"));
			int date_generated = Integer.parseInt(request.getParameter("date_generated"));
			double percentage_of_degree_finish = Double.parseDouble(request.getParameter("percentage_of_degree_finish"));
			double obtained_credit = Double.parseDouble(request.getParameter("obtained_credit"));
			double require__credit = Double.parseDouble(request.getParameter("require__credit"));
			double present_CPI = Double.parseDouble(request.getParameter("present_CPI"));
			double require_CPI = Double.parseDouble(request.getParameter("require_CPI"));
			//double present_CPI = Double.parseDouble(request.getParameter("present_CPI"));
			double present_cource = Double.parseDouble(request.getParameter("present_cource"));
			double require_courcce = Double.parseDouble(request.getParameter("require_courcce"));
			double time_left_finish_degree = Double.parseDouble(request.getParameter("time_left_finish_degree"));
			Audit_Report report = new Audit_Report(date_generated,percentage_of_degree_finish,obtained_credit,require__credit,present_CPI,require_CPI,present_cource,require_courcce,time_left_finish_degree);
			id = (Integer) session.save(report);
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

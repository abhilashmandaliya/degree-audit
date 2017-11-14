/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud;

import java.io.IOException;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import pojo.Audit_Report;
import pojo.CourseCategoryPOJO;
import pojo.ProgramPOJO;
import pojo.SemesterPOJO;
import pojo.UserCategoryPOJO;
import pojo.UserPOJO;
import util.GeneralUtility;
import util.HibernateSessionFactory;
import util.Response;
import util.UserRole;

/**
 *
 * @author Ankit
 */
public class audi_crud2 extends CRUDCore {
	@Override
	public Response create(HttpServletRequest request) throws IOException {
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = null;
		Integer id = null;
		try {
			tx = session.beginTransaction();
			// id=Integer.parseInt(request.getParameter("id"));
			// int date_generated =
			// Integer.parseInt(request.getParameter("date_generated"));
			double percentage_of_degree_finish = Double
					.parseDouble(request.getParameter("percentage_of_degree_finish"));
			double obtained_credit = Double.parseDouble(request.getParameter("obtained_credit"));
			double require__credit = Double.parseDouble(request.getParameter("require__credit"));
			double present_CPI = Double.parseDouble(request.getParameter("present_CPI"));
			double require_CPI = Double.parseDouble(request.getParameter("require_CPI"));
			// double present_CPI = Double.parseDouble(request.getParameter("present_CPI"));
			double present_cource = Double.parseDouble(request.getParameter("present_cource"));
			double require_courcce = Double.parseDouble(request.getParameter("require_courcce"));
			double time_left_finish_degree = Double.parseDouble(request.getParameter("time_left_finish_degree"));
			UserPOJO user_pojo = session.get(UserPOJO.class, Integer.parseInt(request.getParameter("user")));
			SemesterPOJO sem = session.get(SemesterPOJO.class, Integer.parseInt(request.getParameter("sem")));
			Audit_Report report = new Audit_Report(percentage_of_degree_finish, obtained_credit, require__credit,
					present_CPI, require_CPI, present_cource, require_courcce, time_left_finish_degree, user_pojo, sem);
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
	public Object retrive(HttpServletRequest request) throws IOException {
		Response response = null;
		System.out.println("uhkj");
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			UserPOJO user = session.get(UserPOJO.class, id);
			String hql = "from Audit_Report where user_id=" + id;
			Query query = session.createQuery(hql);
			List<Audit_Report> audit = query.list();
			response = GeneralUtility.generateSuccessResponse(null, audit.get(0));
			/*
			 * CriteriaBuilder builder = session.getCriteriaBuilder();
			 * CriteriaQuery<Audit_Report> criteria =
			 * builder.createQuery(Audit_Report.class); Root<Audit_Report> Audit_ReportRoot
			 * = criteria.from(Audit_Report.class); criteria.select(Audit_ReportRoot);
			 * criteria.where(builder.equal(Audit_ReportRoot.get("id"), id));
			 * //System.out.println("ankit"); List<Audit_Report> audit =
			 * session.createQuery(criteria).getResultList();
			 * System.out.println(audit.size());
			 * response=GeneralUtility.generateSuccessResponse(null, audit);
			 */
			tx.commit();
			session.close();
			/*
			 * if (!audit.isEmpty()) { Audit_Report Audit_report = audit.get(0); //String
			 * hashed = userPOJO.getPassword(); UserPOJO userPOJO = Audit_report.getUser();
			 * String userdata = userPOJO.getUsername(); System.out.println(userdata);
			 * String authJSON = GeneralUtility.readAuthJSON(); JsonParser parser = new
			 * JsonParser(); JsonArray data = parser.parse(authJSON).getAsJsonArray(); for
			 * (JsonElement element : data) { UserRole role = json.fromJson(element,
			 * UserRole.class); if (role.getRole().equalsIgnoreCase(userdata)) {
			 * //userPOJO.setPassword(""); response =
			 * GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request),
			 * Audit_report); break; } } HttpSession session = request.getSession();
			 * session.setAttribute("userdata", userdata);
			 * System.out.println(session.getAttribute(userdata)); }
			 */
		}

		catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return response.toString();
	}

	/*
	 * try { int id = Integer.parseInt(request.getParameter("id"));
	 * List<Audit_Report> report = session.
	 * createQuery("select a.id,a.date_generated,a.obtained_credit,a.percentage_of_degree_finish,b.id FROM Audit_Report a,UserPOJO b  where a.id="
	 * +id).list();//("FROM Audit_Report where  id="+id).list(); response =
	 * GeneralUtility.generateSuccessResponse(null, report); }
	 */
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

	public JsonArray getUserAudit(HttpServletRequest request) {
		System.out.println(request.getAttribute("id"));
		System.out.println("LOLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
		int id = (int) request.getAttribute("id");
		UserPOJO user = session.get(UserPOJO.class, id);
		String hql = "from Audit_Report where user_id=" + id;
		Query query = session.createQuery(hql);
		List<Audit_Report> audit = query.list();
		
		JsonArray array = new JsonArray();
		for(Audit_Report report : audit) {
			JsonObject obj= new JsonObject();
			obj.addProperty("id", report.getId());
			UserPOJO user1 = report.getUser();
			obj.addProperty("user_id", user1.getId());
			obj.addProperty("audit_date", report.getDate_generated());
			array.add(obj);
		}
		
		return array;
	}
}

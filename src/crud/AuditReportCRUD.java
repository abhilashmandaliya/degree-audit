package crud;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import pojo.AuditReportPOJO;
import util.GeneralUtility;
import util.Response;

public class AuditReportCRUD extends CRUDCore {

	@Override
	public Response create(HttpServletRequest request) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object retrive(HttpServletRequest request) throws IOException {

		try {
			Integer student_id = Integer.parseInt(request.getParameter("student_id"));
			String hql = "FROM AuditReportPOJO ar WHERE ar.student_id = " + student_id + " ORDER BY ar.student_id";
			Query query = session.createQuery(hql);
			List<AuditReportPOJO> audit_list = query.list();
			response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), audit_list);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return response;
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

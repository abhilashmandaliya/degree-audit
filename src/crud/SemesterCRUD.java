package crud;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import pojo.CourseGroupCoursePOJO;
import pojo.CourseGroupPOJO;
import pojo.CoursePOJO;
import pojo.ProgramCoordinatorPOJO;
import pojo.ProgramPOJO;
import pojo.ProgramSemesterDetailPOJO;
import pojo.SemesterPOJO;
import util.GeneralUtility;
import util.Response;

public class SemesterCRUD extends CRUDCore {

	@Override
	public Response create(HttpServletRequest request) throws IOException {
		Integer id = null;

		try {
			String name = request.getParameter("name");
			SemesterPOJO semester = new SemesterPOJO(name);
			id = (Integer) session.save(semester);
			System.out.println(id);
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
		// TODO Auto-generated method stub

		String search = (String) request.getAttribute("search");

		if (search.equalsIgnoreCase("semester_id_from_name")) {
			try {
				String semester_name = (String) request.getAttribute("semester_name");
				Criteria criteria = session
						.createCriteria(SemesterPOJO.class, "semester")
						.add(Restrictions.eq("semester.name", semester_name));
				List<ProgramPOJO> semester = criteria.list();
				response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), semester);
			} catch (HibernateException e) {
				tx.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}
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

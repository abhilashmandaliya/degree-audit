package crud;

import java.io.IOException;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.mindrot.jbcrypt.BCrypt;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import pojo.CoursePOJO;
import pojo.GradeCard;
import pojo.PrerequisitePOJO;
import pojo.StudentPOJO;
import pojo.UserCategoryPOJO;
import pojo.UserPOJO;
import util.GeneralUtility;
import util.Response;
import util.UserRole;

public class PrerequisiteCRUD extends CRUDCore {

	@Override
	public Response create(HttpServletRequest request) throws IOException {
		// Transaction tx = null;
		Integer id = null;
		try {
			int for_course_id = Integer.parseInt(request.getParameter("for_course_id"));
			int required_course_id = Integer.parseInt(request.getParameter("required_course_id"));
			CoursePOJO for_course = session.get(CoursePOJO.class, for_course_id);
			CoursePOJO required_course = session.get(CoursePOJO.class, required_course_id);
			PrerequisitePOJO prerequisite = new PrerequisitePOJO(for_course, required_course);
			id = (Integer) session.save(prerequisite);
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
		try {
			String search = request.getParameter("search").toLowerCase();
			if (search.equals("all_prerequisites")) {
				Integer for_course = Integer.parseInt(request.getParameter("for_course"));
				CriteriaBuilder builder = session.getCriteriaBuilder();
				CriteriaQuery<PrerequisitePOJO> criteria = builder.createQuery(PrerequisitePOJO.class);
				Root<PrerequisitePOJO> prerequisitePOJORoot = criteria.from(PrerequisitePOJO.class);
				criteria.select(prerequisitePOJORoot);
				criteria.where(builder.equal(prerequisitePOJORoot.get("for_course"), for_course));
				List<PrerequisitePOJO> prerequisites = session.createQuery(criteria).getResultList();
				response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), prerequisites);
			}
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

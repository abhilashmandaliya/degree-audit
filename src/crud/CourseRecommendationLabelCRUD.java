package crud;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import pojo.CourseRecommendationLabelPOJO;
import util.GeneralUtility;
import util.Response;

public class CourseRecommendationLabelCRUD extends CRUDCore {

	@Override
	public Response create(HttpServletRequest request) throws IOException {
		Integer id = null;
		try {
			String label = request.getParameter("label");
			CourseRecommendationLabelPOJO courseRecommendationLabel = new CourseRecommendationLabelPOJO(label);
			id = (Integer) session.save(courseRecommendationLabel);
			tx.commit();
			response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), id);
		} catch (HibernateException e) {
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
		return null;
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

package chart;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;

import crud.CRUDCore;
import pojo.StudentPOJO;
import util.GeneralUtility;
import util.Response;

public class PieChart extends CRUDCore {

	@Override
	public Response create(HttpServletRequest request) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object retrive(HttpServletRequest request) throws IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action").toLowerCase();
		if (action.equals("getcreditwisepiechart")) {
			int student_id = Integer.parseInt(request.getParameter("student_id"));
			String sql = "SELECT SUM(course_credits) FROM CoursePOJO WHERE id IN (SELECT course_id FROM GradeCard WHERE student_id = "
					+ student_id + ")";
			Query query = session.createQuery(sql);
			Long credits_earned = (Long) query.list().get(0);
			sql = "SELECT min_credits FROM ProgramPOJO WHERE id = (Select program_id FROM StudentPOJO WHERE id = "
					+ student_id + ")";
			query = session.createQuery(sql);
			Integer credits_required = (Integer) query.list().get(0);
			String[] labels = { "Required", "Earned" };
			String[] backGroundColors = { "#ff0000", "#00ff00" };
			Long[] data = { Math.max(0, credits_required - credits_earned), credits_earned };
			Chart chart = new Chart(labels, backGroundColors, data);
			response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), chart);
		} else if (action.equals("getcoursewisepiechart")) {
			int student_id = Integer.parseInt(request.getParameter("student_id"));
			String sql = "SELECT COUNT(student_id) FROM GradeCard WHERE student_id = " + student_id + ")";
			Query query = session.createQuery(sql);
			List temp = query.list();
			Long courses_taken = temp.isEmpty() ? 0 : (Long) temp.get(0);
			sql = "SELECT min_courses FROM ProgramPOJO WHERE id = (Select program_id FROM StudentPOJO WHERE id = "
					+ student_id + ")";
			query = session.createQuery(sql);
			temp = query.list();
			Integer courses_required = temp.isEmpty() ? 0 : (Integer) query.list().get(0);
			String[] labels = { "Required", "Taken" };
			String[] backGroundColors = { "#ff0000", "#00ff00" };
			Long[] data = { Math.max(0, courses_required - courses_taken), courses_taken };
			Chart chart = new Chart(labels, backGroundColors, data);
			response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), chart);
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

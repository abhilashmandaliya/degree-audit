package util;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import crud.StudentCRUD;
import pojo.GradeCard;
import pojo.StudentPOJO;

public class StudentGradeCard {

	protected Session session;
	protected Transaction tx;
	protected Response response;
	protected Gson json;
	private HashMap<String, Object> student_details;
	private HashMap<String, Object> semesters;
	HashSet<Object> coursesSet = new HashSet<>();

	public StudentGradeCard() {
		session = HibernateSessionFactory.getSession();
		tx = session.beginTransaction();
		response = new Response();
		json = new Gson();
	}

	public Object studentDetails(HttpServletRequest request) {
		student_details = new HashMap<>();
		semesters = new HashMap<>();
		coursesSet = new HashSet<>();

		Integer id = null;
		response = null;
		System.out.println("checkout ----------------------------------------------------------");
		try {
			String student_id = request.getParameter("student_id");

			Integer _id = new StudentCRUD().getUserId(request);
			/*
			 * try { Integer _id = new StudentCRUD().getUserId(request);
			 * System.out.println("ID found  " + _id); } catch (Exception e) {
			 * System.out.println("exception check function please ..."); }
			 */

			String hql = null;
			List resuList = null;
			StudentPOJO student = session.get(StudentPOJO.class, Integer.valueOf(_id));
			resuList = session.createCriteria(GradeCard.class).add(Restrictions.eq("student_id", student))
					/*
					 * .setProjection(Projections.projectionList().add(Projections.groupProperty(
					 * "student_id"))
					 * .add(Projections.groupProperty("semester")).add(Projections.groupProperty(
					 * "course_id")) .add(Projections.groupProperty("earn_grade")))
					 */
					.list();

			/*
			 * Query query = session.createSQLQuery(hql); List<Object[]> list =
			 * query.list(); for (Object[] arr : list) {
			 * System.out.println(Arrays.toString(arr)); }
			 */
			student_details.put("student_details", student);
			System.out.println("checkout ----------------------------------------------------------");
			GsonBuilder builder = new GsonBuilder();
			builder.setPrettyPrinting();
			Gson gson = builder.create();
			String sendResponse = gson.toJson(resuList);

			System.out.println("Response get ***************\n" + sendResponse);
			JsonElement jelement = new JsonParser().parse(sendResponse);
			JsonArray courses = jelement.getAsJsonArray();

			// System.out.println("Response get ***************\n" + courses);
			for (int i = 0; i < courses.size(); i++) {
				JsonObject course = (JsonObject) courses.get(i);
				String sem = course.get("semester").toString();
				JsonObject courseID = course.getAsJsonObject("course_id");
				courseID.add("earn_grade", course.get("earn_grade"));

				if (semesters.containsKey(sem)) {
					HashSet<Object> set = (HashSet<Object>) semesters.get(sem);
					set.add(courseID);
					semesters.put(sem, set);
				} else {
					HashSet<Object> set = new HashSet<>();
					set.add(courseID);
					semesters.put(sem, set);
				}

				System.out.println("Response get ***************\n" + course);
				HashMap<String, String> cours = new HashMap<>();
			}

			JsonParser parser = new JsonParser();
			JsonObject o = parser.parse(getDataFromAuditReport(request)).getAsJsonObject();
			student_details.put("Audit", o);
			student_details.put("semester", semesters);
			String manualResponse = gson.toJson(student_details);

			System.out.println(
					"Response in my CRUD \n" + manualResponse + "\n----------------------------------------------");

			response = GeneralUtility.generateSuccessResponse(null, student_details);
			tx.commit();
		} catch (NumberFormatException | HibernateException | IOException e1) {
			System.out.println(e1 + " -------------------------");
			tx.rollback();
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		return response.toString();
	}

	public String getDataFromAuditReport(HttpServletRequest req) {

		return "{'key': 'value', 'key2': 'value2' }";
	}
}

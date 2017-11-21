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

import crud.AuditReportCRUD;
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
			request.setAttribute("id", _id);

			String hql = null;
			List resuList = null;
			StudentPOJO student = session.get(StudentPOJO.class, Integer.valueOf(_id));
			resuList = session.createCriteria(GradeCard.class).add(Restrictions.eq("student_id", student))
					.list();

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

				System.out.println("course _ id " + courseID);

				if (semesters.containsKey(sem)) {
					HashSet<JsonObject> set = (HashSet<JsonObject>) semesters.get(sem);
					set.add(courseID);
					semesters.put(sem, set);
				} else {
					HashSet<JsonObject> set = new HashSet<>();
					set.add(courseID);
					semesters.put(sem, set);
				}

				System.out.println("Response get ***************\n" + course);
				// HashMap<String, String> cours = new HashMap<>();
			}

			double total_course_credit = 0;
			double cpi = 0;
			double total_grade_points = 0;

			for (String key : semesters.keySet()) {
				HashSet<Object> set = (HashSet<Object>) semesters.get(key);
				double sem_course_credit = 0;
				double sem_grade_points = 0; // mull
				double spi;

				for (Object couri : set) {
					JsonObject cour = (JsonObject) couri;
					System.out.println("Testing ........ " + cour);
					double credit = Double.valueOf(cour.get("course_credits").toString());
					double grade = Double.valueOf(cour.get("earn_grade").toString());
					sem_course_credit += credit;
					sem_grade_points += (grade * credit);
				}
				
				HashMap<String, Object> temp = new HashMap<>();
				temp.put("course", set);

				spi = sem_grade_points / sem_course_credit;
				temp.put("sem_course_credit", sem_course_credit);
				temp.put("sem_grade_points", sem_grade_points);
				temp.put("spi", spi);
				semesters.put(key, temp);	

				total_grade_points += sem_grade_points;
				total_course_credit += sem_course_credit;
			}

			cpi = total_grade_points / total_course_credit;
			student_details.put("total_course_credit", total_course_credit);
			student_details.put("toal_grade_points", total_grade_points);
			student_details.put("cpi", cpi);

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

	public Object getDataFromAuditReport(HttpServletRequest req) throws IOException {
		return new AuditReportCRUD().retrive(req);
	}
}

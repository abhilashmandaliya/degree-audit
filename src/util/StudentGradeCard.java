package util;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import crud.CRUDCore;
import crud.GradeCardUtility;
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
			// tx = session.beginTransaction();
			String student_id = request.getParameter("student_id");
			// String course_id = request.getParameter("course_id");
			// String semester = request.getParameter("semester");
			String hql = null;
			List resuList = null;

			/*
			 * hql =
			 * "select student_id, semester, sum(obtain_grade) as obtain_total, sum(total_grade) as total_grade from grade_card "
			 * + "where student_id = " + Long.valueOf(student_id) + " and semester = " +
			 * Short.valueOf(semester) + " group by semester,student_id;";
			 */
			resuList = session.createCriteria(GradeCard.class)
					.add(Restrictions.eq("student_id", Long.valueOf(student_id)))
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

			student_details.put("student_details", "api call of abhilash");
			System.out.println("checkout ----------------------------------------------------------");
			GsonBuilder builder = new GsonBuilder();
			builder.setPrettyPrinting();
			Gson gson = builder.create();
			String sendResponse = gson.toJson(resuList);

			// System.out.println("Response get ***************\n" + sendResponse);

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
				/*
				 * cours.put("course_id", course.get(2).toString()); cours.put("obtain_grade",
				 * course.get(3).toString()); cours.put("total_grade",
				 * course.get(4).toString());
				 * 
				 * String sem = course.get(1).toString(); if (semesters.containsKey(sem)) {
				 * HashSet<Object> set = (HashSet<Object>) semesters.get(sem); set.add(cours);
				 * semesters.put(sem, set); } else { HashSet<Object> set = new HashSet<>();
				 * set.add(cours); semesters.put(sem, set); }
				 */
			}

			student_details.put("semester", semesters);
			String manualResponse = gson.toJson(student_details);

			System.out.println(
					"Response in my CRUD \n" + manualResponse + "\n----------------------------------------------");

			response = GeneralUtility.generateSuccessResponse(null, student_details);
			tx.commit();
		} catch (NumberFormatException | HibernateException e1) {
			System.out.println(e1 + " -------------------------");
			tx.rollback();
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		return response.toString();
	}

	// public void checkGetStudentData(HttpServletRequest request) {
	// String str = (String) new StudentCRUD().retrive(request);
	// System.out.println(str);
	// JsonElement jelement = new JsonParser().parse(str);
	// JsonObject jobject = jelement.getAsJsonObject();
	// JsonArray array = jobject.getAsJsonArray("data");
	// for (int i = 0; i < array.size(); i++) {
	// JsonObject obj1 = (JsonObject) array.get(i);
	// System.out.println(obj1 + " --- obj1");
	// System.out.println(obj1.get("student_name"));
	// }
	// System.out.println(array.size());
	// System.out.println(array);
	// /*
	// * Response response = json.fromJson(str, Response.class); List<StudentPOJO>
	// * list = (List<StudentPOJO>) response.getData();
	// * System.out.println(list.get(0).getStudent_name());
	// */
	// }
	//
	// public void extractUserData(HttpServletRequest request) {
	// String str = (String) new StudentCRUD().retrive(request);
	// System.out.println(str);
	// JsonElement jelement = new JsonParser().parse(str);
	// JsonObject jobject = jelement.getAsJsonObject();
	// }
	//
	// public HashMap<String, Object> extractGradeCardData(HttpServletRequest
	// request) {
	// String str = (String) new
	// GradeCardUtility().getStudentGradeCardBySemester(request);
	// System.out.println(str);
	// JsonElement jelement = new JsonParser().parse(str);
	// JsonObject jobject = jelement.getAsJsonObject();
	// JsonArray array = jobject.getAsJsonArray("data");
	// if (array.isJsonNull())
	// return null;
	//
	// HashMap<String, Object> data = new HashMap<>();
	//
	// HashMap<String, Object> Semesters = new HashMap<>();
	//
	// HashSet<Object> semSet = new HashSet<>();
	//
	// HashSet<Object> courses = new HashSet<>();
	// for (int j = 0; j < array.size(); j++) {
	// JsonArray course = array.getAsJsonArray();
	// HashMap<String, String> courseMap = new HashMap<>();
	// courseMap.put("course_id", course.get(1).toString());
	// courseMap.put("obtain_grade", course.get(2).toString());
	// courseMap.put("total_grade", course.get(3).toString());
	// Object obj = courseMap;
	// courses.add(obj);
	// }
	//
	// return data;
	// }
	//
	// public Object getStudentDetails(HttpServletRequest request) {
	// HashMap<String, Object> student = new HashMap<>();
	// student.put("student_id", request.getParameter("student_id"));
	// String studentString = new StudentCRUD().retrive(request).toString();
	// JsonElement jelement = new JsonParser().parse(studentString);
	// JsonObject jobject = jelement.getAsJsonObject();
	// JsonArray array = jobject.getAsJsonArray("data");
	// for (int i = 0; i < array.size(); i++) {
	// JsonObject obj1 = (JsonObject) array.get(i);
	// System.out.println(obj1 + " --- obj1");
	// System.out.println(obj1.get("student_name"));
	// }
	// System.out.println(array.size());
	// System.out.println(array);
	//
	// return null;
	// }
	//
	// private Object getSemester(JsonArray sem) {
	// HashMap<String, Object> sems = new HashMap<>();
	// for (int i = 0; i < sem.size(); i++) {
	// JsonArray course = sem.getAsJsonArray();
	// String sem_no = course.get(0).toString();
	// if (sems.containsKey(sem_no)) {
	// HashSet<Object> coursesSet = (HashSet<Object>) sems.get(sem_no);
	// coursesSet.add(getCourse(course));
	// sems.put(sem_no, coursesSet);
	// } else {
	// HashSet<Object> coursesSet = new HashSet<>();
	// coursesSet.add(getCourses(course));
	// sems.put(sem_no, coursesSet);
	// }
	// }
	// return sems;
	// }
	//
	// private HashSet<Object> getCourses(JsonArray courses) {
	// HashSet<Object> coursesSet = new HashSet<>();
	// for (int j = 0; j < courses.size(); j++) {
	// coursesSet.add(courses.getAsJsonArray());
	// }
	// return coursesSet;
	// }
	//
	// private HashMap<String, Object> getCourse(JsonArray course) {
	// HashMap<String, Object> courseMap = new HashMap<>();
	// courseMap.put("course_id", course.get(1).toString());
	// courseMap.put("obtain_grade", course.get(2).toString());
	// courseMap.put("total_grade", course.get(3).toString());
	// return courseMap;
	// }
}

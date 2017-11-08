package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import crud.CourseGroupCourseCRUD;
import crud.GradeCardCRUD;
import crud.PrerequisiteCRUD;
import pojo.CourseGroupCoursePOJO;
import pojo.CoursePOJO;
import pojo.GradeCard;
import pojo.PrerequisitePOJO;

public final class GeneralUtility {

	private static GeneralUtility instance;
	private static Gson json;
	private final static String ACTION_REDIRECT = "controller/ActionRedirect.properties";

	private GeneralUtility() {
	}

	private static GeneralUtility getInstance() {
		if (instance == null) {
			instance = new GeneralUtility();
			json = new Gson();
		}
		return instance;
	}

	private String readAuthJSONUtil() {
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream("auth.json");
		StringBuffer data = new StringBuffer();
		int item;
		try {
			while ((item = stream.read()) != -1) {
				data.append((char) item);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data.toString();
	}

	public final static String readAuthJSON() {
		String data = getInstance().readAuthJSONUtil();
		return data;
	}

	public static Response generateUnauthorizedResponse() {
		Response response = new Response("401", "Unauthorized", null, null);
		return response;
	}

	public static Response generateSuccessResponse(String redirect, Object data) {
		Response response = new Response(redirect, data);
		response.setStatusCode("200");
		response.setMessage("Action Successful");
		return response;
	}

	public static boolean isAutheticatedUser(HttpServletRequest request) {
		boolean authenticated = false;
		HttpSession session = request.getSession();
		String category;
		if ((category = (String) session.getAttribute("userCategory")) != null) {
			String action = request.getParameter("action");
			if (action != null) {
				JsonParser parser = new JsonParser();
				JsonArray roles = parser.parse(readAuthJSON()).getAsJsonArray();
				outer: for (JsonElement ele : roles) {
					UserRole role = json.fromJson(ele, UserRole.class);
					if (role.getRole().equalsIgnoreCase(category)) {
						Map<String, String> allowedAction = role.getAction();
						for (String key : allowedAction.keySet()) {
							if (key.equalsIgnoreCase(action)) {
								authenticated = true;
								break outer;
							}
						}
					}
				}
			}
		}
		return authenticated;
	}

	public static String getRedirect(HttpServletRequest request) throws IOException {
		String mode = request.getParameter("mode");
		if (mode == null || mode.equalsIgnoreCase("api")) {
			return null;
		}
		String theAction = request.getParameter("action");
		Properties map = new Properties();
		map.load(instance.getClass().getClassLoader().getResourceAsStream(ACTION_REDIRECT));
		String action_redirect = map.getProperty(theAction.toLowerCase());
		return action_redirect;
	}

	public static float getCourseEligibility(HttpServletRequest request) throws IOException {
		final Float NOT_RECOMMENDED = -1f;
		final Float NOTHING_CAN_BE_SAID = 0f;
		final Float HIGHLY_RECOMMENDED = 1f;
		Integer course_id = (Integer) request.getAttribute("course_id");
		request.setAttribute("search", "all_prerequisites");
		List<PrerequisitePOJO> prerequisites = (List<PrerequisitePOJO>) ((Response) new PrerequisiteCRUD()
				.retrive(request)).getData();
		float average = 0.0f;
		int cnt = 0;
		if (prerequisites != null) {
			for (PrerequisitePOJO prerequisite : prerequisites) {
				CoursePOJO required_course = prerequisite.getRequired_course();
				request.setAttribute("course_id", required_course.getCourse_id());
				List<GradeCard> gradeCards = (List<GradeCard>) ((Response) new GradeCardCRUD().retrive(request)).getData();
				if (gradeCards == null) {
					return NOT_RECOMMENDED;
				}
				for (GradeCard gradeCard : gradeCards) {
					if (!gradeCard.isStatus()) {
						return NOT_RECOMMENDED;
					}
					average += gradeCard.getEarn_grade();
					cnt++;
				}
			}
		}
		Integer student_id = Integer.parseInt(request.getParameter("student_id"));
		request.setAttribute("course_id", course_id);
		List<CourseGroupCoursePOJO> courseGroupCourses = (List<CourseGroupCoursePOJO>) ((Response) new CourseGroupCourseCRUD()
				.retrive(request)).getData();
		if (courseGroupCourses != null) {
			request.setAttribute("search", "all_course_group_course_course_group_wise");
			for (CourseGroupCoursePOJO courseGroupCourse : courseGroupCourses) {
				request.setAttribute("course_group_course_id", courseGroupCourse.getId());
				List<CoursePOJO> courses = (List<CoursePOJO>) ((Response) (new CourseGroupCourseCRUD()
						.retrive(request))).getData();
				float temp_average = 0.0f;
				int temp_cnt = 0;
				GradeCardCRUD gradeCardCRUD = new GradeCardCRUD();
				for (CoursePOJO course : courses) {
					request.setAttribute("course_id", course.getId());
					List<GradeCard> gradeCards = (List<GradeCard>) ((Response) gradeCardCRUD.retrive(request))
							.getData();
					if (gradeCards.size() > 0) {
						for (GradeCard gradeCard : gradeCards) {
							temp_cnt++;
							temp_average += gradeCard.getEarn_grade();
						}
					}
				}
				if (temp_cnt > 0) {
					temp_average /= temp_cnt;
					if (temp_average < courseGroupCourse.getCourse_group().getMin_avg()) {
						return NOT_RECOMMENDED;
					}
				}
			}
		}
		return cnt == 0 ? NOTHING_CAN_BE_SAID : HIGHLY_RECOMMENDED;
	}

	public static void copyParamsToAttributes(HttpServletRequest request) {
		Iterator<String> params = request.getParameterNames().asIterator();
		while (params.hasNext()) {
			String param = params.next();
			request.setAttribute(param, request.getParameter(param));
		}
	}
}

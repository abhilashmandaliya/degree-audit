package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
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

import crud.CourseCategoryCRUD;
import crud.CourseGroupCourseCRUD;
import crud.GradeCardCRUD;
import crud.PrerequisiteCRUD;
import crud.ProgramCoordinatorCRUD;
import crud.ProgramSemesterDetailCRUD;
import crud.SemesterCourseCRUD;
import pojo.CourseCategoryPOJO;
import pojo.CourseGroupCoursePOJO;
import pojo.CoursePOJO;
import pojo.GradeCard;
import pojo.PrerequisitePOJO;
import pojo.ProgramSemesterDetailPOJO;
import pojo.SemesterCoursePOJO;

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
				request.setAttribute("course_id", required_course.getId());
				List<GradeCard> gradeCards = (List<GradeCard>) ((Response) new GradeCardCRUD().retrive(request))
						.getData();
				if (gradeCards == null || gradeCards.size() == 0) {
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
		Object temp = request.getAttribute("student_id");
		Integer student_id = null;
		if (temp instanceof String)
			student_id = Integer.valueOf((String) temp);
		else
			student_id = (Integer) request.getAttribute("student_id");
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
		Map<String, String[]> params = request.getParameterMap();
		for (String param : params.keySet()) {
			request.setAttribute(param, request.getParameter(param));
		}
	}

	private static void combinationUtil(List<CoursePOJO> courses, List<CoursePOJO> data, int start, int end, int index,
			int r, List<List<CoursePOJO>> combinations) {
		try {
			if (index == r) {
				List<CoursePOJO> temp = new ArrayList<>();
				for (int j = 0; j < r; j++)
					temp.add(data.get(j));
				combinations.add(temp);
				return;
			}
			for (int i = start; i <= end && end - i + 1 >= r - index; i++) {
				data.set(index, courses.get(i));
				combinationUtil(courses, data, i + 1, end, index + 1, r, combinations);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static List<List<CoursePOJO>> getCombination(List<CoursePOJO> courses, int n, int r) {
		List<CoursePOJO> data = new ArrayList<>(r);
		for (int i = 0; i < r; i++) {
			data.add(null);
		}
		List<List<CoursePOJO>> combinations = new ArrayList<>();
		combinationUtil(courses, data, 0, n - 1, 0, r, combinations);
		return combinations;
	}

	public static Object getSemesterCourseCombinations(HttpServletRequest request, List<CoursePOJO> courses,
			List<CoursePOJO> core_courses) throws IOException {
		int n = courses.size();
		Integer program_id = null, semester_id = null;
		Object temp = request.getAttribute("program_id");
		if (temp instanceof String)
			program_id = Integer.valueOf((String) temp);
		else
			program_id = (Integer) temp;
		temp = request.getAttribute("semester_id");
		if (temp instanceof String)
			semester_id = Integer.valueOf((String) temp);
		else
			semester_id = (Integer) temp;
		request.setAttribute("search", "program_id_semester_id_wise");
		ProgramSemesterDetailPOJO programSemesterDetail = (ProgramSemesterDetailPOJO) ((Response) new ProgramSemesterDetailCRUD()
				.retrive(request)).getData();

		int min_open_elective = programSemesterDetail.getMin_open_electives();
		int min_tech_elective = programSemesterDetail.getMin_tech_electives();
		int max_open_elective = programSemesterDetail.getMax_open_electives();
		int max_tech_elective = programSemesterDetail.getMax_tech_electives();
		int min_open_elective_credits = programSemesterDetail.getMin_open_electives_credits();
		int min_tech_elective_credits = programSemesterDetail.getMin_tech_electives_credits();
		int min_semester_credits = programSemesterDetail.getMin_semester_credits();
		int max_semester_credits = programSemesterDetail.getMax_semester_credits();
		int core_credits = programSemesterDetail.getCore_credits();

		int r = min_open_elective + min_tech_elective;
		int max_r = max_open_elective + max_tech_elective;

		CourseCategoryPOJO tech_elective = null, open_elective = null;

		List<CourseCategoryPOJO> courseCategories = (List<CourseCategoryPOJO>) ((Response) new CourseCategoryCRUD()
				.retrive(request)).getData();

		// finding category code for tech elective and open elective

		for (CourseCategoryPOJO courseCategory : courseCategories) {
			if (courseCategory.getCourse_cat_name().toLowerCase().startsWith("tech")) {
				tech_elective = courseCategory;
			} else if (courseCategory.getCourse_cat_name().toLowerCase().startsWith("open")) {
				open_elective = courseCategory;
			}
			if (tech_elective != null && open_elective != null) {
				break;
			}
		}

		// list of all possible combinations
		ArrayList<Response> valid_combination = new ArrayList<>();
		// finding all possible combinations from r to max_r
		for (int i = r; i <= r; i++) {
			List<List<CoursePOJO>> combinations = getCombination(courses, n, r);
			for (List<CoursePOJO> combination : combinations) {
				// counting tech_electives and open_electives to check whether criteria meets or
				// not
				int tech_electives = 0, open_electives = 0, open_elective_credits = 0, tech_elective_credits = 0;
				for (CoursePOJO course : combination) {
					if (course.getCourse_category().getCourse_cat_name().equals(open_elective.getCourse_cat_name())) {
						open_electives++;
						open_elective_credits += course.getCourse_credits();
					} else {
						tech_electives++;
						tech_elective_credits += course.getCourse_credits();
					}
				}
				if (open_electives >= min_open_elective && tech_electives >= min_tech_elective
						&& open_elective_credits >= min_open_elective_credits
						&& tech_elective_credits >= min_tech_elective_credits
						&& core_credits + open_elective_credits + tech_elective_credits >= min_semester_credits
						&& core_credits + open_elective_credits + tech_elective_credits <= max_semester_credits) {
					String[] labels = new String[combination.size()];
					int[][] data = new int[4][combination.size()];
					int index = 0;
					int eligibility = -10;
					final int CORE = 100;
					final int HIGHLY_RECOMMENDED = 75;
					final int CAN_NOT_SAY = 50;
					final int NOT_RECOMMENDED = 25;
					System.out.println("valid combination");
					// adding core courses to the list so that we can show them in a chart
					for (CoursePOJO course : core_courses) {
						combination.add(course);
					}
					for (CoursePOJO course : combination) {
						labels[index] = course.getCourse_name();
						if (course.getCourse_category().getCourse_cat_name().toLowerCase().startsWith("core")) {
							eligibility = CORE;
						} else {
							request.setAttribute("course_id", course.getId());
							eligibility = (int) GeneralUtility.getCourseEligibility(request);
							if (eligibility == -1) {
								eligibility = NOT_RECOMMENDED;
							} else if (eligibility == 0) {
								eligibility = CAN_NOT_SAY;
							} else if (eligibility == 1) {
								eligibility = HIGHLY_RECOMMENDED;
							}
						}
						data[0][index] = (eligibility == CORE) ? eligibility : 0;
						data[1][index] = (eligibility == CAN_NOT_SAY) ? eligibility : 0;
						data[2][index] = (eligibility == NOT_RECOMMENDED) ? eligibility : 0;
						data[3][index] = (eligibility == HIGHLY_RECOMMENDED) ? eligibility : 0;
						index++;
					}
					Map<String, Object> _response = new HashMap<>();
					_response.put("labels", labels);
					_response.put("data", data);
					valid_combination.add(new Response(GeneralUtility.getRedirect(request), _response));
					System.out.println("added new " + valid_combination.size());
				}
			}
		}
		return new Response(GeneralUtility.getRedirect(request), valid_combination).toString();
	}
}

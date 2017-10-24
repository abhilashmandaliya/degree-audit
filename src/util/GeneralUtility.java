package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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
				System.out.println(roles.toString());
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
}

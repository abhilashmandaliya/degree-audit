package controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import util.GeneralUtility;
import util.ObjectCreator;
import util.Response;

public class Controller extends HttpServlet {
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GeneralUtility.copyParamsToAttributes(request);
		System.setProperty("javax.xml.bind.JAXBContextFactory", "org.eclipse.persistence.jaxb.JAXBContextFactory");
		String theAction = request.getParameter("action");
		System.out.println(theAction);
		Action action = getActionFromConfig(theAction);
		String data = !GeneralUtility.isAutheticatedUser(request)
				? GeneralUtility.generateUnauthorizedResponse().toString()
				: action.perform(request, response);
		Gson json = new Gson();
		Response next = json.fromJson(data, Response.class);
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET,POST");
		response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		if (next.getRedirect() != null) {
			RequestDispatcher rd = request.getRequestDispatcher(next.getRedirect());
			rd.forward(request, response);
		} else {
			response.getWriter().write(data);
		}
	}

	private Action getActionFromConfig(String theAction) throws ServletException, IOException {
		Properties map = new Properties();
		map.load(this.getClass().getClassLoader().getResourceAsStream(ACTION_MAPPING));
		String action_class = map.getProperty(theAction.toLowerCase());
		Action action = (Action) ObjectCreator.createObject(action_class);
		System.out.println(action);
		return action;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	private final static String ACTION_MAPPING = "controller/ActionMapping.properties";
}

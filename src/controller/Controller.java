package controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.ObjectCreator;

public class Controller extends HttpServlet {
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String theAction = request.getParameter("action");

		if (theAction == null)
			theAction = "viewcat";

		Action action = getActionFromConfig(theAction);
		System.out.println(theAction);
		String view = action.perform(request, response);
		response.getWriter().write(view);
		// RequestDispatcher rd = request.getRequestDispatcher(view);
		// rd.forward(request, response);
	}

	private Action getActionFromConfig(String theAction) throws ServletException, IOException {
		Properties map = new Properties();
		map.load(this.getClass().getClassLoader().getResourceAsStream(ACTION_MAPPING));

		String action_class = map.getProperty(theAction.toLowerCase());
		Action action = (Action) ObjectCreator.createObject(action_class);
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

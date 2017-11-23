package controller.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Action;
import util.GeneralUtility;

public class LogoutUser implements Action {

	@Override
	public String perform(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("userCategory");
		session.removeAttribute("user");
		session.invalidate();
		return GeneralUtility.generateSuccessResponse(null, null).toString();
	}

}

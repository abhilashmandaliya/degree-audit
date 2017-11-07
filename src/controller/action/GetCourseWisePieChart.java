package controller.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chart.PieChart;
import controller.Action;
import util.Response;

public class GetCourseWisePieChart implements Action {

	@Override
	public String perform(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		return ((Response) new PieChart().retrive(request)).toString();
	}

}

package chart;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import crud.CRUDCore;
import util.Response;

public class Chart {

	private String[] labels;
	private String[] backgroundColors;
	private Object[] data;

	public Chart() {

	}

	public Chart(String[] labels, String[] backGroundColors, Object[] data) {
		this.backgroundColors = backGroundColors;
		this.labels = labels;
		this.data = data;
	}

	public String[] getLables() {
		return labels;
	}

	public void setLables(String[] labels) {
		this.labels = labels;
	}

	public String[] getBackgroundColors() {
		return backgroundColors;
	}

	public void setBackgroundColors(String[] backgroundColors) {
		this.backgroundColors = backgroundColors;
	}

	public Object[] getData() {
		return data;
	}

	public void setData(Object[] data) {
		this.data = data;
	}

}

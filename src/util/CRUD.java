package util;

import javax.servlet.http.HttpServletRequest;

public interface CRUD {
	public Integer create(HttpServletRequest request);

	public Object retrive(HttpServletRequest request);

	public Integer update(HttpServletRequest request);

	public Integer delete(HttpServletRequest request);
}

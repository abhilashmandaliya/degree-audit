package util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

public interface CRUD {
	public Response create(HttpServletRequest request) throws IOException;

	public Object retrive(HttpServletRequest request) throws IOException;

	public Response update(HttpServletRequest request) throws IOException;

	public Response delete(HttpServletRequest request) throws IOException;
}

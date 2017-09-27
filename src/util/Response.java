package util;

import com.google.gson.Gson;

public class Response {
	private String statusCode;
	private String message;
	private String redirect;
	private Object data;

	public Response() {
	}

	public Response(String redirect, Object data) {
		super();
		this.redirect = redirect;
		this.data = data;
	}

	public Response(String statusCode, String message, String redirect, Object data) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.redirect = redirect;
		this.data = data;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}

package util;

import java.util.Map;

public final class UserRole {
	private String role;
	private String home;
	private Map<String, String> action;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public Map<String, String> getAction() {
		return action;
	}

	public void setAction(Map<String, String> action) {
		this.action = action;
	}

}

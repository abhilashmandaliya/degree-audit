<%@page import="pojo.UserPOJO"%>
<input type="hidden" name="mode" value="api" />
<%
	UserPOJO user = (UserPOJO) session.getAttribute("user");
	if (user != null) {
		out.println("<input type=\"hidden\" name=\"student_id\" value=\"" + user.getId() + "\" />");	
	}
%>
</body>
</html>
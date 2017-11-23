package crud;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.mindrot.jbcrypt.BCrypt;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import pojo.UserCategoryPOJO;
import pojo.UserPOJO;

import util.GeneralUtility;
import util.Response;
import util.UserRole;

public class UserCRUD extends CRUDCore {

	@Override
	public Response create(HttpServletRequest request) throws IOException {
		Integer id = null;
		try {
			String username = request.getParameter("username");
			String password = BCrypt.hashpw(request.getParameter("password"), BCrypt.gensalt(12));
			String first_name = request.getParameter("first_name");
			String last_name = request.getParameter("last_name");
			String email = request.getParameter("email");
			UserCategoryPOJO category = session.get(UserCategoryPOJO.class,
					Integer.parseInt(request.getParameter("category")));
			short is_active = 1;
			UserPOJO user = new UserPOJO(username, password, category, is_active, first_name, last_name, email);
			id = (Integer) session.save(user);
			tx.commit();
			response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), id);
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return response;
	}

	@Override
	public Object retrive(HttpServletRequest request) throws IOException {
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<UserPOJO> criteria = builder.createQuery(UserPOJO.class);
			Root<UserPOJO> userPOJORoot = criteria.from(UserPOJO.class);
			criteria.select(userPOJORoot);
			criteria.where(builder.equal(userPOJORoot.get("username"), username));
			List<UserPOJO> users = session.createQuery(criteria).getResultList();
			if (!users.isEmpty()) {
				UserPOJO userPOJO = users.get(0);
				String hashed = userPOJO.getPassword();
				if (BCrypt.checkpw(password, hashed)) {
					UserCategoryPOJO userCategory = userPOJO.getUserCategory();
					String category = userCategory.getCategory();
					String authJSON = GeneralUtility.readAuthJSON();
					JsonParser parser = new JsonParser();
					JsonArray data = parser.parse(authJSON).getAsJsonArray();
					for (JsonElement element : data) {
						UserRole role = json.fromJson(element, UserRole.class);
						if (role.getRole().equalsIgnoreCase(category)) {
							userPOJO.setPassword("");
							response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request),
									userPOJO);
							break;
						}
					}
//					HttpSession session = request.getSession();
//					session.setAttribute("userCategory", category);
//					session.setAttribute("user", userPOJO);
				}
			}
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return response.toString();
	}

	@Override
	public Response update(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response delete(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}

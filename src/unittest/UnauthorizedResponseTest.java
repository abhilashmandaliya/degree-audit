package unittest;

import org.junit.Test;
import com.google.gson.Gson;
import junit.framework.Assert;
import util.GeneralUtility;
import util.Response;

public class UnauthorizedResponseTest {

	@Test
	public void unauthorizedTest() {
		String unauthorized = "\"{\\\"statusCode\\\":\\\"401\\\",\\\"message\\\":\\\"Unauthorized\\\"}\"";
		Response response = GeneralUtility.generateUnauthorizedResponse();
		String _response = new Gson().toJson(response.toString());
		Assert.assertEquals(_response, unauthorized);
	}

}

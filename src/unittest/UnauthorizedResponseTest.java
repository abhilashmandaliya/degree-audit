package unittest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import junit.framework.Assert;
import util.GeneralUtility;
import util.Response;

class UnauthorizedResponseTest {

	@Test
	void unauthorizedTest() {
		String unauthorized = "\"{\\\"statusCode\\\":\\\"401\\\",\\\"message\\\":\\\"Unauthorized\\\"}\"";
		Response response = GeneralUtility.generateUnauthorizedResponse();
		String _response = new Gson().toJson(response.toString());
		Assert.assertEquals(_response, unauthorized);
	}

}

package unittest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;
import util.GeneralUtility;
import util.Response;

class SuccessResponseTest {

	@Test
	void successResponseTest() {
		String expected = "{\"statusCode\":\"200\",\"message\":\"Action Successful\",\"data\":{\"1\":\"one\",\"2\":\"two\"}}";
		Map<Integer, String> data = new TreeMap();
		data.put(1, "one");
		data.put(2, "two");
		Response response = GeneralUtility.generateSuccessResponse(null, data);
		Assert.assertEquals(response.toString(), expected);
	}

}

package org.broadinstitute.mpg.dcc.translator;

import junit.framework.TestCase;
import org.broadinstitute.mpg.dcc.ReliancepointApplication;
import org.broadinstitute.mpg.dcc.util.DccServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.InputStream;

/**
 * Concrete test class to verify the json translations
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ReliancepointApplication.class)
@WebAppConfiguration
public class DccToIntelJsonTranslatorTest extends TestCase {

    @Test
    public void testGetIntelInputJson() {
        // local variables
        DccToIntelJsonTranslator dccToIntelJsonTranslator = new DccToIntelJsonTranslator();

        // input variables
        InputStream inputDccStream = null;
        JsonReader inputReader = null;
        JsonObject inputObject = null;

        // expected variables
        InputStream expectedStream = null;
        JsonReader expectedReader = null;
        JsonObject expectedObject = null;

        // result variables
        JsonObject resultObject = null;

        // set input variables
        inputDccStream = getClass().getResourceAsStream("/intelFiles/burdenInputPayload.json");
        inputReader = Json.createReader(inputDccStream);
        inputObject = inputReader.readObject();

        // set expected variables
        expectedStream = this.getClass().getResourceAsStream("/intelFiles/intelVariantsPayload.json");
        expectedReader = Json.createReader(expectedStream);
        expectedObject = expectedReader.readObject();

        // get the result
        try {
            resultObject = dccToIntelJsonTranslator.getIntelInputJson(inputObject);

        } catch (DccServiceException exception) {
            fail("Got error translating dcc to intel payload: " + exception.getMessage());
        }

        // test
        assertNotNull(resultObject);
        assertEquals(expectedObject, resultObject);
    }
}

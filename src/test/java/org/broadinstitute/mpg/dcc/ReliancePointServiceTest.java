package org.broadinstitute.mpg.dcc;

import junit.framework.TestCase;
import org.broadinstitute.mpg.dcc.util.DccServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.InputStream;

/**
 * Created by mduby on 7/11/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ReliancepointApplication.class)
@WebAppConfiguration
@PropertySource("classpath:application.properties")
public class ReliancePointServiceTest extends TestCase {
    // instance variables
    @Autowired
    private ReliancePoinService reliancePoinService;

    @Test
    public void testGetBurdenResults() {
        // local variables

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
        inputDccStream = getClass().getResourceAsStream("/intelFiles/intelVariantsPayload.json");
        inputReader = Json.createReader(inputDccStream);
        inputObject = inputReader.readObject();

        // set expected variables
        expectedStream = this.getClass().getResourceAsStream("/intelFiles/burdenDccResults.json");
        expectedReader = Json.createReader(expectedStream);
        expectedObject = expectedReader.readObject();

        // call the service
        try {
            resultObject = this.reliancePoinService.getBurdenResults(inputObject);

        } catch (DccServiceException exception) {
            fail("got error calling service: " + exception.getMessage());
        }

        // test
        assertNotNull(resultObject);
        assertEquals(expectedObject, resultObject);
    }
}

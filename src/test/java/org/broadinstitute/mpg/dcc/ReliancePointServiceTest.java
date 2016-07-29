package org.broadinstitute.mpg.dcc;

import junit.framework.TestCase;
import org.broadinstitute.mpg.dcc.bean.RestResultBean;
import org.broadinstitute.mpg.dcc.bean.VariantResultBean;
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
 * Unit test for the Reliance Point service class
 *
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
        VariantResultBean expectedBean = null;
        RestResultBean expectedRestResultBean = null;

        // result variables
        RestResultBean restResultBean = null;

        // set input variables
        inputDccStream = getClass().getResourceAsStream("/intelFiles/intelVariantsPayload.json");
        inputReader = Json.createReader(inputDccStream);
        inputObject = inputReader.readObject();

        // get the expected results
        expectedBean = new VariantResultBean();
        expectedBean.setNumCases(109);
        expectedBean.setNumControls(86);
        expectedBean.setNumCaseCarriers(1);
        expectedBean.setNumControlCarriers(1);
        expectedBean.setNumCaseVariants(1);
        expectedBean.setNumControlVariants(1);
        expectedBean.setNumInputVariants(10);
        expectedBean.setpValue(0.4699);
        expectedBean.setBeta(-1.0748);
        expectedBean.setStdError(1.4872);
        expectedBean.setCiLevel(0.95);
        expectedBean.setCiLower(-3.989712);
        expectedBean.setCiUpper(1.8401120000000002);
        expectedRestResultBean = new RestResultBean();
        expectedRestResultBean.setStats(expectedBean);

        // call the service
        try {
            restResultBean = this.reliancePoinService.getBurdenResults(inputObject);

        } catch (DccServiceException exception) {
            fail("got error calling service: " + exception.getMessage());
        }

        // test
        assertNotNull(restResultBean);
        assertEquals(expectedRestResultBean.getErrorMessage(), restResultBean.getErrorMessage());
        assertEquals(expectedRestResultBean.isError(), restResultBean.isError());
        assertNotNull(restResultBean.getStats());
        assertEquals(expectedRestResultBean.getStats().getNumCases(), restResultBean.getStats().getNumCases());
        assertEquals(expectedRestResultBean.getStats().getNumControls(), restResultBean.getStats().getNumControls());
        assertEquals(expectedRestResultBean.getStats().getNumCaseCarriers(), restResultBean.getStats().getNumCaseCarriers());
        assertEquals(expectedRestResultBean.getStats().getNumControlCarriers(), restResultBean.getStats().getNumControlCarriers());
        assertEquals(expectedRestResultBean.getStats().getNumCaseVariants(), restResultBean.getStats().getNumCaseVariants());
        assertEquals(expectedRestResultBean.getStats().getNumControlVariants(), restResultBean.getStats().getNumControlVariants());
        assertEquals(expectedRestResultBean.getStats().getNumInputVariants(), restResultBean.getStats().getNumInputVariants());
        assertEquals(expectedRestResultBean.getStats().getpValue(), restResultBean.getStats().getpValue());
        assertEquals(expectedRestResultBean.getStats().getBeta(), restResultBean.getStats().getBeta());
        assertEquals(expectedRestResultBean.getStats().getStdError(), restResultBean.getStats().getStdError());
        assertEquals(expectedRestResultBean.getStats().getCiLevel(), restResultBean.getStats().getCiLevel());
        assertEquals(expectedRestResultBean.getStats().getCiLower(), restResultBean.getStats().getCiLower());
        assertEquals(expectedRestResultBean.getStats().getCiUpper(), restResultBean.getStats().getCiUpper());
    }
}

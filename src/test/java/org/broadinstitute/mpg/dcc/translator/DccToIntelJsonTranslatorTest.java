package org.broadinstitute.mpg.dcc.translator;

import junit.framework.TestCase;
import org.broadinstitute.mpg.dcc.ReliancepointApplication;
import org.broadinstitute.mpg.dcc.bean.RestResultBean;
import org.broadinstitute.mpg.dcc.bean.VariantResultBean;
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
import java.util.HashMap;
import java.util.Map;

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

    @Test
    public void testGetBurdenResultFromStream() {
        // local variables
        DccToIntelJsonTranslator dccToIntelJsonTranslator = new DccToIntelJsonTranslator();

        // input variables
        InputStream inputStream = null;

        // expected variables
        VariantResultBean expectedBean = null;
        RestResultBean expectedRestResultBean = null;

        // result variables
        RestResultBean restResultBean = null;

        // get the inputs
        inputStream = this.getClass().getResourceAsStream("/intelFiles/intelResults.txt");
        assertNotNull(inputStream);

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
        expectedRestResultBean.addToResults(expectedBean);

        // get the result
        try {
            restResultBean = dccToIntelJsonTranslator.getBurdenResultBeanFromStream(inputStream);

        } catch (DccServiceException exception) {
            fail("Got exception reading burden file: " + exception.getMessage());
        }

        // test
        assertNotNull(restResultBean);
        assertEquals(expectedRestResultBean.getErrorMessage(), restResultBean.getErrorMessage());
        assertEquals(expectedRestResultBean.isError(), restResultBean.isError());
        assertTrue(restResultBean.getResults().size() == 1);
        assertEquals(expectedRestResultBean.getResults().get(0).getNumCases(), restResultBean.getResults().get(0).getNumCases());
        assertEquals(expectedRestResultBean.getResults().get(0).getNumControls(), restResultBean.getResults().get(0).getNumControls());
        assertEquals(expectedRestResultBean.getResults().get(0).getNumCaseCarriers(), restResultBean.getResults().get(0).getNumCaseCarriers());
        assertEquals(expectedRestResultBean.getResults().get(0).getNumControlCarriers(), restResultBean.getResults().get(0).getNumControlCarriers());
        assertEquals(expectedRestResultBean.getResults().get(0).getNumCaseVariants(), restResultBean.getResults().get(0).getNumCaseVariants());
        assertEquals(expectedRestResultBean.getResults().get(0).getNumControlVariants(), restResultBean.getResults().get(0).getNumControlVariants());
        assertEquals(expectedRestResultBean.getResults().get(0).getNumInputVariants(), restResultBean.getResults().get(0).getNumInputVariants());
        assertEquals(expectedRestResultBean.getResults().get(0).getpValue(), restResultBean.getResults().get(0).getpValue());
        assertEquals(expectedRestResultBean.getResults().get(0).getBeta(), restResultBean.getResults().get(0).getBeta());
        assertEquals(expectedRestResultBean.getResults().get(0).getStdError(), restResultBean.getResults().get(0).getStdError());
        assertEquals(expectedRestResultBean.getResults().get(0).getCiLevel(), restResultBean.getResults().get(0).getCiLevel());
        assertEquals(expectedRestResultBean.getResults().get(0).getCiLower(), restResultBean.getResults().get(0).getCiLower());
        assertEquals(expectedRestResultBean.getResults().get(0).getCiUpper(), restResultBean.getResults().get(0).getCiUpper());

    }

    /**
     * method to test the safe double accessor method
     *
     */
    @Test
    public void testGetSafeDoubleValue() {
        // local variables
        double resultDouble = -1;
        Map<String, String> tempMap = new HashMap<String, String>();
        String key = "key";
        DccToIntelJsonTranslator dccToIntelJsonTranslator = new DccToIntelJsonTranslator();

        // test for null entry
        resultDouble = dccToIntelJsonTranslator.getSafeDoubleValue(tempMap, key);
        assertEquals(0.0, resultDouble);

        // test for badly formatted number
        tempMap.put(key, "29292hht");
        resultDouble = dccToIntelJsonTranslator.getSafeDoubleValue(tempMap, key);
        assertEquals(0.0, resultDouble);

        // test for real number
        tempMap.put(key, "12.345");
        resultDouble = dccToIntelJsonTranslator.getSafeDoubleValue(tempMap, key);
        assertEquals(12.345, resultDouble);
    }

    /**
     * method to test the safe double accessor method
     *
     */
    @Test
    public void testGetSafeIntegerValue() {
        // local variables
        int resultInteger = -1;
        Map<String, String> tempMap = new HashMap<String, String>();
        String key = "key";
        DccToIntelJsonTranslator dccToIntelJsonTranslator = new DccToIntelJsonTranslator();

        // test for null entry
        resultInteger = dccToIntelJsonTranslator.getSafeIntegerValue(tempMap, key);
        assertEquals(0, resultInteger);

        // test for badly formatted number
        tempMap.put(key, "29292hht");
        resultInteger = dccToIntelJsonTranslator.getSafeIntegerValue(tempMap, key);
        assertEquals(0, resultInteger);

        // test for real number
        tempMap.put(key, "12345");
        resultInteger = dccToIntelJsonTranslator.getSafeIntegerValue(tempMap, key);
        assertEquals(12345, resultInteger);
    }
}

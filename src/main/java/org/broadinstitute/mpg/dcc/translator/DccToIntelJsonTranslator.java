package org.broadinstitute.mpg.dcc.translator;

import org.broadinstitute.mpg.dcc.bean.RestResultBean;
import org.broadinstitute.mpg.dcc.bean.VariantResultBean;
import org.broadinstitute.mpg.dcc.util.DccServiceConstants;
import org.broadinstitute.mpg.dcc.util.DccServiceException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Concrete class to translate from the DCC json format to the Intel format
 *
 */
public class DccToIntelJsonTranslator {
    // instance variables
    Logger translatorLogger = Logger.getLogger(this.getClass().getName());

    /**
     * translate from the dcc burden input format to the Intel burden input format
     *
     * @param dccInputObject
     * @return
     * @throws DccServiceException
     */
    public JsonObject getIntelInputJson(JsonObject dccInputObject) throws DccServiceException {
        // local variables
        JsonObject intellJsonObject = null;
        JsonObjectBuilder objectBuilder = null;
        JsonArrayBuilder arrayBuilder = null;

        // check the input
        if (dccInputObject == null) {
            throw new DccServiceException("Got null DCC input object for the burden test");
        }

        // get the variants collection
        if (dccInputObject.containsKey(DccServiceConstants.Json.VARIANTS_KEY)) {
            // pull out the variants object
            JsonValue tempValue = dccInputObject.get(DccServiceConstants.Json.VARIANTS_KEY);
            if (tempValue.getValueType() == JsonValue.ValueType.ARRAY) {
                // create a json object and add the array
                objectBuilder = Json.createObjectBuilder();
                objectBuilder.add(DccServiceConstants.Json.VARIANTS_KEY, dccInputObject.get(DccServiceConstants.Json.VARIANTS_KEY));
                intellJsonObject = objectBuilder.build();

            } else {
                throw new DccServiceException("Got incorrect json type for variants array: " + dccInputObject.get(DccServiceConstants.Json.VARIANTS_KEY) + " with type: " + dccInputObject.get(DccServiceConstants.Json.VARIANTS_KEY).getValueType());
            }

        } else {
            // create the builders
            objectBuilder = Json.createObjectBuilder();
            arrayBuilder = Json.createArrayBuilder();

            // create a variants collection
            objectBuilder.add(DccServiceConstants.Json.VARIANTS_KEY, arrayBuilder.build());

            // create the root object
            intellJsonObject = objectBuilder.build();
        }

        // return
        return intellJsonObject;
    }

    /**
     * returns a json object based on a input stream consisting of lines with fomat field=value
     * @param inputStream
     * @return
     * @throws DccServiceException
     */
    public RestResultBean getBurdenResultBeanFromStream(InputStream inputStream) throws DccServiceException {
        // local variables
        JsonObject jsonObject = null;
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        Map<String, String> stringMap = null;
        Iterator<String> keyIterator = null;
        RestResultBean restResultBean = new RestResultBean();
        VariantResultBean variantResultBean = new VariantResultBean();

        // get the string map
        stringMap = this.getBurdenResultsMapFromStream(inputStream);

        // build the json object
        keyIterator = stringMap.keySet().iterator();

        while (keyIterator.hasNext()) {
            String key = keyIterator.next();

            // add to the json object
            objectBuilder.add(key, stringMap.get(key));
        }

        // populate the variant result
        variantResultBean.setNumCases(this.getSafeIntegerValue(stringMap, DccServiceConstants.Json.NUMBER_CASES_KEY));
        variantResultBean.setNumControls(this.getSafeIntegerValue(stringMap, DccServiceConstants.Json.NUMBER_CONTROLS_KEY));
        variantResultBean.setNumCaseCarriers(this.getSafeIntegerValue(stringMap, DccServiceConstants.Json.NUMBER_CASE_CARRIERS_KEY));
        variantResultBean.setNumControlCarriers(this.getSafeIntegerValue(stringMap, DccServiceConstants.Json.NUMBER_CONTROL_CARRIERS_KEY));
        variantResultBean.setNumCaseVariants(this.getSafeIntegerValue(stringMap, DccServiceConstants.Json.NUMBER_CASE_VARIANTS_KEY));
        variantResultBean.setNumControlVariants(this.getSafeIntegerValue(stringMap, DccServiceConstants.Json.NUMBER_CONTROL_VARIANTS_KEY));
        variantResultBean.setNumInputVariants(this.getSafeIntegerValue(stringMap, DccServiceConstants.Json.NUMBER_INPUT_VARIANTS_KEY));
        variantResultBean.setpValue(this.getSafeDoubleValue(stringMap, DccServiceConstants.Json.P_VALUE_KEY));
        variantResultBean.setBeta(this.getSafeDoubleValue(stringMap, DccServiceConstants.Json.BETA_KEY));
        variantResultBean.setStdError(this.getSafeDoubleValue(stringMap, DccServiceConstants.Json.STANDARD_ERROR__KEY));
        variantResultBean.setCiLevel(this.getSafeDoubleValue(stringMap, DccServiceConstants.Json.CI_LEVEL_KEY));
        variantResultBean.setCiLower(this.getSafeDoubleValue(stringMap, DccServiceConstants.Json.CI_LOWER_KEY));
        variantResultBean.setCiUpper(this.getSafeDoubleValue(stringMap, DccServiceConstants.Json.CI_UPPER_KEY));

        // add the variant bean
        restResultBean.setStats(variantResultBean);

        // return
        return restResultBean;
    }

    /**
     * method to return a converted float value from a given string; 0.0 returned if not convertable
     *
     * @param map
     * @param key
     * @return
     */
    protected double getSafeDoubleValue(Map<String, String> map, String key) {
        // local variables
        double value = 0.0;
        String mapValue = null;

        // get the value
        mapValue = map.get(key);

        if (mapValue != null) {
            try {
                value = Double.valueOf(map.get(key));

            } catch (NumberFormatException exception) {
                // do nothing
            }
        }

        // return
        return value;
    }

    /**
     * method to return a converted float value from a given string; 0.0 returned if not convertable
     *
     * @param map
     * @param key
     * @return
     */
    protected int getSafeIntegerValue(Map<String, String> map, String key) {
        // local variables
        int value = 0;
        String mapValue = null;

        // get the value
        mapValue = map.get(key);

        if (mapValue != null) {
            try {
                value = Integer.valueOf(map.get(key));

            } catch (NumberFormatException exception) {
                // do nothing
            }
        }

        // return
        return value;
    }

    /**
     * translates the burden reslts file intp a map
     *
     * @param inputStream
     * @return
     * @throws DccServiceException
     */
    protected Map<String, String> getBurdenResultsMapFromStream(InputStream inputStream) throws DccServiceException {
        // local variables
        BufferedReader stringReader = null;
        InputStreamReader inputStreamReader = null;
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        String inputLineString = null;
        Map<String, String> stringMap = new HashMap<String, String>();

        // test the input stream
        if (inputStream == null) {
            throw new DccServiceException("Got null burden result input stream");
        }

        // parse the stream with a line reader
        inputStreamReader = new InputStreamReader(inputStream);
        stringReader = new BufferedReader(inputStreamReader);

        // for each line
        try {
            while ((inputLineString = stringReader.readLine()) != null) {
                // check line
                if (inputLineString == null) {
                    this.translatorLogger.info("got null line in burden result, so skipping");
                    continue;
                }

                // parse using the = sign
                String[] stringArray = null;
                stringArray = inputLineString.split("=");

                // check array for 2 elements
                if (stringArray == null) {
                    this.translatorLogger.info("got null string array in burden result, so skipping");
                    continue;

                } else if (stringArray.length < 2) {
                    this.translatorLogger.info("got incorrect line in burden result, so skipping: " + inputLineString);
                    continue;

                }

                // add to the map
                stringMap.put(stringArray[0], stringArray[1]);
            }

        } catch (IOException exception) {
            throw new DccServiceException("Got IO exception with burden stream: " + exception.getMessage());
        }

        // return the map
        return stringMap;
    }
}

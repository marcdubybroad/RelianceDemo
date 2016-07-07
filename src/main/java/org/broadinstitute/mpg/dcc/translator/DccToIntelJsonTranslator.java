package org.broadinstitute.mpg.dcc.translator;

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

        // TODO - translate the variant strings

        // return
        return intellJsonObject;
    }

    /**
     * returns a json object based on a input stream consisting of lines with fomat field=value
     * @param inputStream
     * @return
     * @throws DccServiceException
     */
    public JsonObject getBurdenResultFromStream(InputStream inputStream) throws DccServiceException {
        // local variables
        JsonObject jsonObject = null;
        BufferedReader stringReader = null;
        InputStreamReader inputStreamReader = null;
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        String inputLineString = null;

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

                // add to the json object
                objectBuilder.add(stringArray[0], stringArray[1]);
            }

            // get the json object results
            jsonObject = objectBuilder.build();

        } catch (IOException exception) {
            throw new DccServiceException("Got IO exception with burden stream: " + exception.getMessage());
        }

        // build the encompassing json object
        objectBuilder.add("stats", jsonObject);
        objectBuilder.add("is_error", false);
        objectBuilder.add("error_message", JsonValue.NULL);
        jsonObject = objectBuilder.build();

        // return
        return jsonObject;
    }

}

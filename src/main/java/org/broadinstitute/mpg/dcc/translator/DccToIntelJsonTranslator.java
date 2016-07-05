package org.broadinstitute.mpg.dcc.translator;

import org.broadinstitute.mpg.dcc.util.DccServiceConstants;
import org.broadinstitute.mpg.dcc.util.DccServiceException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * Concrete class to translate from the DCC json format to the Intel format
 *
 */
public class DccToIntelJsonTranslator {
    // instance variables

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

}

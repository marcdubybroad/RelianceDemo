package org.broadinstitute.mpg.dcc;

import org.apache.log4j.Logger;
import org.broadinstitute.mpg.dcc.io.ReliancePointFileAccessor;
import org.broadinstitute.mpg.dcc.translator.DccToIntelJsonTranslator;
import org.broadinstitute.mpg.dcc.util.DccServiceException;

import javax.json.JsonObject;
import java.io.InputStream;
import java.util.Date;

/**
 * Concreate service class to run the burdenn test script with the given inputs and retrieve the outputs
 *
 * Created by mduby on 7/8/16.
 */
public class ReliancePoinService {
    // instance variables
    Logger serviceLogger = Logger.getLogger(this.getClass().getName());
    DccToIntelJsonTranslator dccToIntelJsonTranslator = new DccToIntelJsonTranslator();

    // paths and scripts
    String scriptPath = "/Users/mduby/Apps/WorkspaceIntelliJ/dig-diabetes-portal/RelianceDemo/src/main/python/mockScript.py";

    /**
     * process the burden result
     *
     * @param inputObject
     * @return
     * @throws DccServiceException
     */
    public JsonObject getBurdenResults(JsonObject inputObject) throws DccServiceException {
        // local variables
        ReliancePointFileAccessor reliancePointFileAccessor = null;
        JsonObject resultObject = null;
        JsonObject burdenInputJson = null;
        Date now = new Date();
        InputStream resultStream = null;
        String[] filePathArray = null;

        // make sure intputs ok
        if (inputObject == null) {
            throw new DccServiceException("Got null burden input json file");
        }

        // get the input json for the burden test converted from the DCC format
        burdenInputJson = this.dccToIntelJsonTranslator.getIntelInputJson(inputObject);

        // create the new directory and input file
        reliancePointFileAccessor = new ReliancePointFileAccessor("/Users/mduby/Scratch/Burden", now.getTime());
        filePathArray = reliancePointFileAccessor.writeVariantFile(burdenInputJson);

        // run the process
        this.runScript(this.scriptPath, filePathArray[0], filePathArray[1]);

        // get the results file stream
        resultStream = reliancePointFileAccessor.readFile(filePathArray[1]);

        // convert the file to a DCC result json file
        resultObject = this.dccToIntelJsonTranslator.getBurdenResultFromStream(resultStream);

        // return
        return resultObject;
    }

    /**
     * run the burden script
     *
     * @param scriptPath
     * @param inputFilePath
     * @param outputFilePath
     * @throws DccServiceException
     */
    protected void runScript(String scriptPath, String inputFilePath, String outputFilePath) throws DccServiceException {
        // local variables
        String target = scriptPath + " " + inputFilePath + " " + outputFilePath;

        // log
        this.serviceLogger.info("Running command: '" + target + "'");

        // run the process
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(target);

        } catch (Throwable throwable) {
            this.serviceLogger.error("Got exception running burden test: " + throwable.getMessage());
            throw new DccServiceException("Got error running burden process: " + throwable.getMessage());
        }
    }

}

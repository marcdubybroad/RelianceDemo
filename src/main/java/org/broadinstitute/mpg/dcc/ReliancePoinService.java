package org.broadinstitute.mpg.dcc;

import org.broadinstitute.mpg.dcc.translator.DccToIntelJsonTranslator;
import org.broadinstitute.mpg.dcc.util.DccServiceException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.logging.Logger;

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
        JsonObject resultObject = null;
        JsonObject burdenInputJson = null;
        Date now = new Date();
        String resultsDirectoryPath = null;
        FileWriter fileWriter = null;
        File resultDirectory = null;
        JsonWriter jsonWriter = null;
        String resultFilePath = null;
        String inputFilePath = null;
        InputStream resultStream = null;

        // make sure intputs ok
        if (inputObject == null) {
            throw new DccServiceException("Got null burden input json file");
        }

        // get the input json for the burden test converted from the DCC format
        burdenInputJson = this.dccToIntelJsonTranslator.getIntelInputJson(inputObject);

        // create the new directory and input file
        resultsDirectoryPath = "BurdenResults" + now.getTime();
        resultDirectory = this.createDirectory(resultsDirectoryPath);
        inputFilePath = resultDirectory.getAbsolutePath() + "/variants.json";

        try {
            Json.createWriter(new FileWriter(inputFilePath));

        } catch (IOException exception) {
            throw new DccServiceException("Got variants.json file writing exception: " + exception.getMessage());
        }

        // write the input file
        jsonWriter.writeObject(burdenInputJson);

        // get the results filepath
        resultFilePath = resultsDirectoryPath + "/results.txt";

        // run the process
        this.runScript(this.scriptPath, inputFilePath, resultFilePath);

        // read the new results file
        try {
            resultStream = new FileInputStream(resultFilePath);

        } catch (IOException exception) {
            throw new DccServiceException("Got error reading burden results file: " + exception.getMessage());
        }

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

        // run the process
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(target);

        } catch (Throwable throwable) {
            this.serviceLogger.info("Got exception running burden test: " + throwable.getMessage());
            throw new DccServiceException("Got error running burden process: " + throwable.getMessage());
        }
    }

    /**
     * create the directory at the given path
     *
     * @param pathString
     * @return
     * @throws DccServiceException
     */
    protected File createDirectory(String pathString) throws DccServiceException {
        // local variables
        File file = new File(pathString);

        // check for the directory
        if (!file.exists()) {
            // create if not there
            if (file.mkdir()) {
                this.serviceLogger.info("Directory is created at path: " + file.getAbsolutePath());

            } else {
                throw new DccServiceException("Directory exists, so failed to create directory at path: " + pathString);
            }
        }

        // return
        return file;
    }
}

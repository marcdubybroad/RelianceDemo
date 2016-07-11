package org.broadinstitute.mpg.dcc.io;

import org.apache.log4j.Logger;
import org.broadinstitute.mpg.dcc.util.DccServiceException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

/**
 * Concrete class to manage writing and reading files
 *
 * Created by mduby on 7/9/16.
 */
public class ReliancePointFileAccessor {
    // instance variables
    private Logger fileLogger = Logger.getLogger(this.getClass().getName());
    String fileDirectoryPath = null;

    /**
     * default constructor
     *
     * @param rootDirectoryPath
     */
    public ReliancePointFileAccessor(String rootDirectoryPath, long time) {
        this.fileDirectoryPath = rootDirectoryPath + "/test" + time;
    }

    /**
     * create the directory at the given path
     *
     * @param directoryPathString
     * @return
     * @throws DccServiceException
     */
    protected File createDirectory(String directoryPathString) throws DccServiceException {
        // local variables
        File file = new File(directoryPathString);

        // check for the directory
        if (!file.exists()) {
            // create if not there
            if (file.mkdirs()) {
                this.fileLogger.info("Directory is created at path: " + file.getAbsolutePath());

            } else {
                throw new DccServiceException("Directory exists, so failed to create directory at path: " + directoryPathString);
            }
        }

        // return
        return file;
    }

    /**
     * write out the variant json file for the process
     *
     * @throws DccServiceException
     */
    public String[] writeVariantFile(JsonObject burdenInputJson) throws DccServiceException {
        // local variables
        File resultDirectory = null;
        String inputFilePath = null;
        String resultFilePath = null;
        JsonWriter jsonWriter = null;
        String[] filePathStringArray = new String[2];

        // create the new directory and input file
        resultDirectory = this.createDirectory(this.fileDirectoryPath);
        inputFilePath = resultDirectory.getAbsolutePath() + "/variants.json";
        resultFilePath = resultDirectory.getAbsolutePath() + "/results.txt";

        try {
            jsonWriter = Json.createWriter(new FileWriter(inputFilePath));

        } catch (IOException exception) {
            throw new DccServiceException("Got variants.json file writing exception: " + exception.getMessage());
        }

        // write the input file
        jsonWriter.writeObject(burdenInputJson);

        // create the string array
        filePathStringArray[0] = inputFilePath;
        filePathStringArray[1] = resultFilePath;

        // return
        return filePathStringArray;
    }

    /**
     * retrieve the results file stream
     *
     * @return
     * @throws DccServiceException
     */
    public InputStream readFile(String filePath) throws DccServiceException {
        // local variables
        InputStream resultStream = null;

        // read the new results file
        try {
            resultStream = new FileInputStream(filePath);

        } catch (IOException exception) {
            throw new DccServiceException("Got error reading burden results file: " + exception.getMessage());
        }

        // return
        return resultStream;
    }
}

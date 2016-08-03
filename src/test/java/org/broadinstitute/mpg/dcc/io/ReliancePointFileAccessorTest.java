package org.broadinstitute.mpg.dcc.io;

import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.broadinstitute.mpg.dcc.ReliancepointApplication;
import org.broadinstitute.mpg.dcc.util.DccServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

/**
 * Created by mduby on 7/11/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ReliancepointApplication.class)
@WebAppConfiguration
public class ReliancePointFileAccessorTest extends TestCase {
    // instance variables
    Logger testLog = Logger.getLogger(this.getClass().getName());

    // spring properties
    @Value("${server.out.results.root.directory}")
    private String rootResultsDirectoryPath;

    @Test
    public void testCreateDirectory() {
        // instance variables
        Date now = new Date();
        ReliancePointFileAccessor reliancePointFileAccessor = new ReliancePointFileAccessor(rootResultsDirectoryPath, now.getTime());
        File directory = null;

        // create the directory
        try {
            directory = reliancePointFileAccessor.createDirectory(rootResultsDirectoryPath + "/test" + now.getTime());

        } catch (DccServiceException exception) {
            fail("got error creating directory: " + exception.getMessage());
        }

        // test
        assertNotNull(directory);
        assertTrue(directory.isDirectory());
        assertTrue(directory.canWrite());
        assertTrue(directory.getAbsolutePath().length() > 10);

        // log
        this.testLog.info("created directory: " + directory.getAbsolutePath());
    }

    @Test
    public void testwriteVariantFile() {
        // local variables
        Date now = new Date();
        ReliancePointFileAccessor reliancePointFileAccessor = new ReliancePointFileAccessor(rootResultsDirectoryPath, now.getTime());
        InputStream expectedStream = null;
        JsonReader expectedReader = null;
        JsonObject expectedObject = null;
        InputStream resultStream = null;
        JsonReader resultReader = null;
        JsonObject resultObject = null;
        String[] resultArray = null;

        // load the expected file results
        expectedStream = this.getClass().getResourceAsStream("/intelFiles/variants.json");
        assertNotNull(expectedStream);
        expectedReader = Json.createReader(expectedStream);
        expectedObject = expectedReader.readObject();
        assertNotNull(expectedObject);

        // write out the file
        try {
            resultArray = reliancePointFileAccessor.writeVariantFile(expectedObject);

        } catch (DccServiceException exception) {
            fail("Got variant file writing error: " + exception.getMessage());
        }

        // test
        assertNotNull(resultArray);
        assertEquals(2, resultArray.length);
        assertNotNull(resultArray[0]);
        assertNotNull(resultArray[1]);
        assertTrue(resultArray[0].length() > 0);
        assertTrue(resultArray[1].length() > 0);

        // load result file
        try {
            resultStream = new FileInputStream(resultArray[0]);

        } catch (FileNotFoundException exception) {
            fail("got error reading json file: " + exception.getMessage());
        }
//        assertNotNull("/Users/mduby/Scratch/Burden/test1470256036181/variants.json");
        assertNotNull(resultStream);
        resultReader = Json.createReader(resultStream);
        resultObject = resultReader.readObject();

        // compare
        assertNotNull(resultObject);
        assertEquals(expectedObject.toString(), resultObject.toString());
    }
}

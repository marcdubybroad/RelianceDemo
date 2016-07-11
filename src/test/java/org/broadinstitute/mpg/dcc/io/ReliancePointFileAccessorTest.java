package org.broadinstitute.mpg.dcc.io;

import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.broadinstitute.mpg.dcc.ReliancepointApplication;
import org.broadinstitute.mpg.dcc.util.DccServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
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

    @Test
    public void testCreateDirectory() {
        // instance variables
        Date now = new Date();
        ReliancePointFileAccessor reliancePointFileAccessor = new ReliancePointFileAccessor("/Users/mduby/Scratch/Intel/test", now.getTime());
        File directory = null;

        // create the directory
        try {
            directory = reliancePointFileAccessor.createDirectory("/Users/mduby/Scratch/Intel/test/" + now.getTime());

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
}

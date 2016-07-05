package org.broadinstitute.mpg.dcc.util;

/**
 * Concrete class to represent a DCC service exception
 *
 */
public class DccServiceException extends Exception {
    public DccServiceException() {
        super();
    }

    public DccServiceException(String message) {
        super(message);
    }
}

package org.broadinstitute.mpg.dcc.bean;

import org.broadinstitute.mpg.dcc.util.DccServiceException;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete bean class to encapsulate the burden input for the Intel Reliance Point call
 *
 */
public class IntelInputBean {
    // instance variables
    private List<String> variants;

    /**
     * add to the variant list
     *
     * @param variantString
     */
    public void addToVariants(String variantString) throws DccServiceException {
        // check input
        if (variantString == null) {
            throw new DccServiceException("Got null variant to add to the Inptel result bean");
        }

        // if collection null, initialize
        if (this.variants == null) {
            this.variants = new ArrayList<String>();
        }

        // add to collection
        this.variants.add(variantString);
    }

    public List<String> getVariants() {
        return variants;
    }
}

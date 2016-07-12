package org.broadinstitute.mpg.dcc.bean;

/**
 * Concrete bean class to contain the burden results
 *
 */
public class VariantResultBean {
    // istance variables
    private int numCases;
    private int numControls;
    private int numCaseCarriers;
    private int numControlCarriers;
    private int numCaseVariants;
    private int numControlVariants;
    private int numInputVariants;
    private double pValue;
    private double beta;
    private double stdError;
    private double ciLevel;
    private double ciLower;
    private double ciUpper;

    public int getNumCases() {
        return numCases;
    }

    public void setNumCases(int numCases) {
        this.numCases = numCases;
    }

    public int getNumControls() {
        return numControls;
    }

    public void setNumControls(int numControls) {
        this.numControls = numControls;
    }

    public int getNumCaseCarriers() {
        return numCaseCarriers;
    }

    public void setNumCaseCarriers(int numCaseCarriers) {
        this.numCaseCarriers = numCaseCarriers;
    }

    public int getNumControlCarriers() {
        return numControlCarriers;
    }

    public void setNumControlCarriers(int numControlCarriers) {
        this.numControlCarriers = numControlCarriers;
    }

    public int getNumCaseVariants() {
        return numCaseVariants;
    }

    public void setNumCaseVariants(int numCaseVariants) {
        this.numCaseVariants = numCaseVariants;
    }

    public int getNumControlVariants() {
        return numControlVariants;
    }

    public void setNumControlVariants(int numControlVariants) {
        this.numControlVariants = numControlVariants;
    }

    public int getNumInputVariants() {
        return numInputVariants;
    }

    public void setNumInputVariants(int numInputVariants) {
        this.numInputVariants = numInputVariants;
    }

    public double getpValue() {
        return pValue;
    }

    public void setpValue(double pValue) {
        this.pValue = pValue;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getStdError() {
        return stdError;
    }

    public void setStdError(double stdError) {
        this.stdError = stdError;
    }

    public double getCiLevel() {
        return ciLevel;
    }

    public void setCiLevel(double ciLevel) {
        this.ciLevel = ciLevel;
    }

    public double getCiLower() {
        return ciLower;
    }

    public void setCiLower(double ciLower) {
        this.ciLower = ciLower;
    }

    public double getCiUpper() {
        return ciUpper;
    }

    public void setCiUpper(double ciUpper) {
        this.ciUpper = ciUpper;
    }

    public boolean equals(Object object) {
        // local variables
        VariantResultBean otherBean = (VariantResultBean)object;

        // test
        if (this.getNumCases() != otherBean.getNumCases()) {
            return false;
        }
        if (this.getNumControls() != otherBean.getNumControls()) {
            return false;
        }
        if (this.getNumCaseCarriers() != otherBean.getNumCaseCarriers()) {
            return false;
        }
        if (this.getNumControlCarriers() != otherBean.getNumControlCarriers()) {
            return false;
        }
        if (this.getNumCaseVariants() != otherBean.getNumCaseVariants()) {
            return false;
        }
        if (this.getNumControlVariants() != otherBean.getNumControlVariants()) {
            return false;
        }
        if (this.getNumInputVariants() != otherBean.getNumInputVariants()) {
            return false;
        }
        if (this.getpValue() != otherBean.getpValue()) {
            return false;
        }
        if (this.getBeta() != otherBean.getBeta()) {
            return false;
        }
        if (this.getStdError() != otherBean.getStdError()) {
            return false;
        }
        if (this.getCiLevel() != otherBean.getCiLevel()) {
            return false;
        }
        if (this.getCiLower() != otherBean.getCiLower()) {
            return false;
        }
        if (this.getCiUpper() != otherBean.getCiUpper()) {
            return false;
        }

        // if all pass, true
        return true;
    }
}

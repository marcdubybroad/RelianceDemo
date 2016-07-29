package org.broadinstitute.mpg.dcc.bean;

/**
 * Bean to house the burden test results
 *
 * Created by mduby on 7/3/16.
 */
public class RestResultBean {
    // instance variables
    private boolean error;
    private String errorMessage;
    private VariantResultBean stats;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public VariantResultBean getStats() {
        return stats;
    }

    public void setStats(VariantResultBean variantBean) {
        this.stats = variantBean;
    }
}

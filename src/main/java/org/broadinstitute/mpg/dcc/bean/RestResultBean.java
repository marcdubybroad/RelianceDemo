package org.broadinstitute.mpg.dcc.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mduby on 7/3/16.
 */
public class RestResultBean {
    // instance variables
    private boolean error;
    private String errorMessage;
    private List<VariantResultBean> stats;

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

    public List<VariantResultBean> getStats() {
        return stats;
    }

    public void addToResults(VariantResultBean bean) {
        if (this.stats == null) {
            this.stats = new ArrayList<VariantResultBean>();
        }

        // add the bean
        this.stats.add(bean);
    }
}

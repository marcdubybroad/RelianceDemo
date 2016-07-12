package org.broadinstitute.mpg.dcc.util;

/**
 * Concrete class to hold service constants
 *
 */
public class DccServiceConstants {
    // json constants
    public static final class Json {
        public static final String VARIANTS_KEY =               "variants";

        // constants for the burden results
        public static final String NUMBER_CASES_KEY =               "numCases";
        public static final String NUMBER_CONTROLS_KEY =            "numControls";
        public static final String NUMBER_CASE_CARRIERS_KEY =       "numCaseCarriers";
        public static final String NUMBER_CONTROL_CARRIERS_KEY =    "numControlCarriers";
        public static final String NUMBER_CASE_VARIANTS_KEY =       "numCaseVariants";
        public static final String NUMBER_CONTROL_VARIANTS_KEY =    "numControlVariants";
        public static final String NUMBER_INPUT_VARIANTS_KEY =      "numInputVariants";
        public static final String P_VALUE_KEY =                    "pValue";
        public static final String BETA_KEY =                       "beta";
        public static final String STANDARD_ERROR__KEY =            "stdError";
        public static final String CI_LEVEL_KEY =                   "ciLevel";
        public static final String CI_LOWER_KEY =                   "ciLower";
        public static final String CI_UPPER_KEY =                   "ciUpper";
    }

}

package org.broadinstitute.mpg.dcc.controller;

import org.apache.log4j.Logger;
import org.broadinstitute.mpg.dcc.ReliancePoinService;
import org.broadinstitute.mpg.dcc.bean.Greeting;
import org.broadinstitute.mpg.dcc.bean.RestResultBean;
import org.broadinstitute.mpg.dcc.bean.VariantResultBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by mduby on 6/30/16.
 */
@RestController
public class RelianceController {
    // instance variables
    private final Logger controllerLog = Logger.getLogger(this.getClass().getName());

    // instance variables
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private ReliancePoinService reliancePoinService = new ReliancePoinService();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        String test = "test";
        return new Greeting(counter.incrementAndGet(), String.format(template, test + ": " + name));
    }

    @RequestMapping("/burden2")
    public VariantResultBean burdenResult(@RequestParam(value="name", defaultValue="World") String name) {
        VariantResultBean resultBean = new VariantResultBean();
        resultBean.setNumCases(109);
        resultBean.setNumControls(86);
        resultBean.setNumCaseCarriers(1);
        resultBean.setNumControlCarriers(1);
        resultBean.setNumCaseVariants(1);
        resultBean.setNumControlVariants(1);
        resultBean.setNumInputVariants(10);
        resultBean.setpValue(0.4699);
        resultBean.setBeta(1.0748);
        resultBean.setStdError(1.4872);
        resultBean.setCiLevel(0.95);
        resultBean.setCiLower(3.989712);
        resultBean.setCiUpper(1.8401120000000002);
        return resultBean;
    }

    @RequestMapping(value = "/burden", method = RequestMethod.POST)
    public VariantResultBean burdenResultJson(@RequestBody String inputString) {
        VariantResultBean resultBean = new VariantResultBean();

        // log
        this.controllerLog.info("Got input json: " + inputString.toString());

        // build the json object
        JsonReader jsonReader = Json.createReader(new StringReader(inputString));
        JsonObject inputObject = jsonReader.readObject();

        // call the service
        /*
        try {
            resultBean = this.reliancePoinService.getBurdenResults(inputObject);

        } catch (DccServiceException exception) {
            this.controllerLog.error("got burden service error: " + exception.getMessage());
        }
        */

        // return
        return resultBean;
    }

    @RequestMapping("/results")
    public RestResultBean burdenResults(@RequestParam(value="name", defaultValue="World") String name) {
        RestResultBean restResultBean = new RestResultBean();

        // log
        this.controllerLog.info("started /results REST call");

        // add 1 bean
        VariantResultBean resultBean = new VariantResultBean();
        resultBean.setNumCases(109);
        resultBean.setNumControls(86);
        resultBean.setNumCaseCarriers(1);
        resultBean.setNumControlCarriers(1);
        resultBean.setNumCaseVariants(1);
        resultBean.setNumControlVariants(1);
        resultBean.setNumInputVariants(10);
        resultBean.setpValue(0.4699);
        resultBean.setBeta(1.0748);
        resultBean.setStdError(1.4872);
        resultBean.setCiLevel(0.95);
        resultBean.setCiLower(3.989712);
        resultBean.setCiUpper(1.8401120000000002);
        restResultBean.addToResults(resultBean);

        // add second bean
        resultBean = new VariantResultBean();
        resultBean.setNumCases(109);
        resultBean.setNumControls(86);
        resultBean.setNumCaseCarriers(1);
        resultBean.setNumControlCarriers(1);
        resultBean.setNumCaseVariants(1);
        resultBean.setNumControlVariants(1);
        resultBean.setNumInputVariants(10);
        resultBean.setpValue(0.4699);
        resultBean.setBeta(1.0748);
        restResultBean.addToResults(resultBean);

        // return
        return restResultBean;
    }
}

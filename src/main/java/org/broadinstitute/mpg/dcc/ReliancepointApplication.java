package org.broadinstitute.mpg.dcc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReliancepointApplication {

	@Autowired
	private ReliancePoinService reliancePoinService;

	public static void main(String[] args) {
		SpringApplication.run(ReliancepointApplication.class, args);
	}
}

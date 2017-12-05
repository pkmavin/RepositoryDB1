package com.db.awmd.challenge;

import org.springframework.boot.SpringApplication;
 
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.hibernate.validator.internal.engine.*;


@SpringBootApplication
public class DevChallengeApplication {
	DefaultParameterNameProvider n=null;
  public static void main(String[] args) {
    SpringApplication.run(DevChallengeApplication.class, args);
    }
}

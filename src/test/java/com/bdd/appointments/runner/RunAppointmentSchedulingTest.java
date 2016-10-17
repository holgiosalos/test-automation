package com.bdd.appointments.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * @author Holmes Salazar
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    format = { "pretty", "html:target/cucumber" },
    glue = "com.bdd.appointments.steps",
    features = "classpath:featureFiles/Appointments.feature"
)
public class RunAppointmentSchedulingTest
{
}

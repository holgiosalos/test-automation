package com.bdd.appointments.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.condition.AnyOf.anyOf;

import com.bdd.appointments.common.SimpleRestClient;
import com.bdd.appointments.common.TestsContext;
import com.bdd.appointments.pages.AppointmentSchedulingPage;
import com.bdd.appointments.pages.HomePage;
import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.assertj.core.api.Condition;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;

/**
 * Step definition class
 *
 * @author Holmes Salazar
 */
public class AppointmentSchedulingStepDef
{
    private static final String CHROME_WEB_DRIVER = "chrome";
    private static final String OPERA_WEB_DRIVER = "opera";
    private static final String FIREFOX_WEB_DRIVER = "firefox";

    private TestsContext testsContext = new TestsContext();
    private SimpleRestClient simpleRestClient;
    private WebDriver webDriver;
    private HomePage homePage;
    private AppointmentSchedulingPage appointmentSchedulingPage;

    @Before
    public void setUp()
    {
        webDriver = getDriver(testsContext.getConfiguredDriver());
        webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    private WebDriver getDriver(final String configuredDriver)
    {
        WebDriver webDriver;
        switch (configuredDriver)
        {
            case CHROME_WEB_DRIVER:
                webDriver = new ChromeDriver();
                break;

            case OPERA_WEB_DRIVER:
                webDriver = new OperaDriver();
                break;

            case FIREFOX_WEB_DRIVER:
            default:
                webDriver = new FirefoxDriver();
                break;
        }
        return webDriver;
    }

    @After
    public void tearDown()
    {
        webDriver.quit();
        webDriver = null;
        homePage = null;
        appointmentSchedulingPage = null;
    }

    @Given("^I am logged in the Home Page$")
    public void givenLoggedInHomePage()
    {
        webDriver.get(testsContext.getBaseUrl());
        homePage = new HomePage(webDriver);
    }

    @Given("^the following patients are requesting a medical appointment$")
    public void givenPatientsAreRegistered(final DataTable patientsTable)
    {
        for (Map<String, String> patientMap : patientsTable.asMaps(String.class, String.class))
        {
            String result = getSimpleRestClient().registerPatient(patientMap);

            Condition<String> registered =
                new Condition<>(result::contains, "Datos guardados correctamente.");
            Condition<String> alreadyExist =
                new Condition<>(result::contains, "El campo &#039;Documento de identidad&#039; ya esta registrado.");

            assertThat(result)
                .as("The patient" + patientMap.get("name") + ", was not registered successfully")
                .is(anyOf(registered, alreadyExist));
        }
    }

    private SimpleRestClient getSimpleRestClient()
    {
        if (simpleRestClient == null)
        {
            simpleRestClient = new SimpleRestClient(testsContext.getBaseUrl());
        }
        return simpleRestClient;
    }

    @Given("^the following doctors are available to attend patients$")
    public void givenDoctorsAreAvailable(final DataTable doctorsTable)
    {
        for (Map<String, String> doctorMap : doctorsTable.asMaps(String.class, String.class))
        {
            String result = getSimpleRestClient().registerDoctor(doctorMap);

            Condition<String> registered =
                new Condition<>(result::contains, "Datos guardados correctamente.");
            Condition<String> alreadyExist =
                new Condition<>(result::contains, "El campo &#039;Documento de identidad&#039; ya esta registrado.");

            assertThat(result)
                .as("The doctor " + doctorMap.get("name") + ", was not registered successfully")
                .is(anyOf(registered, alreadyExist));
        }
    }

    @When("^I open the Appointment Scheduling option$")
    public void whenIOpenAppointmentSchedulingOption()
    {
        appointmentSchedulingPage = homePage.goToAppintmentScheduling();
    }

    @When("^I choose the Appointment Date to be \"([^\"]*)\"$")
    public void whenISetAppointmentDate(final String dateStr)
    {
        appointmentSchedulingPage.setAppointmentDate(dateStr);
    }

    @When("^I choose the Appointment Date to be the current date$")
    public void whenISetCurrentDate()
    {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        whenISetAppointmentDate(dateFormat.format(new Date()));
    }

    @When("^I set the Patient document to be \"([^\"]*)\"$")
    public void whenISetPatientDocument(final String patientDocument)
    {
        appointmentSchedulingPage.setPatientId(patientDocument);
    }

    @When("^I set the Doctor document to be \"([^\"]*)\"$")
    public void whenISetDoctorDocument(final String doctorDocument)
    {
        appointmentSchedulingPage.setDoctorId(doctorDocument);
    }

    @When("^I add \"([^\"]*)\" as Appointment note$")
    public void whenISetAppointmentNote(final String appointmentNote)
    {
        appointmentSchedulingPage.setNotes(appointmentNote);
    }

    @When("^I save the Appointment$")
    public void whenISaveTheAppointment()
    {
        appointmentSchedulingPage.saveAppointment();
    }

    @Then("^I expect the Appointment to not be scheduled$")
    public void thenIExpectAppointmentToNotBeScheduled()
    {
        assertThat(appointmentSchedulingPage.getSaveResultText())
            .as("The Appointment should not be saved")
            .contains("No se pudo guardar debido a:");
    }

    @Then("^I expect the Appointment to be scheduled$")
    public void thenIExpectAppointmentToBeScheduled()
    {
        assertThat(appointmentSchedulingPage.getSaveResultText())
            .as("The Appointment should be saved")
            .contains("Datos guardados correctamente.");
    }

    @Then("^I expect the Error message to say document of patient is not registered$")
    public void thenIExpectErrorMessageAboutPatient()
    {
        assertThat(appointmentSchedulingPage.getSaveResultText())
            .as("Unexpected error message on Appointment Scheduling Page")
            .contains("no se encuentra entre nuestros pacientes registrados");
    }

    @Then("^I expect the Error message to say document of doctor is not registered$")
    public void thenIExpectErrorMessageAboutDoctor()
    {
        assertThat(appointmentSchedulingPage.getSaveResultText())
            .as("Unexpected error message on Appointment Scheduling Page")
            .contains("no se encuentra entre nuestros doctores");
    }
}

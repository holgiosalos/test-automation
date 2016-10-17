package com.bdd.appointments.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Holmes Salazar
 */
public class AppointmentSchedulingPage
{
    private final WebDriver webDriver;
    private WebDriverWait driverWait;

    public AppointmentSchedulingPage(final WebDriver webDriver)
    {
        this.webDriver = webDriver;
        driverWait = new WebDriverWait(webDriver, 30);
    }

    /**
     * Sets the appointment date in this page.
     *
     * @param appointmentDate date in MM/DD/AAAA format
     */
    public void setAppointmentDate(final String appointmentDate)
    {
        webDriver.findElement(By.xpath("(//input[@type='text'])[1]")).clear();
        webDriver.findElement(By.xpath("(//input[@type='text'])[1]")).sendKeys(appointmentDate);
    }

    public void setPatientId(final String patientId)
    {
        webDriver.findElement(By.xpath("(//input[@type='text'])[2]")).clear();
        webDriver.findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys(patientId);
    }

    public void setDoctorId(final String doctorId)
    {
        webDriver.findElement(By.xpath("(//input[@type='text'])[3]")).clear();
        webDriver.findElement(By.xpath("(//input[@type='text'])[3]")).sendKeys(doctorId);
    }

    public void setNotes(final String notes)
    {
        webDriver.findElement(By.cssSelector("textarea.form-control")).clear();
        webDriver.findElement(By.cssSelector("textarea.form-control")).sendKeys(notes);
    }

    public void saveAppointment()
    {
        webDriver.findElement(By.linkText("Guardar")).click();
        driverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("textarea.form-control")));
    }

    public String getSaveResultText()
    {
        return webDriver.findElement(By.cssSelector("div.panel-body")).getText();
    }
}

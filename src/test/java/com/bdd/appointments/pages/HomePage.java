package com.bdd.appointments.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object Model for Principal Page
 *
 * @author Holmes Salazar
 */
public class HomePage
{
    private static final String HOME_PAGE_TITLE = "PSL - Ejercicio de automatizaci&oacute;n";

    private final WebDriver webDriver;

    public HomePage(final WebDriver webDriver)
    {
        this.webDriver = webDriver;

        if (HOME_PAGE_TITLE.equalsIgnoreCase(webDriver.getTitle()))
        {
            throw new IllegalStateException("This is not the home page, current page is: " + webDriver.getTitle());
        }
    }

    public AppointmentSchedulingPage goToAppintmentScheduling()
    {
        webDriver.findElement(By.linkText("Agendar Cita")).click();
        return new AppointmentSchedulingPage(webDriver);
    }
}
